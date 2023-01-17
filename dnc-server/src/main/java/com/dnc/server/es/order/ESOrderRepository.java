package com.dnc.server.es.order;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ESOrderRepository extends ElasticsearchRepository<ESOrderDto, String> {


    void deleteByUuid(String uuid);
}