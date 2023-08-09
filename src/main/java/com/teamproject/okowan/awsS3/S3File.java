package com.teamproject.okowan.awsS3;

import com.teamproject.okowan.card.Card;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "s3files")
public class S3File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    private String fileName;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "cardId", nullable = false)
    private Card card;

    public S3File(String fileName) {
        this.fileName = fileName;
    }

    public S3File(String fileName, Card card) {
        this.fileName = fileName;
        this.card = card;
    }
}
