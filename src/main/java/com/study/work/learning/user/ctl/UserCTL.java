package com.study.work.learning.user.ctl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.work.learning.user.dto.UserDTO;
import com.study.work.learning.user.svc.UserSVC;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/user")
@Tag(name = "회원 관리", description = "회원가입, 로그인 등 사용자 계정 관리 API")
public class UserCTL {

    @Autowired
    private UserSVC userSVC;

    /**
     * 회원가입 API
     */
    @Operation(summary = "회원가입", description = "사용자가 입력한 정보로 회원가입을 진행합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "회원가입 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/signup")
    public void signup(@RequestBody UserDTO userDto) {
        userSVC.signup(userDto);
    }
}
