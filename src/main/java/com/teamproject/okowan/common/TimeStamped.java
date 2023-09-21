package com.teamproject.okowan.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@MappedSuperclass // 엔티티 클래스에 공통된 매핑 정보를 정의하기 위함
@EntityListeners(AuditingEntityListener.class) //자동으로 시간을 넣어주는 기능 수행
public abstract class TimeStamped {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @CreatedDate
    @Column(updatable = false) //최초 생성시간만 초기화 되고 그 뒤 수정될 수 없음
    private LocalDateTime createdAt;

    @LastModifiedDate //변경될 때마다 시간 저장
    @Column
    private LocalDateTime modifiedAt;

    public String getCreatedAtFormatted() {
        return createdAt.format(FORMATTER);
    }

    public String getModifiedAtFormatted() {
        return modifiedAt.format(FORMATTER);
    }
}

