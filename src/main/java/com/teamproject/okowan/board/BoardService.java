package com.teamproject.okowan.board;

import com.teamproject.okowan.aop.ApiResponseDto;
import com.teamproject.okowan.security.UserDetailsImpl;
import com.teamproject.okowan.user.User;
import com.teamproject.okowan.user.UserRequestDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface BoardService {
    /**
     * 보드 전체 조회
     *
     * @return
     */
    ApiResponseDto getBoardList();

    /**
     * 보드 상세 조회
     *
     * @return
     */
    ApiResponseDto getBoard(Long board_id);

    /**
     * 보드 생성
     *
     * @param userRequestDto
     * @return
     */
    ApiResponseDto createBoard(UserRequestDto userRequestDto, User user);

    /**
     * 보드 수정
     *
     * @param requestDto
     * @param userDetails
     * @return
     */
    ApiResponseDto updateBoard(Long id, BoardRequestDto requestDto, UserDetailsImpl userDetails);

    /**
     * 보드 삭제
     *
     * @param id
     * @param userDetails
     * @return
     */
    ApiResponseDto deleteBoard(Long id, UserDetailsImpl userDetails);

    /**
     * ++ 수정 필요
     *
     * @param id
     * @return
     */
    Board findBoard(Long id);


    /**
     * 이메일로 유저 조회
     *
     * @param username 조회할 유저 이메일
     * @return 조회한 유저
     */
    User findUserByUsername(String username);
}
