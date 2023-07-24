package com.dnc.server.es.service;

import com.dnc.server.es.dto.ESCookieDto;
import com.dnc.server.es.repository.ESCookieRepository;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

@Service
public class ESCookieService {

    private Logger logger = Logger.getLogger(ESCookieService.class);

    @Resource
    ESCookieRepository repo;


    public void saveCookie(ESCookieDto cookie){
        if(logger.isDebugEnabled()){
            logger.debug("save cookie function called : " + cookie);
        }
        cookie.setTimestamp(new Date());
        cookie.setUuid(UUID.randomUUID().toString());
        repo.save(cookie);
    }

    public Page<ESCookieDto> getCookie(String version, Pageable pageable){
        if(logger.isDebugEnabled()){
            logger.debug("get cookie function called");
        }

        Page<ESCookieDto> page = repo.findFirst(version, pageable);

        String uuid = "";
        if(page.getContent().size()!=0)
        {
            uuid = page.getContent().get(0).getUuid();
        }

        if(uuid != null && !StringUtils.isEmpty(uuid)){
            repo.deleteByUuid(uuid, pageable);
        }

        return page;
    }
}
