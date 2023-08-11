package com.teamproject.okowan.aop;

import com.teamproject.okowan.board.Board;
import com.teamproject.okowan.board.BoardService;
import com.teamproject.okowan.card.Card;
import com.teamproject.okowan.card.CardService;
import com.teamproject.okowan.category.Category;
import com.teamproject.okowan.category.CategoryService;
import com.teamproject.okowan.entity.BoardRoleEnum;
import com.teamproject.okowan.security.UserDetailsImpl;
import com.teamproject.okowan.user.User;
import com.teamproject.okowan.userBoard.UserBoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j(topic = "RoleCheckAop")
@Component
@Aspect // AOP를 가능하게하는 어노테이션
public class RoleCheckAop {

    @Autowired
    private CardService cardService;

    @Autowired
    private UserBoardRepository userBoardRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BoardService boardService;

    @Pointcut("execution(* com.teamproject.okowan.card.CardService.updateCard(..))")
    private  void updateCard() {}

    @Pointcut("execution(* com.teamproject.okowan.card.CardService.updateDeadLine(..))")
    private  void updateDeadLine() {}

    @Pointcut("execution(* com.teamproject.okowan.card.CardService.updateFileUpload(..))")
    private  void updateFileUpload() {}


    @Pointcut("execution(* com.teamproject.okowan.card.CardService.deleteCard(..))")
    private void deleteCard() {
    }

    @Pointcut("execution(* com.teamproject.okowan.category.CategoryService.registCategory(..))")
    private void registCategory() {
    }

    @Pointcut("execution(* com.teamproject.okowan.category.CategoryService.updateCategory(..))")
    private void updateCategory() {
    }

    @Pointcut("execution(* com.teamproject.okowan.category.CategoryService.deleteCategory(..))")
    private void deleteCategory() {
    }


    @Pointcut("execution(* com.teamproject.okowan.card.CardService.deleteFile(..))")
    private  void deleteFile() {}

    @Around("updateCard() || deleteCard() || updateDeadLine() || deleteFile() || updateFileUpload()")
    public Object executeCardRoleCheck(ProceedingJoinPoint joinPoint) throws  Throwable {

        // 1,2 번째 매개변수로 id, user값 가져오기
        Long id = (Long) joinPoint.getArgs()[0];
        User user = (User) joinPoint.getArgs()[1];

        // 타겟 메서드에서 card 객체 가져오기
        Card card = cardService.findCard(id);

        userBoardRepository.getRoleFindByUserIdAndBoardId(user.getId(), card.getBoard().getBoardId())
                .ifPresent(role -> { // ifPresent를 사용하여 값이 있는경우에만 람다식을 실행하게 함
                    if (role != BoardRoleEnum.OWNER && role != BoardRoleEnum.EDITER) {
                        throw new IllegalArgumentException("Card의 관한 권한이 없습니다.");
                    }
                });

        // board에 권한이 아예 없는 유저가 카드를 생성하려고 할 때의 예외처리
        if (!userBoardRepository.getRoleFindByUserIdAndBoardId(user.getId(), card.getBoard().getBoardId()).isPresent()) {
            throw new IllegalArgumentException("Board의 사용자가 아닙니다.");
        }

        //핵심기능 수행
        return joinPoint.proceed();
    }

    /* Check RegistCategory User Role  */
    @Around("registCategory()")
    public Object executeRegistCategoryRoleCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        Long boardId = (Long) joinPoint.getArgs()[0];
        User user = ((UserDetailsImpl) joinPoint.getArgs()[1]).getUser();

        Board board = boardService.findBoard(boardId);

        Optional<BoardRoleEnum> roleEnum = userBoardRepository.getRoleFindByUserIdAndBoardId(user.getId(), board.getBoardId());
        roleEnum.ifPresent(role -> {
            if (!(role.equals(BoardRoleEnum.OWNER) || role.equals(BoardRoleEnum.EDITER))) {
                throw new IllegalArgumentException("Category 생성 권한이 없습니다.");
            }
        });
        if (!roleEnum.isPresent()) {
            throw new IllegalArgumentException("Board의 사용자가 아닙니다.");
        }

        return joinPoint.proceed();
    }

    /* Check UpdateCategory, DeleteCategory User Role*/
    @Around("updateCategory() || deleteCategory()")
    public Object executeUpdateAndDeleteCategoryRoleCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        Long categoryId = (Long) joinPoint.getArgs()[0];
        User user = ((UserDetailsImpl) joinPoint.getArgs()[1]).getUser();

        Category category = categoryService.findCategory(categoryId);

        Optional<BoardRoleEnum> roleEnum = userBoardRepository.getRoleFindByUserIdAndBoardId(user.getId(), category.getBoard().getBoardId());
        roleEnum.ifPresent(role -> {
            if (!(role.equals(BoardRoleEnum.OWNER) || role.equals(BoardRoleEnum.EDITER))) {
                throw new IllegalArgumentException("Category에 관한 권한이 없습니다.");
            }
        });
        if (!roleEnum.isPresent()) {
            throw new IllegalArgumentException("Board의 사용자가 아닙니다.");
        }

        return joinPoint.proceed();
    }
}
