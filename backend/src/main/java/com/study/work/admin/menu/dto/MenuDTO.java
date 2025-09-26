package com.study.work.admin.menu.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "메뉴 정보 DTO")
public class MenuDTO {

    @Schema(description = "메뉴 ID", example = "1")
    private Long menuId;

    @Schema(description = "부모 메뉴 ID", example = "null")
    private Long parentId;

    @Schema(description = "메뉴 이름", example = "dashboard")
    private String name;

    @Schema(description = "메뉴 제목", example = "대시보드")
    private String title;

    @Schema(description = "메뉴 아이콘", example = "fas fa-tachometer-alt")
    private String icon;

    @Schema(description = "메뉴 설명", example = "대시보드")
    private String description;

    @Schema(description = "연결 컴포넌트 이름", example = "Dashboard")
    private String component;

    @Schema(description = "정렬 순서", example = "1")
    private Integer orderNo;

    @Schema(description = "활성화 여부 (Y/N)", example = "Y")
    private String isActive;

    @Schema(description = "생성 일시", example = "2025-09-22T08:34:35")
    private String createdDttm;

    @Schema(description = "수정 일시", example = "2025-09-22T08:34:35")
    private String updatedDttm;
}
