package com.teamproject.okowan.board;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@Setter
public class BoardResponseDto {

    private Long id;
    private String title;
    private String content;
    private String username;
    private String nickname;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
//    private List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
}
