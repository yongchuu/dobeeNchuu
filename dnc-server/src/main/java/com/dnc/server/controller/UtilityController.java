package com.dnc.server.controller;

import com.fasterxml.jackson.databind.DatabindException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@Controller
public class UtilityController {

    @RequestMapping(value="/ping.do", method = RequestMethod.POST)
    public String healthCheck(){
        return "pong";
    }

    @RequestMapping(value="/time.do", method = RequestMethod.POST)
    public String getServerTime(){
        return new Date().toString();
    }
}
