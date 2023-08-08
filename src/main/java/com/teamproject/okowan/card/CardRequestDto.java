package com.teamproject.okowan.card;

import com.teamproject.okowan.entity.ColorEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardRequestDto {
    private Long categoryId;
    private String title;
    private String description;
    private ColorEnum color;
    private String deadlineStr;
}
