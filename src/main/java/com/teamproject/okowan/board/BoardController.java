package com.teamproject.okowan.board;

import com.teamproject.okowan.aop.ApiResponseDto;
import com.teamproject.okowan.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/okw/boards")
public class BoardController {    // 전체 보드 조회

    // 보드 리스트 조회
    @GetMapping
    public ResponseEntity<List<BoardResponseDto>> getBoardList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<BoardResponseDto> responseDto = boardService.getBoardList(userDetails.getUser());
        return ResponseEntity.ok().body(responseDto);
    }

    // 상세 보드 조회
    @GetMapping("/{BoardId}")
    public ResponseEntity<BoardResponseDto> getBoard(@PathVariable Long BoardId) {
        BoardResponseDto responseDto = boardService.getBoard(BoardId);
        return ResponseEntity.ok().body(responseDto);
    }

    private final BoardService boardService;

    // 보드 전체 멤버 조회
    @GetMapping("/member/{BoardId}")
    public ResponseEntity<List<BoardWorkerResponseDto>> getBoardMemberList(@PathVariable Long BoardId) {
        List<BoardWorkerResponseDto> result = boardService.getBoardMemberList(BoardId);
        return ResponseEntity.ok().body(result);
    }

    // 보드 작업자 조회
    @GetMapping("/worker/{BoardId}")
    public ResponseEntity<List<BoardWorkerResponseDto>> getBoardWorkerList(@PathVariable Long BoardId) {
        List<BoardWorkerResponseDto> result = boardService.getBoardWorkerList(BoardId);
        return ResponseEntity.ok().body(result);
    }

    // 보드 작성
    @PostMapping
    public ResponseEntity<ApiResponseDto> createBoard(@RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto responseDto = boardService.createBoard(requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(responseDto);
    }

    // 보드 수정
    @PutMapping("/{BoardId}")
    public @ResponseBody ResponseEntity<ApiResponseDto> updateBoard(
            @PathVariable Long BoardId,
            @Valid @RequestBody BoardRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto responseDto = boardService.updateBoard(BoardId, requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(responseDto);
    }

    // 보드 삭제
    @DeleteMapping("/{BoardId}")
    public @ResponseBody ResponseEntity<ApiResponseDto> deleteBoard(
            @PathVariable Long BoardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto responseDto = boardService.deleteBoard(BoardId, userDetails.getUser());
        return ResponseEntity.ok().body(responseDto);
    }

    // UserBoard 안에 사용자를 BoardId번째 보드의 view 또는 editor로 추가(초대)하기
    @PostMapping("/{BoardId}/invite")
    public @ResponseBody ResponseEntity<ApiResponseDto> inviteUserToBoard(@PathVariable Long BoardId, @Valid @RequestBody BoardInvitationRequestDto requestDto, BindingResult bindingResult, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(error.getDefaultMessage());
            }

            ApiResponseDto errorResponse = new ApiResponseDto(errorMessage.toString(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        ApiResponseDto responseDto = boardService.inviteUserToBoard(BoardId, requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(responseDto);
        // userDetails.getUsername()이 안되는 이유??
    }

    @PutMapping("/{BoardId}/invite/update")
    public @ResponseBody ResponseEntity<ApiResponseDto> updateUser(@PathVariable Long BoardId, @Valid @RequestBody BoardInvitationRequestDto requestDto, BindingResult bindingResult, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(error.getDefaultMessage());
            }

            ApiResponseDto errorResponse = new ApiResponseDto(errorMessage.toString(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        ApiResponseDto responseDto = boardService.updateUser(BoardId, requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(responseDto);
    }

    // 보드에 포함된 카테고리와 카드까지 조회
    @GetMapping("/contents/{boardId}")
    public ResponseEntity<BoardDetailResponseDto> getBoardContents(@PathVariable Long boardId) {
        BoardDetailResponseDto boardDetailResponseDto = boardService.getBoardContents(boardId);
        return ResponseEntity.ok().body(boardDetailResponseDto);
    }

}
