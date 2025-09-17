package com.study.work.learning.board.dao;

import com.study.work.learning.board.dto.BoardDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface BoardDAO {

    List<BoardDTO> getBoardList();

    List<BoardDTO> searchBoard(String keyword);

    void insertBoard(BoardDTO boardDTO);


//
//    void updateBoard(BoardDTO boardDTO);
//
//    void deleteBoard(Long boardId);
}
