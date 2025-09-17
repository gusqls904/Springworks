package com.study.work.learning.board.svc;

import com.study.work.learning.board.dto.BoardDTO;


import java.util.List;

public interface BoardSVC {

    List<BoardDTO> getBoardList();

    List<BoardDTO> searchBoard(String keyword);
}
