package com.teamproject.okowan.entity;

public enum ColorEnum {
    RED("Red color"),
    ORANGE("Orange color"),
    YELLOW("Yellow color"),
    GREEN("Green color"),
    BLUE("Blue color"),
    PURPLE("Purple color");

    private final String description;

    ColorEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
