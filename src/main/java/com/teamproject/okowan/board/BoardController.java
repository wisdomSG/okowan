package com.teamproject.okowan.board;

import com.teamproject.okowan.aop.ApiResponseDto;
import com.teamproject.okowan.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/okw")
public class BoardController {

    private final BoardService boardService;

    // 전체 보드 조회
//    @GetMapping("/boards")
//    public @ResponseBody ResponseEntity<ApiResponseDto> getPostList() {
//        return boardService.getBoardList();
//    }
    @GetMapping("/boards")
    public ResponseEntity<ApiResponseDto> getBoardList() {
        ApiResponseDto responseDto = boardService.getBoardList();
        return ResponseEntity.ok().body(responseDto);
    }

    // 상세 보드 조회
//    @GetMapping("/board")
//    public @ResponseBody ResponseEntity<ApiResponseDto> getPostList() {
//        return boardService.getBoardList();
//    }
    @GetMapping("/board/{boardId}")
    public ResponseEntity<ApiResponseDto> getBoard(@PathVariable Long id) {
        ApiResponseDto responseDto = boardService.getBoard(id);
        return ResponseEntity.ok().body(responseDto);
    }


    // 보드 작성
//    @PostMapping("/board")
//    public ResponseEntity<ApiResponseDto> createPost(
//            @RequestBody BoardRequestDto requestDto,
//            @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        this.tokenValidate(userDetails);
//        return boardService.createBoard(requestDto,userDetails.getUser());
//    }
    @PostMapping("/board")
    public ResponseEntity<ApiResponseDto> createBoard(@RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        this.tokenValidate(userDetails);
        ApiResponseDto responseDto = boardService.createBoard(requestDto,userDetails.getUser());
        return ResponseEntity.ok().body(responseDto);
    }

    // 보드 수정
    @PutMapping("/board/{boardId}")
    public @ResponseBody ResponseEntity<ApiResponseDto> updateBoard(
            @PathVariable Long id,
            @Valid @RequestBody BoardRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        this.tokenValidate(userDetails);
        ApiResponseDto responseDto = boardService.updateBoard(id, requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(responseDto);
    }

    // 보드 삭제
    @DeleteMapping("/board/{boardId}")
    public @ResponseBody ResponseEntity<ApiResponseDto> deleteBoard(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        this.tokenValidate(userDetails);
        ApiResponseDto responseDto = boardService.deleteBoard(id, userDetails.getUser());
        return ResponseEntity.ok().body(responseDto);
    }

    // 보드 초대

    // 토큰 유효성
    public void tokenValidate(UserDetailsImpl userDetails) {
        try{
            userDetails.getUser();
        }catch (Exception ex){
            throw new TokenNotValidateException("토큰이 유효하지 않습니다.");
        }
    }

}
