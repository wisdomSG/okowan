package com.teamproject.okowan.board;

import com.teamproject.okowan.aop.ApiResponseDto;
import com.teamproject.okowan.entity.BoardRoleEnum;
import com.teamproject.okowan.user.User;
import com.teamproject.okowan.user.UserService;
import com.teamproject.okowan.userBoard.UserBoard;
import com.teamproject.okowan.userBoard.UserBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserBoardRepository userBoardRepository;
    private final UserService userService;

    @Override
    public List<BoardResponseDto> getBoardList() {
        List<Board> boardList = boardRepository.findAll();
        List<BoardResponseDto> boardResponseDtoList = boardList.stream()
                .map(BoardResponseDto::new)
                .toList();

        return boardResponseDtoList;
    }

    @Override
    public BoardResponseDto getBoard(Long BoardId) {
        Board board = findBoard(BoardId);
        return new BoardResponseDto(board);
    }

    @Override
    public List<BoardWorkerResponseDto> getBoardWorkerList(Long BoardId) {

        List<BoardWorkerResponseDto> boardWorkerList = userBoardRepository.getAllFindByBoardId(BoardId).stream()
                .map(BoardWorkerResponseDto::new)
                .toList();
        return boardWorkerList;
    }

    @Override
    public ApiResponseDto createBoard(BoardRequestDto requestDto, User user) {

        Board board = Board.builder()
                .title(requestDto.getTitle())
                .color(requestDto.getColor())
                .description(requestDto.getDescription())
                .build();

        boardRepository.save(board);

        UserBoard userBoard = new UserBoard(BoardRoleEnum.OWNER, user, board);
        userBoardRepository.save(userBoard);

        return new ApiResponseDto("보드 생성 완료", HttpStatus.OK.value());
    }

    @Override
    @Transactional
    public ApiResponseDto updateBoard(Long BoardId, BoardRequestDto requestDto, User user) {
        // 보드 유무 확인
        Board board = findBoard(BoardId);

        try {
            userBoardRepository.findByBoardAndUserAndRole(board, user, BoardRoleEnum.OWNER)
                    .orElseThrow(() -> new RejectedExecutionException("해당 보드의 소유주가 아닙니다."));
        } catch (RejectedExecutionException e) {
            return new ApiResponseDto("수정이 불가합니다.", HttpStatus.BAD_REQUEST.value());
        }

        // 보드 내용 수정
        board.setTitle(requestDto.getTitle());
        board.setDescription(requestDto.getDescription());
        board.setColor(requestDto.getColor());

        return new ApiResponseDto("보드 수정 완료", HttpStatus.OK.value());
    }

    @Override
    public ApiResponseDto deleteBoard(Long BoardId, User user) {
        // 보드가 있는지
        Board board = findBoard(BoardId);

        // 보드 작성자인지
        try {
            userBoardRepository.findByBoardAndUserAndRole(board, user, BoardRoleEnum.OWNER)
                    .orElseThrow(() -> new RejectedExecutionException("해당 보드의 소유주가 아닙니다."));
        } catch (RejectedExecutionException e) {
            return new ApiResponseDto("삭제가 불가합니다.", HttpStatus.BAD_REQUEST.value());
        }
        boardRepository.delete(board);
        return new ApiResponseDto("보드 삭제 성공.", HttpStatus.OK.value());
    }

    @Override
    public Board findBoard(Long BoardId) {
        return boardRepository.findById(BoardId).orElseThrow(() -> new IllegalArgumentException("선택한 보드는 존재하지 않습니다."));
    }

    // 보드에 사용자 초대
    @Override
    public ApiResponseDto inviteUserToBoard(Long BoardId, BoardInvitationRequestDto requestDto, User user) {
    // BoardInvitationRequestDto requestDto 초대되는 사람의 정보
    // User user 초대하는 사람의 정보

        Board board = findBoard(BoardId); // 몇 번째 보드인지 찾기
        User inviteUser = userService.findUserByUsername(requestDto.getUsername()); // 초대되는 사람의 정보를 USER 디비에서 찾기
        UserBoard userBoard = new UserBoard(requestDto.getRole(), inviteUser, board); // 초대되는 사람의 정보를 userBoard로 저장
        userBoardRepository.findByBoardAndUserAndRole(board, user, BoardRoleEnum.OWNER)
                .orElseThrow(() -> new RejectedExecutionException("해당 보드의 소유주가 아닙니다.")); // 초대하는 사람의 role이 OWNER인지 확
        userBoardRepository.save(userBoard); // UserBoard에 초대되는 사람의 정보를 저장
        return new ApiResponseDto("초대 성공", HttpStatus.OK.value());
    }

    //초대된 사용자의 권한 수정
    @Override
    @Transactional
    public ApiResponseDto updateUser(Long BoardId, BoardInvitationRequestDto requestDto, User user) {
        Board board = findBoard(BoardId); // 몇 번째 보드인지 찾기
        User inviteUser = userService.findUserByUsername(requestDto.getUsername()); // 초대되는 사람의 정보를 USER 디비에서 찾기
        UserBoard userBoard = userBoardRepository.findByBoardAndUser(board, inviteUser)
                .orElseThrow(() -> new RejectedExecutionException("사용자가 존재하지 않습니다."));  // 초대된 사용자와 관련된 UserBoard 엔티티 찾기
//
        userBoardRepository.findByBoardAndUserAndRole(board, user, BoardRoleEnum.OWNER)
                .orElseThrow(() -> new RejectedExecutionException("해당 보드의 소유주가 아닙니다.")); // 초대하는 사람의 role이 OWNER인지 확인

        userBoard.setRole(requestDto.getRole());

        return new ApiResponseDto("권한 수정 완료", HttpStatus.OK.value());
    }
}

