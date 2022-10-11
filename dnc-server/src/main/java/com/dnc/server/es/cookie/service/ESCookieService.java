package com.dnc.server.es.cookie.service;

import com.dnc.server.es.cookie.ESCookieDto;
import com.dnc.server.es.cookie.ESCookieRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ESCookieService {

    private Logger logger = Logger.getLogger(ESCookieService.class);

    @Resource
    ESCookieRepository repo;



    public void saveCookie(ESCookieDto cookie){
        if(logger.isDebugEnabled()){
            logger.debug("save cookie function called : " + cookie);
        }
        repo.save(cookie);
    }

    public void getCookie(){
        if(logger.isDebugEnabled()){
            logger.debug("get cookie function called");
        }
        
    }
}
