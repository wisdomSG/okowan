package com.teamproject.okowan.category;

import com.teamproject.okowan.card.CardSimpleResponseDto;
import lombok.Getter;

import java.util.List;

@Getter
public class CategoryDetailResponseDto {
    private Long categoryId;
    private String title;
    private List<CardSimpleResponseDto> cardSimpleResponseDtoList;

    public CategoryDetailResponseDto(Category category, List<CardSimpleResponseDto> cardSimpleResponseDtoList) {
        this.categoryId = category.getCategoryId();
        this.title = category.getTitle();
        this.cardSimpleResponseDtoList = cardSimpleResponseDtoList;
    }
}
