package com.s63b.controllers;

import com.S63B.domain.Entities.Car;
import com.S63B.domain.Entities.LicensePlate;
import com.S63B.domain.Entities.Pol;
import com.S63B.domain.Entities.Tracker;
import com.S63B.domain.Enums;
import com.S63B.domain.Ride;
import com.google.maps.*;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import com.s63b.dao.CarDao;
import com.s63b.dao.LicensePlateDao;
import com.s63b.dao.PolDao;
import com.s63b.dao.TrackerDao;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static javax.ws.rs.core.Response.Status.OK;
import static javax.ws.rs.core.Response.Status.REQUEST_TIMEOUT;

@RestController
public class PolController {

    private PolDao polDao;
    private TrackerDao trackerDao;
    private CarDao carDao;
    private LicensePlateDao licensePlateDao;

    @PostConstruct
    private void init(){
        polDao = new PolDao();
        trackerDao = new TrackerDao();
        carDao = new CarDao();
        licensePlateDao = new LicensePlateDao();
    }

    /**
     * Add pol
     * Sample request: POST http://localhost:8080/pol?license_plate=25-GGG-3&lat=51.4505821&lng=5.4686695
     * @param licensePlate
     * @param lat
     * @param lng
     * @return The added pol if successful else the error message.
     */
    @RequestMapping(value = "/pol", method = RequestMethod.POST)
    public Response addPol(@RequestParam(value="license_plate") String licensePlate,
                           @RequestParam(value="lat") double lat,
                           @RequestParam(value="lng") double lng,
                           @RequestParam(value="timestamp") long timestamp) {
        // Check if license plate is valid by Dutch standard
//        String pattern = "^(?>[A-Z]{2}|\\d\\d)-(?>[A-Z]{2}|\\d\\d)-(?<!\\d\\d-\\d\\d-)\\d\\d$|^(?>[A-Z]{2}|\\d\\d)-(?>[A-Z]{2}|\\d\\d)-(?<![A-Z]{2}-[A-Z]{2}-)[A-Z]{2}$|^\\d\\d-[A-Z]{3}-\\d$";
//        if(!licensePlate.matches(pattern))
//            return Response.status(BAD_REQUEST).entity("License plate invalid.").build();

        Car car = carDao.getCar(licensePlate);

        if (car == null){
            LicensePlate plate = new LicensePlate(licensePlate, new DateTime());
            Tracker tracker = new Tracker(null, null);

            licensePlateDao.create(plate);
            trackerDao.create(tracker);

            car = new Car(plate, Enums.EnergyLabel.A);
            carDao.create(car);

            tracker.setCar(car);
            car.setTracker(tracker);

            trackerDao.save(tracker);
            carDao.save(car);
        }

        // Add poll to database
        Pol pol = new Pol(car.getTracker(), lat, lng, timestamp);
        if(polDao.create(pol))
            return Response.status(OK).entity(pol).build();

        return Response.status(REQUEST_TIMEOUT).entity("Something went wrong.").build();
    }

    /**
     * Get polls of license plate.
     * Sample request: GET http://localhost:8080/pols?license_plate=25-PPP-3
     * @param licensePlate
     * @return List of polls if successful else the error message.
     */
    @RequestMapping(value = "/pols", method = RequestMethod.GET)
    public Response getPolls(@RequestParam(value="license_plate") String licensePlate) {
        GenericEntity<List<Pol>> pols = new GenericEntity<List<Pol>>(polDao.getPols(licensePlate)) {};
//        if(pols != null)
        return Response.status(OK).entity(pols).build();

//        return Response.status(REQUEST_TIMEOUT).entity("Something went wrong.").build();
    }

    @RequestMapping(value = "/rides", method = RequestMethod.GET)
    public Response getRides(@RequestParam(value="license_plate") String licensePlate,
                             @RequestParam(value="start_date") long startDate,
                             @RequestParam(value="end_date") long endDate) {
        List<Pol> pols = polDao.getPolsBetween(licensePlate, startDate, endDate);

        Collections.sort(pols);

        List<Ride> rides = new ArrayList<>();
        Pol lastPol = null;
        Ride currentRide = new Ride();

        for (Pol pol : pols){
            if (!(lastPol == null || pol.getTimestampMillis() - lastPol.getTimestampMillis() < 300000)){
                currentRide = updateRide(currentRide, licensePlate);
                rides.add(currentRide);
                currentRide = new Ride();
            }

            currentRide.addPol(pol);
            lastPol = pol;
        }

        currentRide = updateRide(currentRide, licensePlate);
        rides.add(currentRide);

        List<Ride> ridez = new ArrayList<>();

        for (Ride ride : rides){
            if (ride.getPols().size() > 1){
                ridez.add(ride);
            }
        }

        return Response.status(OK).entity(ridez).build();
    }

    /**
     * Get driven distance between time range of licenseplate.
     * Sample request: GET http://localhost:8080/distance?license_plate=25-PPP-3&start_date=1480000073366&end_date=1490090073366
     * @param licensePlate
     * @param startDate
     * @param endDate
     * @return List of polls if successful else the error message.
     */
    @RequestMapping(value = "/distance", method = RequestMethod.GET)
    public Response getDrivenDistance(@RequestParam(value="license_plate") String licensePlate,
                                      @RequestParam(value="start_date") long startDate,
                                      @RequestParam(value="end_date") long endDate) {
        List<Pol> pols = polDao.getPolsBetween(licensePlate, startDate, endDate);
        if(pols != null){
            long meters = 0;

            for (int i = 0; i < pols.size() - 1; i++) {
                GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyDNtmOxKdE2VfxAHO6wTdiqRZMoGN_20cc").setQueryRateLimit(100);
                try {
                    DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(context);
                    DistanceMatrix trix = req
                            .origins(new LatLng(pols.get(i).getLat(), pols.get(i).getLng()))
                            .destinations(new LatLng(pols.get(i + 1).getLat(), pols.get(i + 1).getLng()))
                            .mode(TravelMode.DRIVING)
                            .language("en-EN")
                            .await();
                    meters += trix.rows[0].elements[0].distance.inMeters;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            return Response.status(OK).entity(meters).build();
        }
        return Response.status(REQUEST_TIMEOUT).entity("Something went wrong.").build();
    }

    public Ride updateRide(Ride ride, String licensePlate){
        if (ride.getPols().size() >= 1){
            ride.setStartDate(ride.getPols().get(0).getTimestampMillis());
            ride.setEndDate(ride.getPols().get(ride.getPols().size() - 1).getTimestampMillis());
        }

        ride.setDistance((long) this.getDrivenDistance(licensePlate, ride.getStartDate() - 1, ride.getEndDate() + 1).getEntity());

        return ride;
    }
}
