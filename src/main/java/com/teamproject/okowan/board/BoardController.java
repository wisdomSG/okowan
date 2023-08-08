package com.teamproject.okowan.board;

import com.teamproject.okowan.aop.ApiResponseDto;
import com.teamproject.okowan.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/okw/boards")
public class BoardController {    // 전체 보드 조회
    @GetMapping
    public ResponseEntity<List<BoardResponseDto>> getBoardList() {
        List<BoardResponseDto> responseDto = boardService.getBoardList();
        return ResponseEntity.ok().body(responseDto);
    }

    // 상세 보드 조회
    @GetMapping("/{BoardId}")
    public ResponseEntity<BoardResponseDto> getBoard(@PathVariable Long BoardId) {
        BoardResponseDto responseDto = boardService.getBoard(BoardId);
        return ResponseEntity.ok().body(responseDto);
    }

    private final BoardService boardService;

    // 보드 작성
    @PostMapping
    public ResponseEntity<ApiResponseDto> createBoard(@RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto responseDto = boardService.createBoard(requestDto,userDetails.getUser());
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

    // 보드 초대
//    @PostMapping("/{boardId}/invite")
//    public ResponseEntity<ApiResponseDto> inviteUserToBoard(
//            @PathVariable Long BoardId,
//            @RequestBody BoardInviteRequestDto boardInviteRequestDto) {
//        ApiResponseDto responseDto = boardService.inviteUserToBoard(BoardId, boardInviteRequestDto);
//        return ResponseEntity.ok().body(responseDto);
//    }
}
