package com.study.work.admin.auth.dao;
import org.apache.ibatis.annotations.Mapper;

import com.study.work.admin.auth.dto.LoginResDTO;

@Mapper
public interface AuthDAO {

    /**
     * 아이디로 사용자 조회
     */
	LoginResDTO selectLoginInfo(String userId, String password);


}
