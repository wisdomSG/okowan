package com.teamproject.okowan.card;

import com.teamproject.okowan.aop.ApiResponseDto;
import com.teamproject.okowan.category.Category;
import com.teamproject.okowan.category.CategoryService;
import com.teamproject.okowan.user.User;
import com.teamproject.okowan.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    private final CategoryService categoryService;

    private final UserService userService;

    @Override
    public ApiResponseDto createCard(User user, CardRequestDto requestDto) {
        Category category = categoryService.findByIdCategory(requestDto.getCategoryId());

        // "yyyy-MM-dd HH:mm"과 같은 형식의 문자열을 deadlineStr 필드로 요청하면, 서버에서는 deadlineStr을 LocalDateTime으로 변환하여 Card 엔티티의 deadline 필드에 저장
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); // "yyyy-MM-dd HH:mm" 이러한 형식, 문자열로 deadline을 받아옴
        LocalDateTime deadline = LocalDateTime.parse(requestDto.getDeadlineStr(), formatter); // LocalDateTime으로 변환하여 데이터베이스에 저장할 예정

        Card card = new Card(requestDto.getTitle(), requestDto.getDescription(), requestDto.getColor(), deadline, category);

        cardRepository.save(card);

        return new ApiResponseDto("카드 생성완료", HttpStatus.OK.value());
    }

    @Override
    public CardResponseDto getCard(Long id, User user) {
        Card card = findCard(id);

        return new CardResponseDto(card);
    }

    @Override
    public List<CardResponseDto> getCardFindByTitleList(String keyword) {
        List<CardResponseDto> cardList = cardRepository.getCardFindByTitleList(keyword)
                .stream()
                .map(CardResponseDto::new)
                .toList();
        return cardList;
    }

    @Override
    @Transactional
    public ApiResponseDto updateCard(Long id, User user, CardRequestDto requestDto) {
        Card card = findCard(id);

        card.setTitle(requestDto.getTitle());
        card.setDescription(requestDto.getDescription());
        card.setColor(requestDto.getColor());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); // "yyyy-MM-dd HH:mm" 이러한 형식, 문자열로 deadline을 받아옴
        LocalDateTime deadline = LocalDateTime.parse(requestDto.getDeadlineStr(), formatter); // LocalDateTime으로 변환하여 데이터베이스에 저장할 예정
        card.setDeadline(deadline);

        Category category = categoryService.findByIdCategory(requestDto.getCategoryId());
        card.setCategory(category);

        return new ApiResponseDto("카드 수정 완료", HttpStatus.OK.value());
    }

    @Override
    public ApiResponseDto deleteCard(Long id, User user) {
        Card card = findCard(id);

        cardRepository.delete(card);

        return new ApiResponseDto("카드 삭제 완료", HttpStatus.OK.value());
    }

    @Override
    @Transactional
    public ApiResponseDto saveWorker(Long cardId,Long userId) {
        Card card = findCard(cardId);
        User user = userService.findUserById(userId);

        // if문 user가 card에 있는 board에 권한이 있는지 한번더 확인

        card.setUser(user);

        return new ApiResponseDto("작업자 등록완료", HttpStatus.OK.value());
    }

    @Override
    public Card findCard(Long id) {
        return cardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("카드를 찾을 수 없습니다.")
        );
    }
}
