package com.project.transactional.shared.exception;

public class NotBalanceException extends RuntimeException {
    public NotBalanceException(String message) {
        super(message);
    }
}