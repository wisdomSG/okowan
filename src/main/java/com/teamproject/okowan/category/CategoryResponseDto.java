package com.teamproject.okowan.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryResponseDto {
    private Long categoryId;
    private String title;

    public CategoryResponseDto(Category category) {
        this.categoryId = category.getCategoryId();
        this.title = category.getTitle();
    }
}
