package com.dnc.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UtilityController {

    @RequestMapping(value="/ping.do", method = RequestMethod.POST)
    public String healthCheck(){
        return "pong";
    }
}
