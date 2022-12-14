package com.dnc.server.es.cookie.service;

import com.dnc.server.es.cookie.ESCookieDto;
import com.dnc.server.es.cookie.ESCookieRepository;
import com.sun.istack.NotNull;
import org.apache.log4j.Logger;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
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
