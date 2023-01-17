package com.dnc.server.es.member;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ESMemberRepository extends ElasticsearchRepository<ESMemberDto, String> {


    void deleteByEmail(String email);
}