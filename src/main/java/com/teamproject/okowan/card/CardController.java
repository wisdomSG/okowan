package com.teamproject.okowan.card;

import com.teamproject.okowan.aop.ApiResponseDto;
import com.teamproject.okowan.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/okw/card")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;
    @PostMapping
    public ResponseEntity<ApiResponseDto> createCard(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CardRequestDto requestDto) {
        ApiResponseDto result =  cardService.createCard(userDetails.getUser(), requestDto);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping
    public ResponseEntity<CardResponseDto> getCard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CardResponseDto result = cardService.getCard(id, userDetails.getUser());
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/worker")
    public ResponseEntity<ApiResponseDto> saveWorker(@RequestParam("cardId") Long cardId,@RequestParam("worker") Long userId) {
        ApiResponseDto result = cardService.saveWorker(cardId, userId);
        return ResponseEntity.ok().body(result);
    }


}
