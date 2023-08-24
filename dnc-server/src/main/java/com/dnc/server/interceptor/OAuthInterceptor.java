package com.dnc.server.interceptor;


import com.dnc.server.oauth.OAuthService;
import com.dnc.server.oauth.dto.MemberDto;
import com.dnc.server.utils.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
@Slf4j
public class OAuthInterceptor implements HandlerInterceptor {

    @Autowired
    OAuthService service;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception{
        Map<String, String> map = CookieUtils.getCookieMap(req);
        String token = map.get(CookieUtils.SESSION_KEY);

        if(token == null || token.isEmpty()){
            if(log.isDebugEnabled()) {
                log.debug("has no token, so return " + HttpServletResponse.SC_UNAUTHORIZED);
            }

//            resp.sendRedirect("http://chongmubot.click/noauthorize");
//            resp.setHeader("Location", "http://chongmubot.click/noauthorize");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("Unauthorized");
        }
        else {
            if(log.isDebugEnabled()) {
                log.debug(req.getRequestURI());
                log.debug(CookieUtils.SESSION_KEY + " : " + token);
            }

            // 권한 처리
            String email = service.getEmail(token);
            MemberDto member = service.getMemeber(email);

            log.debug(member.getDormOwner() + "s type : " +member.getType());

            if(member.getType().equals("admin")){
                // 권한이 있는 경우
                return true;
            }else{
                // 권한이 없는 경우
//                resp.sendRedirect("http://chongmubot.click/noauthorize");
//                resp.setHeader("Location", "http://chongmubot.click/noauthorize");
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                resp.getWriter().write("Forbidden");
                return false;
            }
        }

        return false;
    }

}
