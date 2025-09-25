package com.study.work.admin.auth.dao;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface AuthDAO {

    /**
     * 아이디로 사용자 조회
     */
    Map<String, Object> login(String userId, String password);


}
