package com.study.work.admin.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "로그인 응답 DTO")
public class LoginResDTO {

    @Schema(description = "사용자 ID", example = "testuser01")
    private String userId;

    @Schema(description = "사용자 이름", example = "홍길동")
    private String userName;
    
    @Schema(description = "비밀번호 (암호화 저장)", example = "1234abcd!")
    private String password;

    @Schema(description = "사용자 역할", example = "admin")
    private String roleId;
    
    @Schema(description = "사용자 역할", example = "admin")
    private String roleName;
    
    @Schema(description = "활성 여부 (0: 비활성, 1: 활성)", example = "1")
    private String isActive;

    @Schema(description = "로그인 실패 횟수", example = "0")
    private int loginFailCnt;

}
