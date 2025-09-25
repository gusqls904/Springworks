package com.study.work.admin.auth.svc.impl;

import com.study.work.admin.auth.dao.AuthDAO;
import com.study.work.admin.auth.svc.AuthSVC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class AuthSVCImpl implements AuthSVC {

    @Autowired
    private AuthDAO authDAO;


    @Override
    public Map<String, Object> login(String userId, String password) {
        return authDAO.login(userId, password);
    }
}
