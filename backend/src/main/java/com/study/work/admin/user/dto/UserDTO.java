package com.study.work.admin.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "회원 정보 DTO (TB_USER 기준)")
public class UserDTO {

    @Schema(description = "사용자 순번", example = "1")
    private Long userSeq;

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

    @Schema(description = "가입일", example = "2025-09-11T10:30:00")
    private LocalDateTime createdDttm;

    @Schema(description = "정보 수정일", example = "2025-09-11T11:00:00")
    private LocalDateTime updatedDttm;

    @Schema(description = "활성 여부 (0: 비활성, 1: 활성)", example = "1")
    private Integer isActive;
}
