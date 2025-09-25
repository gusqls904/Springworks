package com.study.work.admin.auth.svc;


import java.util.Map;

public interface AuthSVC {

    Map<String, Object> login(String userId, String password);
}
