package com.teamproject.okowan.awsS3;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public List<String> uploadFile(List<MultipartFile> files) {
        List<String> fileNames = new ArrayList<>();

        for (MultipartFile file : files) {
//            String fileName = file.getOriginalFilename();
            String fileName = UUID.randomUUID() + file.getOriginalFilename(); // fileName을 난수와 함께 저장
            String fileUrl = S3FileUpload(file, fileName);
            fileNames.add(fileUrl);
        }

        return fileNames;
    }

    public String S3FileUpload(MultipartFile file, String fileName) {
//        String fileUrl = "https://" + bucket +".s3." + ".amazonaws.com/" + fileName;
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        try {
            amazonS3.putObject(bucket, fileName, file.getInputStream(), metadata);
            return amazonS3.getUrl(bucket, fileName).toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("이미지의 크기가 너무 큽니다");
        }
    }

    public void deleteFiles(List<String> S3File) {
        try {
            for (String url : S3File) {
                String fileName = url.substring(url.lastIndexOf("/") + 1);
                amazonS3.deleteObject(bucket, fileName);
            }
        } catch (SdkClientException e) {
            throw new IllegalArgumentException("S3 파일을 삭제 중 문제 발생", e);
        }
    }


    public void deleteFile(String originalFilename) {
        String fileName = originalFilename.substring(originalFilename.lastIndexOf("/") + 1);
        amazonS3.deleteObject(bucket, fileName);
    }

    public ResponseEntity<UrlResource> downloadFile(String originalFilename) {
        UrlResource urlResource = new UrlResource(amazonS3.getUrl(bucket, originalFilename));

        String contentDisposition = "attachment; filename=\"" + originalFilename + "\"";

        // header에 CONTENT_DISPOSITION 설정을 통해 클릭 시 다운로드 진행
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(urlResource);

    }

}
