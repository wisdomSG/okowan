package com.teamproject.okowan.card;

import com.teamproject.okowan.aop.ApiResponseDto;
import com.teamproject.okowan.category.Category;
import com.teamproject.okowan.user.User;
import lombok.extern.java.Log;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CardService {

    ApiResponseDto createCard(User user, CardRequestDto requestDto);

    CardResponseDto getCard(Long id, User user);

    List<CardResponseDto> getCardFindByTitleList(String keyword);

    ApiResponseDto updateCard(Long id, User user, CardRequestDto requestDto);

    ApiResponseDto updateFileUpload(Long id, User user, List<MultipartFile> multipartFiles);

    ApiResponseDto updateDeadLine(Long id, User user, CardRequestDto requestDto);

    ApiResponseDto deleteCard(Long id, User user);

    ApiResponseDto deleteFile(Long cardId, User user, Long fileId);

    ApiResponseDto saveWorker(Long cardId, Long userId);

    Card findCard(Long id);

}
