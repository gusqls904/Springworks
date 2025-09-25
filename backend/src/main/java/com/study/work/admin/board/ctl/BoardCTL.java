package com.study.work.admin.board.ctl;
import java.util.List;
import java.util.Map;

import com.study.work.common.dto.CommonResponseDTO;
import com.study.work.admin.board.dto.BoardDTO;
import com.study.work.admin.board.svc.BoardSVC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/board")
@Tag(name = "게시판 관리", description = "게시판 목록, 글쓰기, 조회 등을 관리하는 API")
public class BoardCTL {

    @Autowired
    private BoardSVC boardSVC;

    @Operation(summary = "게시판 목록 조회", description = "로그인 이후 게시판 기본 화면에서")
    @PostMapping("/getBoardList")
    public CommonResponseDTO<List<BoardDTO>> getBoards() {

        List<BoardDTO> boardList = boardSVC.getBoardList();

        return new CommonResponseDTO<List<BoardDTO>>(boardList);
    }

    @Operation(summary = "게시판 검색", description = "제목, 내용, 작성자 단일 키워드 입력 시")
    @PostMapping("/searchBoard")
    public CommonResponseDTO<List<BoardDTO>> searchBoard(@RequestBody Map<String, String> reqMap) {
        String keyword=reqMap.get("keyword");
        List<BoardDTO> boardList = boardSVC.searchBoard(keyword);

        return new CommonResponseDTO<List<BoardDTO>>(boardList);
    }

    @Operation(summary = "게시판 글쓰기", description = "게시판 창에서 글쓰기 클릭 시")
    @PostMapping("/insertBoard")
    public CommonResponseDTO<Void> insertBoard(@RequestBody BoardDTO boardDTO) {

        boardSVC.insertBoard(boardDTO);
        return new CommonResponseDTO<Void>();
    }


}