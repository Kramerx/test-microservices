package com.project.transactional.exception;

public class NotBalanceException extends RuntimeException {
    public NotBalanceException(String message) {
        super(message);
    }
}