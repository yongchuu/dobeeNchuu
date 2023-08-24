package com.dnc.server.oauth;

import com.dnc.server.oauth.dao.MemberDao;
import com.dnc.server.oauth.dto.MemberDto;
import com.dnc.server.utils.GsonUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Slf4j
@Service
public class OAuthService {
    private static final String kakaoUrl = "https://kapi.kakao.com/v2/user/me";

    @Autowired
    MemberDao dao;

    public String getKakaoAccessToken (String code) {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=ac6b1e0a4255c2fda458605a05833aae");
            //sb.append("&redirect_uri=http://50.19.142.110:8080/dnc/oauth/kakao.do");
            sb.append("&redirect_uri=http://chongmubot.click/dnc/oauth/kakao.do");
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();

            if(responseCode >= 400){
                log.error("responseCode : " + responseCode);
                log.error(conn.getResponseMessage());
                throw new RuntimeException("KaKao 로그인 실패!!");
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            access_Token = GsonUtils.getAsString(result, "access_token");
//            refresh_Token = GsonUtils.getAsString(result, "refresh_token");

            if(log.isDebugEnabled()) {
                log.debug("access_token : " + access_Token);
            }

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return access_Token;
    }

    public String getEmail(String token) {

        String email = "";

        //access_token을 이용하여 사용자 정보 조회
        try {
            URL url = new URL(kakaoUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token); //전송할 header 작성, access_token전송

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            if(log.isDebugEnabled()) {
                log.debug("response body : " + result);
            }

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            int id = element.getAsJsonObject().get("id").getAsInt();
            boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
            email = "";
            if(hasEmail){
                email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
            }

            if(log.isDebugEnabled()) {
                log.debug("email : " + email);
            }

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return email;
    }

    public MemberDto getMemeber(String email) {
        List<MemberDto> memberList = dao.getMemberByEmail(email);
        if(memberList.size() == 0){
            return new MemberDto();
        }else{
            return memberList.get(0);
        }
    }

    public int addMember(MemberDto dto){
        if(log.isDebugEnabled()){
            log.debug(dto.toString());
        }

        return dao.addMember(dto);
    }


}
