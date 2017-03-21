package com.s63b.controllers;

import com.google.maps.*;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.LatLng;
import com.google.maps.model.TransitRoutingPreference;
import com.google.maps.model.TravelMode;
import com.s63b.dao.PolDao;
import com.s63b.domain.Pol;
import com.s63b.domain.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static javax.ws.rs.core.Response.Status.OK;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.REQUEST_TIMEOUT;
/**
 * Created by bramd on 21-2-2017.
 */
@RestController
public class PolController {

    private PolDao polDao;

    @PostConstruct
    private void init(){
        polDao = new PolDao();
    }

    /**
     * Add pol
     * Sample request: POST http://localhost:8080/pol?licence_plate=25-GGG-3&lat=51.4505821&lng=5.4686695
     * @param licencePlate
     * @param lat
     * @param lng
     * @return The added pol if successful else the error message.
     */
    @RequestMapping(value = "/pol", method = RequestMethod.POST)
    public Response addPol(@RequestParam(value="licence_plate", required=true) String licencePlate,
                        @RequestParam(value="lat", required=true) float lat,
                        @RequestParam(value="lng", required=true) float lng) {
        // Check if licence plate is valid by Dutch standard
        String pattern = "^(?>[A-Z]{2}|\\d\\d)-(?>[A-Z]{2}|\\d\\d)-(?<!\\d\\d-\\d\\d-)\\d\\d$|^(?>[A-Z]{2}|\\d\\d)-(?>[A-Z]{2}|\\d\\d)-(?<![A-Z]{2}-[A-Z]{2}-)[A-Z]{2}$|^\\d\\d-[A-Z]{3}-\\d$";
        if(!licencePlate.matches(pattern))
            return Response.status(BAD_REQUEST).entity("Lincence plate invalid.").build();

        // Add poll to database
        Pol pol = new Pol(licencePlate, lat, lng, System.currentTimeMillis());
        if(polDao.addPol(pol))
            return Response.status(OK).entity(pol).build();

        return Response.status(REQUEST_TIMEOUT).entity("Somethign went wrong.").build();
    }

    /**
     * Get polls of licence plate.
     * Sample request: GET http://localhost:8080/pols?licence_plate=25-PPP-3
     * @param licencePlate
     * @return List of polls if successful else the error message.
     */
    @RequestMapping(value = "/pols", method = RequestMethod.GET)
    public Response getPolls(@RequestParam(value="licence_plate", required=true) String licencePlate) {
        GenericEntity<List<Pol>> pols = new GenericEntity<List<Pol>> (polDao.getPols(licencePlate)) {};
        if(pols != null)
            return Response.status(OK).entity(pols).build();

        return Response.status(REQUEST_TIMEOUT).entity("Somethign went wrong.").build();
    }

    /**
     * Get driven distance between time range of licenceplate.
     * Sample request: GET http://localhost:8080/distance?licence_plate=25-PPP-3&start_date=1480000073366&end_date=1490090073366
     * @param licencePlate
     * @param startDate
     * @param endDate
     * @return List of polls if successful else the error message.
     */
    @RequestMapping(value = "/distance", method = RequestMethod.GET)
    public Response getDrivenDistance(@RequestParam(value="licence_plate", required=true) String licencePlate,
                        @RequestParam(value="start_date", required=true) long startDate,
                        @RequestParam(value="end_date", required=true) long endDate) {
        List<Pol> pols = polDao.getPolsBetween(licencePlate, startDate, endDate);
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
        return Response.status(REQUEST_TIMEOUT).entity("Somethign went wrong.").build();
    }
}
