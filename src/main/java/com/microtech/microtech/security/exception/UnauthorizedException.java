package com.microtech.microtech.security.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("You must be logged in.");
    }
}