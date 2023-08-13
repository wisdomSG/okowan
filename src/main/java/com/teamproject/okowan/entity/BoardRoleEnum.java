package com.teamproject.okowan.entity;

public enum BoardRoleEnum {
    VIEWER("조회만 가능 / 모든 작성,수정,삭제 불가능"),
    EDITOR("CATEGORY, CARD 작성, 조회, 수정, 삭제 가능"),
    OWNER("BOARD, CATEGORY, CARD 작성, 조회, 수정, 삭제 가능 ");

    private final String description;

    BoardRoleEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
