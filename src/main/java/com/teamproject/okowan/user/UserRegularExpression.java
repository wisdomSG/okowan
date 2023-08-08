package com.teamproject.okowan.user;

public class UserRegularExpression {
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9+_-]+" +
            "(?:\\.[a-zA-z0-9+_-]+)*@" +
            "(?:[a-zA-Z0-9]+\\.)+[a-zA-Z]+$";
    public static final String PASSWORD_REGEX = "^[a-zA-z0-9!@#$%^&*()]{4,20}$";
    public static final String NICKNAME_REGEX = "^[a-zA-Z0-9]{1,20}$";
}
