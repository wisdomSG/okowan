package com.teamproject.okowan.board;

import com.teamproject.okowan.aop.ApiResponseDto;
import com.teamproject.okowan.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/okw")
public class BoardController {

    private final BoardService boardService;

    // 전체 보드 조회
    @GetMapping("/boards")
    public @ResponseBody ResponseEntity<ApiResponseDto> getPostList() {
        return boardService.getBoardList();
    }

    // 상세 보드 조회
    @GetMapping("/board")
    public @ResponseBody ResponseEntity<ApiResponseDto> getPostList() {
        return boardService.getBoardList();
    }


    // 보드 작성
    @PostMapping("/board")
    public @ResponseBody ResponseEntity<ApiResponseDto> createPost(
            @Valid @RequestBody BoardRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        this.tokenValidate(userDetails);
        return boardService.createBoard(requestDto,userDetails.getUser());

    }

    // 보드 수정
    @PutMapping("/board/{boardId}")
    public @ResponseBody ResponseEntity<ApiResponseDto> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody BoardRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        this.tokenValidate(userDetails);
        return boardService.updateBoard(id, requestDto, userDetails.getUser());
    }

    // 보드 삭제
    @DeleteMapping("/board/{boardId}")
    public @ResponseBody ResponseEntity<ApiResponseDto> deletePost(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        this.tokenValidate(userDetails);
        return boardService.deleteBoard(id, userDetails.getUser());
    }

    // 보드 초대

    public void tokenValidate(UserDetailsImpl userDetails) {
        try{
            userDetails.getUser();
        }catch (Exception ex){
            throw new TokenNotValidateException("토큰이 유효하지 않습니다.");
        }
    }

}
