package com.study.work.admin.auth.svc.impl;

import com.study.work.admin.auth.dao.AuthDAO;
import com.study.work.admin.auth.svc.AuthSVC;
import com.study.work.common.exception.BizException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class AuthSVCImpl implements AuthSVC {

    @Autowired
    private AuthDAO authDAO;


    @Override
    public Map<String, Object> login(String userId, String password) {
    	
    	Map<String, Object> userInfo = authDAO.login(userId, password);
    	
    	/**
    	 *  사용자 데이터 없을 경우 예외처리
    	 *  /backend/src/main/resources/exception/exception.yml -> 예외 리스트
    	 */
    	
    	if(userInfo == null) {
    		throw new BizException("user_not_found");
    	}
    	
        return userInfo;
    }
}
