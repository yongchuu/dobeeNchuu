package com.dnc.server.es.repository;

import com.dnc.server.es.dto.ESMemberDto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ESMemberRepository extends ElasticsearchRepository<ESMemberDto, String> {


    void deleteByEmail(String email);
}