package com.example.demo.controller;

import com.example.demo.dto.RestResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test2")
public class TestController {
    @RequestMapping(value = "/test")
    public Object test(){
        return   new RestResult(null);
    }

}