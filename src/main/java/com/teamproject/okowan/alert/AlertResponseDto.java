package com.teamproject.okowan.alert;

import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class AlertResponseDto {
    private Long alertId;
    private String board_title;
    private String category_title;
    private String card_title;
    private String card_description;
    private String alert_at;

    public AlertResponseDto(Alert alert) {
        this.alertId = alert.getAlertId();
        this.board_title = alert.getBoard_title();
        this.category_title = alert.getCategory_title();
        this.card_title = alert.getCard_title();
        this.card_description = alert.getCard_description();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.alert_at = alert.getAlert_at().format(formatter);
    }

}
