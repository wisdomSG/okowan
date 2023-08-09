package com.teamproject.okowan.comment;

import com.teamproject.okowan.aop.ApiResponseDto;
import com.teamproject.okowan.card.Card;
import com.teamproject.okowan.card.CardRepository;
import com.teamproject.okowan.card.CardService;
import com.teamproject.okowan.card.CardServiceImpl;
import com.teamproject.okowan.security.UserDetailsImpl;
import com.teamproject.okowan.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CardRepository cardRepository;
    private final CommentRepository commentRepository;

    /* 댓글 등록 */
    @Override
    public ApiResponseDto registComment(Long cardId, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {
        User user = checkUser(userDetails);
        Card card = findByIdCard(cardId);

        Comment comment = new Comment(commentRequestDto);

        comment.setCard(card);
        comment.setUser(user);

        commentRepository.save(comment);

        return new ApiResponseDto("댓글 등록 성공", HttpStatus.OK.value());
    }

    /* 댓글 수정 */
    @Override
    @Transactional
    public ApiResponseDto updateComment(Long commentId, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {
        User user = checkUser(userDetails);

        Comment comment = findComment(commentId);

        checkUserCommentOwner(comment, user);

        comment.setContent(commentRequestDto.getContent());

        return new ApiResponseDto("댓글 수정 성공", HttpStatus.OK.value());
    }

    /* 댓글 삭제 */
    @Override
    @Transactional
    public ApiResponseDto deleteComment(Long commentId, UserDetailsImpl userDetails) {
        User user = checkUser(userDetails);

        Comment comment = findComment(commentId);

        checkUserCommentOwner(comment, user);

        commentRepository.delete(comment);

        return new ApiResponseDto("댓글 삭제 성공" , HttpStatus.OK.value());
    }

    /* 댓글 찾기 */
    @Override
    public Comment findComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
    }

    /* Find Card By Id */
    public Card findByIdCard(Long cardId) {
        return cardRepository.findById(cardId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카드입니다."));
    }

    /* Jwt UserDetails Null Check */
    public User checkUser(UserDetailsImpl userDetails) {
        if(userDetails == null) {
            throw new IllegalArgumentException("올바른 사용자가 아닙니다");
        }
        return userDetails.getUser();
    }

    /* check same user - comment */
    public void checkUserCommentOwner(Comment comment, User user) {
        if(comment.getUser().getId() != user.getId()) {
            throw new IllegalArgumentException("댓글 작성자가 아닙니다.");
        }
    }

}
