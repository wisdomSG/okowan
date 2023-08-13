package com.teamproject.okowan.card;

import com.teamproject.okowan.awsS3.S3File;
import lombok.Getter;

@Getter
public class S3FileResponseDto {
    private String fileName;
    private Long fileId;

    public S3FileResponseDto(S3File s3File) {
        this.fileName = s3File.getFileName();
        this.fileId = s3File.getFileId();
    }
}
