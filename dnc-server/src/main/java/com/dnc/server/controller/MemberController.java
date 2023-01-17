package com.dnc.server.controller;

import com.dnc.server.es.member.ESMemberDto;
import com.dnc.server.es.member.service.ESMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/member")
@CrossOrigin("*")
public class MemberController {

    @Autowired
    ESMemberService memberService;

    @RequestMapping(value="/addMember.do", method = RequestMethod.POST)
    public void addMember(ESMemberDto member){

        memberService.addMember(member);
    }

    @RequestMapping(value="/deleteMember.do", method = RequestMethod.POST)
    public void deleteMember(String email){

        memberService.deleteMember(email);
    }

    @RequestMapping(value="/getMembers.do", method = RequestMethod.POST)
    public Iterable<ESMemberDto> getMemberList(){

        return memberService.getMemberList();
    }
}
