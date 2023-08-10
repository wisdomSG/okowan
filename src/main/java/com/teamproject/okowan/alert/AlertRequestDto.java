package com.teamproject.okowan.alert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AlertRequestDto {
    private String board_title;
    private String category_title;
    private String card_title;
    private String card_description;
    private Long workerId;
}
