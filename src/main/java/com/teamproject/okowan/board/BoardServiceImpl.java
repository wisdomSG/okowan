package com.teamproject.okowan.board;

import com.teamproject.okowan.aop.ApiResponseDto;
import com.teamproject.okowan.user.UserRequestDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public ApiResponseDto getBoardList() {
        List<Board> boardList = boardRepository.findAll();
        List<BoardResponseDto> boardResponseDtoList = boardList.stream()
                .map(BoardResponseDto::new)
                .toList();
        return this.resultResponse(HttpStatus.OK,"보드 전체 조회",boardResponseDtoList);

    @Override
    public ApiResponseDto getBoard(id) {
        Board board = findBoard(id);
        BoardResponseDto responseDto = new BoardResponseDto(board);
        return this.resultResponse(HttpStatus.OK,"보드 상세 조회", responseDto);
    }

    @Override
    public ApiResponseDto createBoard (BoardRequestDto requestDto, User user) {
        Board board = new Board(requestDto,user);
        boardRepository.save(board);
        return this.resultResponse(HttpStatus.OK,"보드 생성 완료",new BoardResponseDto(board));
    }

    @Override
    @Transactional
    public ApiResponseDto updateBoard(Long id, BoardRequestDto requestDto, User user) {
        // 보드 유무 확인
        Board board = findBoard(id);

        // 보드 작성자인
        Long writerId = board.getUser().getId(); // 게시글 작성자 id
        Long loginId = user.getId(); // 현재 로그인한 id
        // 게시글 작성자가 아니고, 관리자도 아닐 경우
        if(!writerId.equals(loginId)){
            throw new IllegalArgumentException("작성자만 수정 할 수 있습니다.");
        }

        // 게시글 내용 수정
        board.update(requestDto);
        return this.resultResponse(HttpStatus.OK,"보드 수정 완료",new BoardResponseDto(board));
    }
        }

    @Override
    public ApiResponseDto deletePost(Long id, User user) {
        // 게시글이 있는지
        Board board = findBoard(id);
        // 게시글 작성자인지
        Long writerId = board.getUser().getId();
        Long loginId = user.getId();
        // 작성자가 아니고 관리자도 아님 경우 -> true && true
        // 작성자는 아니지만 관리자일 경우 -> 수정 가능. true && false
        if (!writerId.equals(loginId)) {
            throw new IllegalArgumentException("작성자만 삭제 할 수 있습니다.");
        }
        private ApiResponseDto resultResponse (HttpStatus status, String message, Object result){
            ApiResponseDto ApiResponseDto = new ApiResponseDto(status.value(), message, result);
            return new ResponseEntity<>(
                    ApiResponseDto,
                    status
            );
        }
    }

//    @Override
//    public User findUserByUsername(String username) {
//        return userRepository.findByUsername(username).orElseThrow(() ->
//                new IllegalArgumentException("존재하지 않는 유저입니다.")
//        );
//    }

    private ApiResponseDto resultResponse(HttpStatus status, String message, Object result) {
        ApiResponseDto ApiResponseDto = new ApiResponseDto(status.value(), message, result);
        return new ApiResponseDto;
    }
}
