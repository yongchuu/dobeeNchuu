package com.dnc.server.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class MemberDto {
    int uuid;
    String email;

    String dormNm;
    String dormOwner;
    String dormTel;
    String dormAddr;
    String cntnt;
    Date date;

    String type;

    @Override
    public String toString() {
        return "MemberDto{" +
                "uuid=" + uuid +
                ", email='" + email + '\'' +
                ", dormNm='" + dormNm + '\'' +
                ", dormOwner='" + dormOwner + '\'' +
                ", dormTel='" + dormTel + '\'' +
                ", dormAddr='" + dormAddr + '\'' +
                ", cntnt='" + cntnt + '\'' +
                ", type='" + type + '\'' +
                ", date=" + date +
                '}';
    }
}
