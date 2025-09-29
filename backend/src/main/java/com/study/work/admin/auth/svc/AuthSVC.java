package com.study.work.admin.auth.svc;


import com.study.work.admin.auth.dto.LoginResDTO;
import com.study.work.common.exception.BizException;

public interface AuthSVC {

	LoginResDTO login(String userId, String password) throws BizException;
}
