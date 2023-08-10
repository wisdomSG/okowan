package com.teamproject.okowan.user;

import com.teamproject.okowan.alert.Alert;
import com.teamproject.okowan.oauth.OAuthProvider;
import jakarta.persistence.*;
import lombok.Builder;
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
    private String nickname;

    @Column(nullable = false)
    private String introduction;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OAuthProvider oAuthProvider;

    private String address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Alert> alertList = new ArrayList<>();

    @Builder
    public User(String username, String password,String nickname, String introduction, OAuthProvider oAuthProvider) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.introduction = introduction;
        this.oAuthProvider = oAuthProvider;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setoAuthProvider(OAuthProvider OauthProvider) {
        this.oAuthProvider = OauthProvider;
    }
}
