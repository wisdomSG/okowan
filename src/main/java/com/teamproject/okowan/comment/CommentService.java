package com.teamproject.okowan.comment;

import com.teamproject.okowan.common.ApiResponseDto;
import com.teamproject.okowan.security.UserDetailsImpl;

public interface CommentService {
    /* 댓글 등록 */
    ApiResponseDto registComment(Long cardId, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails);

    /* 댓글 수정 */
    ApiResponseDto updateComment(Long commentId, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails);

    /* 댓글 삭제 */
    ApiResponseDto deleteComment(Long commentId, UserDetailsImpl userDetails);

    /* 댓글 찾기 */
    Comment findComment(Long commentId);
}
