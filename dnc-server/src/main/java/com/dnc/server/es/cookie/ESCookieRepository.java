package com.dnc.server.es.cookie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ESCookieRepository extends ElasticsearchRepository<ESCookieDto, String> {

    @Query( "  {\n" +
            "    \"function_score\": {\n" +
            "      \"random_score\": {}\n" +
            "    }\n" +
            "  }")
    Page<ESCookieDto> findRandom(Pageable pageable);
}