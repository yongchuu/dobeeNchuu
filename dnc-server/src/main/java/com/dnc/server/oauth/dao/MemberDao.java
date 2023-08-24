package com.dnc.server.oauth.dao;

import com.dnc.server.oauth.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberDao {
    @Autowired
    JdbcTemplate jt;

    @Autowired
    @Qualifier(value = "memberRowMapper")
    RowMapper<MemberDto> rowMapper;

    public List<MemberDto> getMemberList(){
        return jt.query("select * from member", rowMapper);
    }

    public List<MemberDto> getMemberByEmail(String email){
        return jt.query("select * from member where email = ?", rowMapper, email);
    }

    public int addMember(MemberDto dto) {
        return jt.update("insert into member (email, dorm_name, dorm_addr, dorm_owner, dorm_tel, cntnt, date)\n" +
                "values (?, ?, ?, ?, ?, ?, now())", dto.getEmail(), dto.getDormNm(), dto.getDormAddr(), dto.getDormOwner(), dto.getDormTel(), dto.getCntnt());
    }
}
