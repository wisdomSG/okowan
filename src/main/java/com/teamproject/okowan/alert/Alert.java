package com.teamproject.okowan.alert;

import com.teamproject.okowan.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor // 기본생성자
@Table(name = "alerts")
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alert_id;

    private String board_title;

    private String column_title;

    private String card_title;

    private String card_description;

    @CreatedDate
    @Column(updatable = false) //최초 생성시간만 초기화 되고 그 뒤 수정될 수 없음
    private LocalDateTime alert_at;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
