package com.example.shengtingapi.controller.test;

import com.example.shengtingapi.dto.RestResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test2")
public class Test2Controller {
    @RequestMapping(value = "/test")
    public Object test(){
        return   new RestResult(null);
    }

}
