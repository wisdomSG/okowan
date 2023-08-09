package com.teamproject.okowan.board;

import com.teamproject.okowan.aop.ApiResponseDto;
import com.teamproject.okowan.user.User;

import java.util.List;


public interface BoardService {

    List<BoardResponseDto> getBoardList();

    BoardResponseDto getBoard(Long BoardId);

    List<BoardWorkerResponseDto> getBoardWorkerList(Long BoardId);

    ApiResponseDto createBoard(BoardRequestDto requestDto, User user);


    ApiResponseDto updateBoard(Long BoardId, BoardRequestDto requestDto, User user);


    ApiResponseDto deleteBoard(Long BoardId, User user);

    Board findBoard(Long BoardId);

}
