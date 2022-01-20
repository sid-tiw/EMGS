package com.paytmbank.middleware.emgs.exception;

public class UnauthorizedUser extends Exception {
    public UnauthorizedUser(String message) {
        super(message);
    }
}
