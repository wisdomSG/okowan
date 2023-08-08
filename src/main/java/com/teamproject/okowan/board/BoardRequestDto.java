package com.teamproject.okowan.board;

import com.teamproject.okowan.entity.ColorEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class BoardRequestDto {
    @NotBlank(message = "제목이 입력이 안되었습니다.")
    private String title;

    private ColorEnum color;

    @NotBlank(message = "내용이 입력이 안되었습니다.")
    private String description;

}
