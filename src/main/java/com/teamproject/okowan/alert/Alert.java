package com.teamproject.okowan.alert;

import com.teamproject.okowan.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "alerts")
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alertId;

    private String board_title;

    private String category_title;

    private String card_title;

    private String card_description;

    @CreatedDate
    @Column(updatable = false) //최초 생성시간만 초기화 되고 그 뒤 수정될 수 없음
    private LocalDateTime alert_at;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Alert(AlertRequestDto alertRequestDto) {
        this.board_title = alertRequestDto.getBoard_title();
        this.category_title = alertRequestDto.getCategory_title();
        this.card_title = alertRequestDto.getCard_title();
        this.card_description = alertRequestDto.getCard_description();
    }

    public void setUser(User user) {
        this.user = user;
    }


}
