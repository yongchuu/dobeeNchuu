package com.dnc.server.es.service;

import com.dnc.server.es.dto.ESLoggingDto;
import com.dnc.server.es.dto.ESMemberDto;
import com.dnc.server.es.repository.ESLoggingRepository;
import com.dnc.server.es.repository.ESMemberRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ESLoggingService {

    private Logger logger = Logger.getLogger(ESLoggingService.class);

    @Resource
    ESLoggingRepository repo;

    public void addLog(ESLoggingDto log){
        if(logger.isDebugEnabled()){
            logger.debug("save log function called : " + log);
        }
        repo.save(log);
    }

    public Iterable<ESLoggingDto> getLogs(){
        return repo.findAll();
    }

    public int hasIp(String ip) {

        return repo.hasIp(ip).size();
    }
}
