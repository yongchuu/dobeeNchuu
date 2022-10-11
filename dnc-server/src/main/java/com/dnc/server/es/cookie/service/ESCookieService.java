package com.dnc.server.es.cookie.service;

import com.dnc.server.es.cookie.ESCookieDto;
import com.dnc.server.es.cookie.ESCookieRepository;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    public Page<ESCookieDto> getCookie(){
        if(logger.isDebugEnabled()){
            logger.debug("get cookie function called");
        }
        return repo.findRandom(new Pageable() {
            @Override
            public int getPageNumber() {
                return 0;
            }

            @Override
            public int getPageSize() {
                return 1;
            }

            @Override
            public long getOffset() {
                return 0;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public Pageable withPage(int pageNumber) {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        });
    }
}
