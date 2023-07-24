package com.dnc.server.controller;

import com.dnc.server.es.dto.ESLoggingDto;
import com.dnc.server.es.dto.ESMemberDto;
import com.dnc.server.es.service.ESLoggingService;
import com.dnc.server.es.service.ESMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping(value = "/log")
@CrossOrigin("*")
public class LoggingController {

    @Autowired
    ESLoggingService loggingService;

    @RequestMapping(value="/addLog.do", method = RequestMethod.POST)
    public void addMember(ESLoggingDto log){
        log.setTimestamp(new Date());

        loggingService.addLog(log);
    }

    @RequestMapping(value="/getLogs.do", method = RequestMethod.POST)
    public Iterable<ESLoggingDto> getMemberList(){

        return loggingService.getLogs();
    }

    @RequestMapping(value="/hasIp.do", method = RequestMethod.GET)
    public int hasIp(String ip){

        return loggingService.hasIp(ip);
    }
}
