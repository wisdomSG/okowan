package com.teamproject.okowan.card;

import com.teamproject.okowan.entity.ColorEnum;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class CardSimpleResponseDto {
    private Long cardId;
    private String title;
    private ColorEnum color;
    private String workerName;
    private String deadline; // 문자열로 저장하기 위한 필드

    public CardSimpleResponseDto(Card card, String nickname) {
        this.cardId = card.getCardId();
        this.title = card.getTitle();
        this.color = card.getColor();
        this.workerName = nickname;

        // LocalDateTime을 "yyyy-MM-dd HH:mm" 형식의 문자열로 변환하여 저장
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.deadline = card.getDeadline().format(formatter); // deadline을 문자열로 변환하여 저장
    }
}
