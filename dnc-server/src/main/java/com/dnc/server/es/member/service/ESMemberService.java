package com.dnc.server.es.member.service;

import com.dnc.server.es.member.ESMemberDto;
import com.dnc.server.es.member.ESMemberRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ESMemberService {

    private Logger logger = Logger.getLogger(ESMemberService.class);

    @Resource
    ESMemberRepository repo;


    public void addMember(ESMemberDto mem){
        if(logger.isDebugEnabled()){
            logger.debug("save member function called : " + mem);
        }
        repo.save(mem);
    }

    public void deleteMember(String email){
        if(logger.isDebugEnabled()){
            logger.debug("delete Member function called : " + email);
        }

        repo.deleteByEmail(email);
    }

    public Iterable<ESMemberDto> getMemberList(){
        return repo.findAll();
    }
}
