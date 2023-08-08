package com.teamproject.okowan.board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardResponseDto {

    private Long boardId;
    private String title;
    private String description;
    private String createdAt;
    private String modifiedAt;

    public BoardResponseDto(Board board) {
        this.title = board.getTitle();
        this.description = board.getDescription();
        this.createdAt = board.getCreatedAtFormatted();
        this.modifiedAt = board.getModifiedAtFormatted();
    }
//    private List<CategoryResponseDto> categoryResponseDtoList = new ArrayList<>();
//    private List<CardResponseDto> cardResponseDtoList = new ArrayList<>();
}
