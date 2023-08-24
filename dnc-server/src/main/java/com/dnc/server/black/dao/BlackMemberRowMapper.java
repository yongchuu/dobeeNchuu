package com.dnc.server.black.dao;

import com.dnc.server.black.dto.BlackMemberDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class BlackMemberRowMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        BlackMemberDto dto = new BlackMemberDto();

        dto.setUuid(rs.getInt("uuid"));
        dto.setDormNm(rs.getString("dorm_name"));
        dto.setDormOwner(rs.getString("dorm_owner"));
        dto.setDormTel(rs.getString("dorm_tel"));
        dto.setBlackNm(rs.getString("black_name"));
        dto.setBlackTel(rs.getString("black_tel"));
        dto.setCntnt(rs.getString("cntnt"));
        dto.setDate(rs.getDate("date"));

        return dto;
    }
}
