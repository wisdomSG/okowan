package com.teamproject.okowan.card;

import com.teamproject.okowan.aop.ApiResponseDto;
import com.teamproject.okowan.awsS3.S3Service;
import com.teamproject.okowan.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/okw/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    private final S3Service s3Service;

    @PostMapping
    public ResponseEntity<ApiResponseDto> createCard(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CardRequestDto requestDto) {
        ApiResponseDto result = cardService.createCard(userDetails.getUser(), requestDto);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardResponseDto> getCard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CardResponseDto result = cardService.getCard(id, userDetails.getUser());
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CardResponseDto>> getCardFindByTitleList(@RequestParam("keyword") String keyword) {
        List<CardResponseDto> result = cardService.getCardFindByTitleList(keyword);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/s3FileDownload")
    public ResponseEntity<UrlResource> downloadS3File(String originalFilename) {
        return s3Service.downloadFile(originalFilename);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto> updateCard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CardRequestDto requestDto) {
        ApiResponseDto result = cardService.updateCard(id, userDetails.getUser(), requestDto);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/files/{id}")
    public ResponseEntity<ApiResponseDto> updateFileUpload(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestPart("fileName") List<MultipartFile> multipartFiles) {
        ApiResponseDto result = cardService.updateFileUpload(id, userDetails.getUser(), multipartFiles);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/deadLine/{id}")
    public ResponseEntity<ApiResponseDto> updateDeadLine(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CardRequestDto requestDto) {
        ApiResponseDto result = cardService.updateDeadLine(id, userDetails.getUser(), requestDto);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto> deleteCard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto result = cardService.deleteCard(id, userDetails.getUser());
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{cardId}/files/{fileId}")
    public ResponseEntity<ApiResponseDto> deleteFile(@PathVariable Long cardId, @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long fileId) {
        ApiResponseDto result = cardService.deleteFile(cardId, userDetails.getUser(), fileId);
        return ResponseEntity.ok().body(result);
    }

    @PatchMapping("/worker")
    public ResponseEntity<ApiResponseDto> saveWorker(@RequestParam("cardId") Long cardId, @RequestParam("worker") Long userId) {
        ApiResponseDto result = cardService.saveWorker(cardId, userId);
        return ResponseEntity.ok().body(result);
    }

}

