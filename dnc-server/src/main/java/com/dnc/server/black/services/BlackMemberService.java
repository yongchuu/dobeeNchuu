package com.dnc.server.black.services;

import com.dnc.server.black.dao.BlackMemberDao;
import com.dnc.server.black.dto.BlackMemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlackMemberService {
    @Autowired
    BlackMemberDao dao;

    public List<BlackMemberDto> getBlackList(){
        return dao.getBlackList();
    }

    public List<BlackMemberDto> selectByNumber(String blackTel){
        return dao.selectByNumber(blackTel);
    }

    public int addBlackMember(BlackMemberDto dto) {
        return dao.addBlackMember(dto);
    }
}
