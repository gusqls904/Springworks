package com.study.work.admin.board.svc;

import com.study.work.admin.board.dto.BoardDTO;


import java.util.List;

public interface BoardSVC {

    List<BoardDTO> getBoardList();

    List<BoardDTO> searchBoard(String keyword);

    void insertBoard(BoardDTO boardDTO);
}
