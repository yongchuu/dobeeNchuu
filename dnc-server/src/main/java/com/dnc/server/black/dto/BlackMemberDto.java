package com.dnc.server.black.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class BlackMemberDto {
    int uuid;
    String dormNm;
    String dormOwner;
    String dormTel;
    String blackNm;
    String blackTel;
    String cntnt;
    Date date;

    @Override
    public String toString() {
        return "BlackMemberDto{" +
                "uuid=" + uuid +
                ", dormNm='" + dormNm + '\'' +
                ", dormOwner='" + dormOwner + '\'' +
                ", dormTel='" + dormTel + '\'' +
                ", blackNm='" + blackNm + '\'' +
                ", blackTel='" + blackTel + '\'' +
                ", cntnt='" + cntnt + '\'' +
                ", date=" + date +
                '}';
    }
}
