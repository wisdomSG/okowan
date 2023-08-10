package com.teamproject.okowan.board;

import com.teamproject.okowan.aop.ApiResponseDto;
import com.teamproject.okowan.user.User;

import java.util.List;


public interface BoardService {

    List<BoardResponseDto> getBoardList();

    BoardResponseDto getBoard(Long BoardId);

    /**
     * Board에 초대된 보드 전체멤버 조회 메서드
     * @param BoardId 조회할 BoardId
     * @return 보드에 초대된 멤버 리스트
     */
    List<BoardWorkerResponseDto> getBoardMemberList(Long BoardId);

    /**
     * Board에 Board에 초대된 editor, owner 조회 메서드
     * @param BoardId 조회할 BoardId
     * @return Board에 초대된 editor, owner 리스트
     */
    List<BoardWorkerResponseDto> getBoardWorkerList(Long BoardId);

    ApiResponseDto createBoard(BoardRequestDto requestDto, User user);


    ApiResponseDto updateBoard(Long BoardId, BoardRequestDto requestDto, User user);


    ApiResponseDto deleteBoard(Long BoardId, User user);

    Board findBoard(Long BoardId);

}
