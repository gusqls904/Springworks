package com.study.work.admin.auth.ctl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.work.admin.auth.dto.LoginResDTO;
import com.study.work.admin.auth.svc.AuthSVC;
import com.study.work.common.dto.CommonResponseDTO;
import com.study.work.common.exception.BizException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
@RestController
@RequestMapping("/api/auth")
@Tag(name = "인증 관리", description = "인증 관리 API")
public class AuthCTL {

    @Autowired
    private AuthSVC authSVC;

    @Operation(summary = "로그인", description = "강의 시스템 페이지 로그인 버튼 클릭 시")
    @PostMapping("/login")
    public CommonResponseDTO<LoginResDTO> login(@RequestBody Map<String, String> reqMap) throws BizException{

    	// 요청 데이터 DTO로 변경
        String userId = reqMap.get("userId");
        String password = reqMap.get("password");
        
        LoginResDTO result = authSVC.login(userId, password);
        
        return new CommonResponseDTO<>(result);
    }


}
