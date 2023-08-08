package com.teamproject.okowan.aop;

import com.teamproject.okowan.card.Card;
import com.teamproject.okowan.card.CardService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.RejectedExecutionException;

@Slf4j(topic = "RoleCheckAop")
@Component
@Aspect // AOP를 가능하게하는 어노테이션
public class RoleChecckAop {

    @Autowired
    private CardService cardService;

    @Pointcut("execution(* com.teamproject.okowan.card.CardService.updateCard(..))")
    private  void updateCard() {}

    @Pointcut("execution(* com.teamproject.okowan.card.CardService.deleteCard(..))")
    private  void deleteCard() {}

    @Around("updateCard() || deleteCard()")
    public Object excuteCardRoleCheck(ProceedingJoinPoint joinPoint) throws  Throwable {
        // 1,2 번째 매개변수로 id, user값 가져오기
        Long id = (Long) joinPoint.getArgs()[0];
        Long user = (Long) joinPoint.getArgs()[1];

        // 타겟 메서드에서 card 객체 가져오기
        Card card = cardService.findCard(id);

        // 카드 작성자(card.user) 와 요청자(user)가 같은지, board에 대한 권한이 owner인지 확인
        if(!card.getUser().equals(user)) {
            log.warn("[AOP] 작성자만 댓글을 수정/삭제 할 수 있습니다.");
            throw new RejectedExecutionException();
        }

        //핵심기능 수행
        return joinPoint.proceed();


    }
}
