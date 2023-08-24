package com.dnc.server.black.dao;

import com.dnc.server.black.dto.BlackMemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BlackMemberDao {
    @Autowired
    JdbcTemplate jt;

    @Autowired
    @Qualifier(value = "blackMemberRowMapper")
    RowMapper<BlackMemberDto> rowMapper;

    public List<BlackMemberDto> getBlackList(){
        return jt.query("select * from black_list", rowMapper);
    }

    public List<BlackMemberDto> selectByNumber(String blackTel){
        return jt.query("select * from black_list where black_tel = ?", rowMapper, blackTel);
    }

    public int addBlackMember(BlackMemberDto dto) {
        return jt.update("insert into black_list (dorm_name, dorm_owner, dorm_tel, black_name, black_tel, cntnt, date)\n" +
                "values (?, ?, ?, ?, ?, ?, now())",dto.getDormNm(), dto.getDormOwner(), dto.getDormTel(), dto.getBlackNm(), dto.getBlackTel(), dto.getCntnt());
    }
}
