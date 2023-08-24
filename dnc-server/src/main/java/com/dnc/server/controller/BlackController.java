package com.dnc.server.controller;

import com.dnc.server.black.dto.BlackMemberDto;
import com.dnc.server.black.services.BlackMemberService;
import com.dnc.server.utils.GsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/black")
@CrossOrigin("*")
public class BlackController {
    @Autowired
    BlackMemberService service;

    @RequestMapping(value="/isBlackMember.do", method = RequestMethod.POST)
    public Iterable isBlackMember(@RequestParam String jsonString){
        String blackTel = GsonUtils.getAsString(jsonString, "blackTel");

        return service.selectByNumber(blackTel);
    }

    @RequestMapping(value="/addBlackMember.do", method = RequestMethod.POST)
    public int addBlackMember(@RequestBody BlackMemberDto dto){
        return service.addBlackMember(dto);
    }


}
