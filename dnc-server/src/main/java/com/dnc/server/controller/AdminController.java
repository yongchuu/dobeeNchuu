package com.dnc.server.controller;

import com.dnc.server.black.services.BlackMemberService;
import com.dnc.server.oauth.OAuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    OAuthService authService;

    @Autowired
    BlackMemberService blackService;

    @RequestMapping(value="/getBlackList.do", method = RequestMethod.POST)
    public Iterable getBlackList(){
        return blackService.getBlackList();
    }
}
