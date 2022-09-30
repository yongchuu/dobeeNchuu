package com.dnc.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ClientController {

    @RequestMapping(value="/heartbeat.do", method = RequestMethod.POST)
    public void heartbeat(@RequestParam String clientId){
        // TODO : 클라이언트 관리

    }

    @RequestMapping(value="/addcookie.do", method = RequestMethod.POST)
    public void addCookie(@RequestParam String cookieJson){
        // TODO : 내가만든 쿠키

    }


}
