package com.dnc.server.es.repository;

import com.dnc.server.es.dto.ESLoggingDto;
import com.dnc.server.es.dto.ESMemberDto;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ESLoggingRepository extends ElasticsearchRepository<ESLoggingDto, String> {

    @Query("{\"bool\":{\"must\":[{\"match\":{\"ip\":\"?0\"}},{\"range\":{\"timestamp\":{\"gte\":\"now-2d\",\"lte\":\"now\"}}}]}}")
    List<ESLoggingDto> hasIp(String ip);
}