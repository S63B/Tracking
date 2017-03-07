package com.s63b.controllers;

import com.google.maps.DirectionsApi;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import com.s63b.dao.PolDao;
import com.s63b.domain.Pol;
import com.s63b.domain.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping(path = "/pol/{licencePlate}/{lat}/{lng}", method = RequestMethod.POST)
    public Result pol(@PathVariable String licencePlate, @PathVariable float lat, @PathVariable float lng) {
        // Check if licence plate is valid by EU standard
        String pattern = "^(?>[A-Z]{2}|\\d\\d)-(?>[A-Z]{2}|\\d\\d)-(?<!\\d\\d-\\d\\d-)\\d\\d$|^(?>[A-Z]{2}|\\d\\d)-(?>[A-Z]{2}|\\d\\d)-(?<![A-Z]{2}-[A-Z]{2}-)[A-Z]{2}$|^\\d\\d-[A-Z]{3}-\\d$";
        if(!licencePlate.matches(pattern))
            return new Result(false, licencePlate, 1, "Lincence plate invalid.");

        Pol pol = new Pol(licencePlate, lat, lng, System.currentTimeMillis());
        if(polDao.addPol(pol))
            return new Result(true, pol, 0, "Pol added");

        return new Result(false, licencePlate, 1, "Something went wrong");
    }

    @RequestMapping(path = "/pols/{licencePlate}", method = RequestMethod.GET)
    public Result pol(@PathVariable String licencePlate) {
        List<Pol> pols = polDao.getPols(licencePlate);
        if(pols != null)
            return new Result(true,  pols, 0, "OK");

        return new Result(false, null, 1, "Something went wrong");
    }

    @RequestMapping(path = "/pols/{licencePlate}/{startDate}/{endDate}", method = RequestMethod.GET)
    public Result pol(@PathVariable String licencePlate, @PathVariable long startDate, @PathVariable long endDate) {
        List<Pol> pols = polDao.getPolsBetween(licencePlate, startDate, endDate);
        if(pols == null)
            return new Result(false,  null, 0, "Something went wrong");

        long meters = 0;

        for (int i = 0; i < pols.size() - 1; i++) {
            if(pols != null) {
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
                } catch (ApiException e) {
                    System.out.println(e.getMessage());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }
        }

        return new Result(true, meters, 0, "OK");
    }
}
