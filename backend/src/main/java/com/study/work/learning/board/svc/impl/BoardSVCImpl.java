package com.study.work.learning.board.svc.impl;

import com.study.work.learning.board.dao.BoardDAO;
import com.study.work.learning.board.dto.BoardDTO;
import com.study.work.learning.board.svc.BoardSVC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardSVCImpl implements BoardSVC {

    @Autowired
    private BoardDAO boardDAO;

    @Override
    public List<BoardDTO> getBoardList() {
        return boardDAO.getBoardList();
    }

    @Override
    public List<BoardDTO> searchBoard(String keyword) {
        return boardDAO.searchBoard(keyword);
    }

    @Override
    public void insertBoard(BoardDTO boardDTO) {
        boardDAO.insertBoard(boardDTO);
    }

    /*@Override
    public void updateBoard(BoardDTO boardDTO) {
        boardDAO.updateBoard(boardDTO);
    }

    @Override
    public void deleteBoard(Long boardId) {
        boardDAO.deleteBoard(boardId);
    }*/

}
