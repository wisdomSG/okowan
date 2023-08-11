package com.teamproject.okowan.card;

import com.teamproject.okowan.alert.AlertRequestDto;
import com.teamproject.okowan.alert.AlertService;
import com.teamproject.okowan.aop.ApiResponseDto;
import com.teamproject.okowan.awsS3.S3File;
import com.teamproject.okowan.awsS3.S3FileRepository;
import com.teamproject.okowan.awsS3.S3Service;
import com.teamproject.okowan.category.Category;
import com.teamproject.okowan.category.CategoryService;
import com.teamproject.okowan.entity.BoardRoleEnum;
import com.teamproject.okowan.user.User;
import com.teamproject.okowan.user.UserService;
import com.teamproject.okowan.userBoard.UserBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    private final CategoryService categoryService;

    private final UserService userService;

    private final S3Service s3Service;

    private final S3FileRepository s3FileRepository;

    private final AlertService alertService;

    private final UserBoardRepository userBoardRepository;

    @Override
    public ApiResponseDto createCard(User user, CardRequestDto requestDto) { //Title 만 create
        Category category = categoryService.findCategory(requestDto.getCategoryId());

        // "yyyy-MM-dd HH:mm"과 같은 형식의 문자열을 deadlineStr 필드로 요청하면, 서버에서는 deadlineStr을 LocalDateTime으로 변환하여 Card 엔티티의 deadline 필드에 저장
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); // "yyyy-MM-dd HH:mm" 이러한 형식, 문자열로 deadline을 받아옴
        LocalDateTime deadline = LocalDateTime.parse(requestDto.getDeadlineStr(), formatter); // LocalDateTime으로 변환하여 데이터베이스에 저장할 예정

        Card card = new Card(requestDto.getTitle(), requestDto.getDescription(), requestDto.getColor(), deadline, category, user);

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

        Category category = categoryService.findCategory(requestDto.getCategoryId());
        card.setCategory(category);

        return new ApiResponseDto("카드 수정 완료", HttpStatus.OK.value());
    }

    @Override
    @Transactional
    public ApiResponseDto updateFileUpload(Long id, User user, List<MultipartFile> multipartFiles) {
        Card card = findCard(id);

        // AWS에 파일 저장
        List<String> filePaths = s3Service.uploadFile(multipartFiles);

        for (String fileUrl : filePaths) {
            S3File file = new S3File(fileUrl, card);
            s3FileRepository.save(file);
        }

        return new ApiResponseDto("카드에 파일 추가 완료", HttpStatus.OK.value());
    }

    @Override
    @Transactional
    public ApiResponseDto updateDeadLine(Long id, User user, CardRequestDto requestDto) {
        Card card = findCard(id);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); // "yyyy-MM-dd HH:mm" 이러한 형식, 문자열로 deadline을 받아옴
        LocalDateTime deadline = LocalDateTime.parse(requestDto.getDeadlineStr(), formatter); // LocalDateTime으로 변환하여 데이터베이스에 저장할 예정
        card.setDeadline(deadline);

        return new ApiResponseDto("데드라인 수정 완료", HttpStatus.OK.value());
    }

    // 카드 삭제 (파일 삭제 포함)
    @Override
    @Transactional
    public ApiResponseDto deleteCard(Long id, User user) {
        Card card = findCard(id);


        for(S3File s3File : card.getS3FileList()) {
            s3Service.deleteFile(s3File.getFileName());
        }

        cardRepository.delete(card);

        return new ApiResponseDto("카드 삭제 완료", HttpStatus.OK.value());
    }

    // 파일 삭제
    @Override
    public ApiResponseDto deleteFile(Long cardId, User user, Long fileId) {
        Card card = findCard(cardId);

        S3File s3File = s3FileRepository.findById(fileId).orElseThrow(() -> {
            throw new IllegalArgumentException("삭제할 파일이 없음");
        });

        s3FileRepository.delete(s3File);
        s3Service.deleteFile(s3File.getFileName());

        return new ApiResponseDto("첨부 파일 삭제 완료", HttpStatus.OK.value());
    }

    @Override
    @Transactional
    public ApiResponseDto saveWorker(Long cardId, Long userId) {
        User user = userService.findUserById(userId);   //worker
        Card card = findCard(cardId);

        Optional<BoardRoleEnum> roleEnum = userBoardRepository.getRoleFindByUserIdAndBoardId(user.getId(), card.getBoard().getBoardId());
        roleEnum.ifPresent(role -> {
            if (!(role.equals(BoardRoleEnum.OWNER) || role.equals(BoardRoleEnum.EDITER))) {
                throw new IllegalArgumentException("Board 관리권한이 없습니다.");
            }
        });
        if (!roleEnum.isPresent()) {
            throw new IllegalArgumentException("Board에 초대되지 않은 사용자입니다.");
        }

        card.setUser(user);

        // regist Alert
        alertService.registAlerts(new AlertRequestDto(
                card.getBoard().getTitle(),
                card.getCategory().getTitle(),
                card.getTitle(),
                card.getDescription(),
                user.getId()));

        return new ApiResponseDto("작업자 등록완료", HttpStatus.OK.value());
    }

    @Override
    public Card findCard(Long id) {
        return cardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("카드를 찾을 수 없습니다.")
        );
    }
}
