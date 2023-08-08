package com.teamproject.okowan.comment;

import com.teamproject.okowan.aop.ApiResponseDto;
import com.teamproject.okowan.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/okw")
@RequiredArgsConstructor
public class CommentController {
    private final CommentServiceImpl commentService;

    @PostMapping("/comments/{card_id}")
    public ResponseEntity<ApiResponseDto> registComment(@PathVariable Long card_id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto apiResponseDto = commentService.registComment(card_id, commentRequestDto, userDetails);
        return ResponseEntity.ok().body(apiResponseDto);
    }

    @PutMapping("/comments/{comment_id}")
    public ResponseEntity<ApiResponseDto> updateComment(@PathVariable Long comment_id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto apiResponseDto = commentService.updateComment(comment_id, commentRequestDto, userDetails);
        return ResponseEntity.ok().body(apiResponseDto);
    }

    @DeleteMapping("/comments/{comment_id}")
    public ResponseEntity<ApiResponseDto> deleteComment(@PathVariable Long comment_id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto apiResponseDto = commentService.deleteComment(comment_id, userDetails);
        return ResponseEntity.ok().body(apiResponseDto);
    }
}
