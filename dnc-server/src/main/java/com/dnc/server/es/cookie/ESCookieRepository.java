package com.dnc.server.es.cookie;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ESCookieRepository extends ElasticsearchRepository<ESCookieDto, String> {
}