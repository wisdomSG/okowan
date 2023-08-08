package com.teamproject.okowan.card;

import com.teamproject.okowan.aop.ApiResponseDto;
import com.teamproject.okowan.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/okw/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

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

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto> updateCard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CardRequestDto requestDto) {
        ApiResponseDto result = cardService.updateCard(id, userDetails.getUser(), requestDto);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto> deleteCard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto result = cardService.deleteCard(id, userDetails.getUser());
        return ResponseEntity.ok().body(result);
    }

    @PatchMapping("/worker")
    public ResponseEntity<ApiResponseDto> saveWorker(@RequestParam("cardId") Long cardId, @RequestParam("worker") Long userId) {
        ApiResponseDto result = cardService.saveWorker(cardId, userId);
        return ResponseEntity.ok().body(result);
    }

}

