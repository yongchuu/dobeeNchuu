package com.dnc.server.oauth.dao;

import com.dnc.server.black.dto.BlackMemberDto;
import com.dnc.server.oauth.dto.MemberDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class MemberRowMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        MemberDto dto = new MemberDto();

        dto.setUuid(rs.getInt("uuid"));
        dto.setDormNm(rs.getString("dorm_name"));
        dto.setDormOwner(rs.getString("dorm_owner"));
        dto.setDormTel(rs.getString("dorm_tel"));
        dto.setEmail(rs.getString("email"));
        dto.setDormAddr(rs.getString("dorm_addr"));
        dto.setCntnt(rs.getString("cntnt"));
        dto.setDate(rs.getDate("date"));
        dto.setType(rs.getString("type"));

        return dto;
    }
}
