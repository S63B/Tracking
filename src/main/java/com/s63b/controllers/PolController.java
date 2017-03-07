package com.s63b.controllers;

import com.s63b.domain.Result;
import org.springframework.web.bind.annotation.*;

/**
 * Created by bramd on 21-2-2017.
 */
@RestController
public class PolController {

    @RequestMapping(path = "/pol/{id}/{lat}/{lng}", method = RequestMethod.POST)
    public Result pol(@PathVariable int id, @PathVariable float lat, @PathVariable float lng) {


        return new Result(true, id + lat + lng, 0, "Hastikke mooi");
    }
}
