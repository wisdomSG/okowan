package com.teamproject.okowan.board;

import com.teamproject.okowan.common.ApiResponseDto;
import com.teamproject.okowan.user.User;

import java.util.List;


public interface BoardService {

    List<BoardResponseDto> getBoardList(User user);

    BoardResponseDto getBoard(Long BoardId);

    /**
     * Board에 초대된 보드 전체멤버 조회 메서드
     *
     * @param BoardId 조회할 BoardId
     * @return 보드에 초대된 멤버 리스트
     */
    List<BoardWorkerResponseDto> getBoardMemberList(Long BoardId);

    /**
     * Board에 Board에 초대된 editor, owner 조회 메서드
     *
     * @param BoardId 조회할 BoardId
     * @return Board에 초대된 editor, owner 리스트
     */
    List<BoardWorkerResponseDto> getBoardWorkerList(Long BoardId);

    ApiResponseDto createBoard(BoardRequestDto requestDto, User user);


    ApiResponseDto updateBoard(Long BoardId, BoardRequestDto requestDto, User user);


    ApiResponseDto deleteBoard(Long BoardId, User user);

    Board findBoard(Long BoardId);

    ApiResponseDto inviteUserToBoard(Long BoardId, BoardInvitationRequestDto requestDto, User user);

    ApiResponseDto updateUser(Long BoardId, BoardInvitationRequestDto requestDto, User user);

    /**
     * 보드의 카테고리와 카드를 조회
     *
     * @param boardId 조회할 보드 ID
     * @return 조회한 데이터
     */
    BoardDetailResponseDto getBoardContents(Long boardId);
}