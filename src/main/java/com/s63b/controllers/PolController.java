package com.s63b.controllers;

import com.s63b.dao.PolDao;
import com.s63b.domain.Pol;
import com.s63b.domain.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
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
        String pattern = "^(?>[A-Z]{2}|\\d\\d)-(?>[A-Z]{2}|\\d\\d)-(?<!\\d\\d-\\d\\d-)\\d\\d$|^(?>[A-Z]{2}|\\d\\d)-(?>[A-Z]{2}|\\d\\d)-(?<![A-Z]{2}-[A-Z]{2}-)[A-Z]{2}$|^\\d\\d-[A-Z]{3}-\\d$";
        if(!licencePlate.matches(pattern))return new Result(false, licencePlate, 1, "Lincence plate invalid.");

        Pol pol = new Pol(licencePlate, lat, lng, System.currentTimeMillis());
        if(polDao.addPol(pol)) return new Result(true, pol, 0, "Pol added");

        return new Result(false, licencePlate, 1, "Something went wrong");
    }

    @RequestMapping(path = "/pols/{licencePlate}", method = RequestMethod.GET)
    public List<Pol> pol(@PathVariable String licencePlate) {
        return polDao.getPols(licencePlate);
    }
}
