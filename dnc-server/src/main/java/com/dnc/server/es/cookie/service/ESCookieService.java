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

import javax.annotation.Resource;
import java.util.Date;

@Service
public class ESCookieService {

    private Logger logger = Logger.getLogger(ESCookieService.class);

    @Resource
    ESCookieRepository repo;

    @Resource
    ElasticsearchOperations oper;


    public void saveCookie(ESCookieDto cookie){
        if(logger.isDebugEnabled()){
            logger.debug("save cookie function called : " + cookie);
        }
        cookie.setTimestamp(new Date());
        repo.save(cookie);
    }

    public Page<ESCookieDto> getCookie(String version, Pageable pageable){
        if(logger.isDebugEnabled()){
            logger.debug("get cookie function called");
        }

        Page<ESCookieDto> page = repo.findFirst(version, pageable);

        repo.deleteFirst(version, pageable);

        MatchQueryBuilder mqb = QueryBuilders.matchQuery("version", version);

        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder().withQuery(mqb).withPageable(pageable).build();

        String query = nativeSearchQuery.getQuery().toString();
        if(logger.isDebugEnabled()){
            logger.debug("query :" + query);
        }
        Assert.notNull(oper);
        oper.delete(nativeSearchQuery);

        return page;
    }
}
