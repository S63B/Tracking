package com.s63b;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by bramd on 21-2-2017.
 */
@RestController
public class PolController {

    @RequestMapping("/pol")
    public Result pol(@RequestParam(value="id", defaultValue="id") String id,
                         @RequestParam(value="lat", defaultValue="lat") String lat,
                         @RequestParam(value="lng", defaultValue="lng") String lng) {

        return new Result(true, id + lat + lng, 0, "Hastikke mooi");
    }
}
