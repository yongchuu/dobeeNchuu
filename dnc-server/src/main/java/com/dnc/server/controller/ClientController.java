package com.dnc.server.controller;

import com.dnc.server.es.cookie.ESCookieDto;
import com.dnc.server.es.cookie.service.ESCookieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/client")
public class ClientController {

    @Autowired
    ESCookieService cookieService;

    @RequestMapping(value="/heartbeat.do", method = RequestMethod.POST)
    public void heartbeat(@RequestParam String clientId){
        // TODO : 클라이언트 관리

    }

    @RequestMapping(value="/addcookie.do", method = RequestMethod.POST)
    public void addCookie(ESCookieDto cookie){

        cookieService.saveCookie(cookie);
    }

    @RequestMapping(value="/getcookie.do", method = RequestMethod.POST)
    public Page<ESCookieDto> getCookie(String version, @PageableDefault(value = 1, sort = "timestamp", direction = Sort.Direction.ASC) final Pageable pageable){

        return cookieService.getCookie(version, pageable);
    }

}
