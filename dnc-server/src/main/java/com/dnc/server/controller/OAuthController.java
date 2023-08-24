package com.dnc.server.controller;


import com.dnc.server.oauth.OAuthService;
import com.dnc.server.oauth.dto.MemberDto;
import com.dnc.server.utils.CookieUtils;
import com.dnc.server.utils.GsonUtils;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URI;
import java.net.URLEncoder;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {

    @Autowired
    OAuthService service;

    @ResponseBody
    @GetMapping("/kakao.do")
    public void kakaoCallback(@RequestParam String code,
                              HttpServletResponse resp
                              ) throws Exception{
        //https://kauth.kakao.com/oauth/authorize?client_id=ac6b1e0a4255c2fda458605a05833aae&redirect_uri=http://localhost:8081/dnc/oauth/kakao.do&response_type=code
        // 1. access 토큰 가져오기
        String accessToken = service.getKakaoAccessToken(code);

        // 1-1. cookie 설정
        resp.addCookie(CookieUtils.makeCookie(CookieUtils.SESSION_KEY, accessToken));

        // 2. 토큰으로 이메일 가져오기
        String email = service.getEmail(accessToken);

        System.out.println(email);
        // 3. DB에 사용자 조회
        MemberDto _dto = service.getMemeber(email);

        // 3-1. 사용자 있을 경우 : 로그인
        if(_dto.getEmail().isEmpty() == false){
            if(log.isDebugEnabled()){
                log.debug("member : " + URLEncoder.encode(_dto.getDormOwner()));
            }

            resp.addCookie(CookieUtils.makeCookie("name", URLEncoder.encode(_dto.getDormOwner())));
            resp.addCookie(CookieUtils.makeCookie("email", URLEncoder.encode(_dto.getEmail())));

            resp.sendRedirect("http://chongmubot.click");
        }
        // 3-2. 사용자 없을 경우 : 회원가입
        else{
            if(log.isDebugEnabled()){
                log.debug("no member");
            }

            resp.sendRedirect("http://chongmubot.click/join");
        }
    }

    @RequestMapping(value="/join.do", method = RequestMethod.POST)
    public void join(@RequestParam String jsonString,
                     HttpServletRequest req,
                     HttpServletResponse resp
    ) throws Exception {
        if(log.isDebugEnabled()){
            log.debug("join called : " + jsonString);
        }

        MemberDto dto = GsonUtils.fromJson(jsonString, MemberDto.class);

        String token = CookieUtils.getCookieMap(req).get(CookieUtils.SESSION_KEY);
        if(token.isEmpty()){
            log.error("There is no token");
            resp.sendRedirect("http://chongmubot.click/login");
        }else{
            if(log.isDebugEnabled()){
                log.debug("token : " + token);
            }
            dto.setEmail(service.getEmail(token));
            dto.setCntnt("");
            dto.setType("user");
            service.addMember(dto);
        }

    }
}
