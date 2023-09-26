package com.teamproject.okowan.card;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepositoryQuery {

    List<Card> getCardFindByTitleList(String keyword);
}
