package com.teamproject.okowan.board;

import com.teamproject.okowan.category.CategoryDetailResponseDto;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BoardDetailResponseDto {
    private Long boardId;
    private String title;
    private String description;

    private List<CategoryDetailResponseDto> categoryDetailResponseDtoList = new ArrayList<>();

    public BoardDetailResponseDto(Board board, List<CategoryDetailResponseDto> categoryDetailResponseDtoList) {
        this.boardId = board.getBoardId();
        this.title = board.getTitle();
        this.description = board.getDescription();
        this.categoryDetailResponseDtoList = categoryDetailResponseDtoList;
    }
}