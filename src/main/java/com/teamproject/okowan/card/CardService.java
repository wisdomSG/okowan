package com.teamproject.okowan.card;

import com.teamproject.okowan.aop.ApiResponseDto;
import com.teamproject.okowan.category.Category;
import com.teamproject.okowan.user.User;

public interface CardService {

    ApiResponseDto createCard(User user, CardRequestDto requestDto);

    CardResponseDto getCard(Long id, User user);

    ApiResponseDto updateCard(Long id, User user, CardRequestDto requestDto);

    ApiResponseDto deleteCard(Long id, User user);

    ApiResponseDto saveWorker(Long cardId, Long userId);

    Card findCard(Long id);

}
