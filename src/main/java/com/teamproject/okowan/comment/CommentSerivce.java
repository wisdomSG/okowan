package com.teamproject.okowan.comment;

import com.teamproject.okowan.aop.ApiResponseDto;
import com.teamproject.okowan.security.UserDetailsImpl;

public interface CommentSerivce {
    /* 댓글 등록 */
    ApiResponseDto registComment(Long card_id, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails);

    /* 댓글 수정 */
    ApiResponseDto updateComment(Long comment_id, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails);

    /* 댓글 삭제 */
    ApiResponseDto deleteComment(Long comment_id, UserDetailsImpl userDetails);
}
