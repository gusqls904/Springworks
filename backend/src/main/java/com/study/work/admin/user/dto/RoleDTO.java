package com.study.work.admin.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "사용자 역할 DTO")
public class RoleDTO {

    @Schema(description = "역할 ID", example = "ROLE_ADMIN")
    private String roleId;

    @Schema(description = "역할 이름", example = "관리자")
    private String roleName;

    @Schema(description = "활성 여부 (0: 비활성, 1: 활성)", example = "1")
    private String isActive;
}
