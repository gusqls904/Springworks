package com.study.work.learning.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "회원 정보 DTO")
public class UserDTO {

    @Schema(description = "사용자 ID", example = "testuser01")
    private String userId;

    @Schema(description = "사용자 이름", example = "홍길동")
    private String userName;

    @Schema(description = "비밀번호 (암호화 저장)", example = "1234abcd!")
    private String password;

    @Schema(description = "이메일", example = "testuser01@example.com")
    private String email;

    @Schema(description = "사용자 역할", example = "admin")
    private String roleId;

//    @Schema(description = "가입일 (yyyy-MM-dd HH:mm:ss)", example = "2025-09-11 10:30:00")
//    private String createdDttm;
//
//    @Schema(description = "정보 수정일 (yyyy-MM-dd HH:mm:ss)", example = "2025-09-11 11:00:00")
//    private String updatedDttm;

    @Schema(description = "활성 여부 (0: 비활성, 1: 활성)", example = "1")
    private String isActive;
}
