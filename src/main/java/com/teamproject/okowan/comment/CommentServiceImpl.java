package com.teamproject.okowan.comment;

import com.teamproject.okowan.aop.ApiResponseDto;
import com.teamproject.okowan.card.Card;
import com.teamproject.okowan.card.CardRepository;
import com.teamproject.okowan.security.UserDetailsImpl;
import com.teamproject.okowan.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentSerivce {
    private final CardRepository cardRepository;
    private final CommentRepository commentRepository;

    /* 댓글 등록 */
    @Override
    public ApiResponseDto registComment(Long card_id, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {
        User user = checkUser(userDetails);
        Card card = findByIdCard(card_id);

        Comment comment = new Comment(commentRequestDto);

        comment.setCard(card);
        comment.setUser(user);

        commentRepository.save(comment);

        return new ApiResponseDto("댓글 등록 성공", HttpStatus.OK.value());
    }

    /* 댓글 수정 */
    @Override
    @Transactional
    public ApiResponseDto updateComment(Long comment_id, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {
        User user = checkUser(userDetails);

        Comment comment = findByIdComment(comment_id);

        checkUserCommentOwner(comment, user);

        comment.setContent(commentRequestDto.getContent());

        return new ApiResponseDto("댓글 수정 성공", HttpStatus.OK.value());
    }

    /* 댓글 삭제 */
    @Override
    @Transactional
    public ApiResponseDto deleteComment(Long comment_id, UserDetailsImpl userDetails) {
        User user = checkUser(userDetails);

        Comment comment = findByIdComment(comment_id);

        checkUserCommentOwner(comment, user);

        commentRepository.delete(comment);

        return new ApiResponseDto("댓글 삭제 성공" , HttpStatus.OK.value());
    }

    /* Jwt UserDetails Null Check */
    public User checkUser(UserDetailsImpl userDetails) {
        if(userDetails == null) {
            throw new IllegalArgumentException("올바른 사용자가 아닙니다");
        }

        return userDetails.getUser();
    }

    /* Find Card By Id */
    public Card findByIdCard(Long card_id) {
        return cardRepository.findById(card_id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카드입니다."));
    }

    /* Find Comment by Id */
    public Comment findByIdComment(Long comment_id) {
        return commentRepository.findById(comment_id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
    }

    /* check same user - comment */
    public void checkUserCommentOwner(Comment comment, User user) {
        if(comment.getUser().getId() != user.getId()) {
            throw new IllegalArgumentException("댓글 작성자가 아닙니다.");
        }
    }

}
