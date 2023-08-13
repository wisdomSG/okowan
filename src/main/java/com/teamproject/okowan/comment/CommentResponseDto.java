package com.teamproject.okowan.comment;

import lombok.Getter;

@Getter
public class CommentResponseDto {

    private Long commentId;
    private String content;
    private String nickname;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
        this.nickname = comment.getUser().getNickname();
    }
}
