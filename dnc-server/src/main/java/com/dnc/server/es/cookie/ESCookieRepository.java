package com.dnc.server.es.cookie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Repository;

@Repository
public interface ESCookieRepository extends ElasticsearchRepository<ESCookieDto, String> {

    @Query( "  {\n" +
            "    \"function_score\": {\n" +
            "      \"random_score\": {}\n" +
            "    }\n" +
            "  }")
    Page<ESCookieDto> findRandom(Pageable pageable);

    @Query("{\"match\": {\n" +
            "      \"version\": \"?0\"\n" +
            "    }}")
    Page<ESCookieDto> findFirst(String version, Pageable pageable);

    Page<ESCookieDto> deleteByUuid(String uuid, Pageable pageable);
}