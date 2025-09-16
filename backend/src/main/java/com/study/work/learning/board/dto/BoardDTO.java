package com.study.work.learning.board.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "게시판 정보 DTO")
public class BoardDTO {

    @Schema(description = "게시글 ID", example = "1")
    private Long boardId;

    @Schema(description = "제목", example = "공지사항 제목")
    private String title;

    @Schema(description = "내용", example = "공지사항 내용")
    private String content;

    @Schema(description = "작성자", example = "홍길동")
    private String writer;

    @Schema(description = "조회수", example = "100")
    private Integer viewCount;

    @Schema(description = "생성일시", example = "2025-09-16T13:00:00")
    private LocalDateTime createdDttm;

    @Schema(description = "수정일시", example = "2025-09-16T14:00:00")
    private LocalDateTime updatedDttm;

    @Schema(description = "삭제 여부 (0: 미삭제, 1: 삭제)", example = "0")
    private String isDeleted;
}
