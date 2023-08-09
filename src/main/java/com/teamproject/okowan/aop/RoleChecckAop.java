package com.teamproject.okowan.aop;

import com.teamproject.okowan.card.Card;
import com.teamproject.okowan.card.CardService;
import com.teamproject.okowan.entity.BoardRoleEnum;
import com.teamproject.okowan.user.User;
import com.teamproject.okowan.userBoard.UserBoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j(topic = "RoleCheckAop")
@Component
@Aspect // AOP를 가능하게하는 어노테이션
public class RoleChecckAop {

    @Autowired
    private CardService cardService;

    @Autowired
    private UserBoardRepository userBoardRepository;

    @Pointcut("execution(* com.teamproject.okowan.card.CardService.updateCard(..))")
    private  void updateCard() {}

    @Pointcut("execution(* com.teamproject.okowan.card.CardService.updateDeadLine(..))")
    private  void updateDeadLine() {}

    @Pointcut("execution(* com.teamproject.okowan.card.CardService.deleteCard(..))")
    private  void deleteCard() {}

    @Around("updateCard() || deleteCard() || updateDeadLine()")
    public Object executeCardRoleCheck(ProceedingJoinPoint joinPoint) throws  Throwable {

        // 1,2 번째 매개변수로 id, user값 가져오기
        Long id = (Long) joinPoint.getArgs()[0];
        User user = (User) joinPoint.getArgs()[1];

        // 타겟 메서드에서 card 객체 가져오기
        Card card = cardService.findCard(id);

        userBoardRepository.getRoleFindByUserId(user.getId(),card.getBoard().getBoardId())
                .ifPresent(role -> { // ifPresent를 사용하여 값이 있는경우에만 람다식을 실행하게 함
                    if(role != BoardRoleEnum.OWNER) {
                        throw new IllegalArgumentException("Card의 관한 권한이 없습니다.");
                    }
                });


        // board에 권한이 아예 없는 유저가 카드를 생성하려고 할 때의 예외처리
        if (!userBoardRepository.getRoleFindByUserId(user.getId(), card.getBoard().getBoardId()).isPresent()) {
            throw new IllegalArgumentException("Board의 사용자가 아닙니다.");
        }

        //핵심기능 수행
        return joinPoint.proceed();

    }
}
