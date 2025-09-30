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
    public LoginResDTO login(String userId, String password) throws BizException{

        LoginResDTO loginResDTO = authDAO.selectLoginInfo(userId,password);

        /**
         *  예외처리 목록
         *  /backend/src/main/resources/exception/exception.yml
         */
        if(loginResDTO == null) {
            throw new BizException("user_not_found");
        }

        if(loginResDTO.getIsActive().equals("0")) {
            throw new BizException("account_locked");
        }

        // 비밀번호 검증
        if (!loginResDTO.getPassword().equals(password)) {
            int loginFailCnt = loginResDTO.getLoginFailCnt() + 1;
            authDAO.updateFailCnt(userId, loginFailCnt);  // 실패 횟수 증가

            if (loginFailCnt >= 5) {
                authDAO.updateIsActive(userId,"0");  // 계정 잠금 처리
                throw new BizException("login_attempt_exceeded");
            }

            throw new BizException("invalid_credentials_count", loginFailCnt); // 5회 미만이면 단순 실패
        }

        // 로그인 성공 → 실패 카운트 초기화
        if (loginResDTO.getLoginFailCnt() > 0) {
            authDAO.updateFailCnt(userId, 0);
        }

        return loginResDTO;

    }
}
