package com.paytmbank.middleware.emgs.exception;

import lombok.NoArgsConstructor;

public class RequestError extends Exception {
    public RequestError(String message) {
        super(message);
    }
}
