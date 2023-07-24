package com.dnc.server.es.repository;

import com.dnc.server.es.dto.ESOrderDto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ESOrderRepository extends ElasticsearchRepository<ESOrderDto, String> {


    void deleteByUuid(String uuid);
}