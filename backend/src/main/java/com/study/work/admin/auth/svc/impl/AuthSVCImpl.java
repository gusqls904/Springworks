package com.study.work.admin.auth.svc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.work.admin.auth.dao.AuthDAO;
import com.study.work.admin.auth.dto.LoginResDTO;
import com.study.work.admin.auth.svc.AuthSVC;
import com.study.work.common.exception.BizException;

@Service
public class AuthSVCImpl implements AuthSVC {

    @Autowired
    private AuthDAO authDAO;


    @Override
    public LoginResDTO login(String userId, String password) {
    	
    	LoginResDTO loginResDTO = authDAO.login(userId, password);
    	
    	/**
    	 *  예외처리 목록
    	 *  /backend/src/main/resources/exception/exception.yml
    	 */
    	if(loginResDTO == null) {
    		throw new BizException("user_not_found");
    	}
    	
    	if(!loginResDTO.getPassword().equals(password)) {
    		throw new BizException("invalid_credentials");
    	}
    	
    	if(loginResDTO.getIsActive().equals("0")) {
    		throw new BizException("account_locked");
    	}
    	
        return loginResDTO;
    }
}
