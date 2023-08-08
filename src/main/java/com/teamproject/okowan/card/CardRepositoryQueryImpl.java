package com.teamproject.okowan.card;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.teamproject.okowan.user.QUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CardRepositoryQueryImpl implements CardRepositoryQuery{

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Card> getCardFindByTitleList(String keyword) {
        QCard card = QCard.card;

        return jpaQueryFactory.selectFrom(card)
                .where(card.title.like("%" + keyword + "%"))
                .fetch();
    }
}
