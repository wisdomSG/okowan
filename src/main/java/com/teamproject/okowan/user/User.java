package com.teamproject.okowan.user;

import com.teamproject.okowan.alert.Alert;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor // 기본생성자
@EqualsAndHashCode
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String introduction;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Alert> alertList = new ArrayList<>();

    public User(String username, String password, String introduction) {
        this.username = username;
        this.password = password;
        this.introduction = introduction;
    }
}
