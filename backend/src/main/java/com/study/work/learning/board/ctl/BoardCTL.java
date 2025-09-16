package com.study.work.learning.board.ctl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.study.work.common.dto.CommonResponseDTO;
import com.study.work.learning.board.dto.BoardDTO;
import com.study.work.learning.board.svc.BoardSVC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/board")
@Tag(name = "게시판 관리", description = "게시판 목록, 글쓰기, 조회 등을 관리하는 API")
public class BoardCTL {

    @Autowired
    private BoardSVC boardSVC;

    @Operation(summary = "게시판 목록 조회", description = "-")
    @PostMapping("/getBoardList")
    public CommonResponseDTO<List<BoardDTO>> getBoards() {

        List<BoardDTO> boardList = boardSVC.getBoardList();

        return new CommonResponseDTO<List<BoardDTO>>(boardList);
    }


}