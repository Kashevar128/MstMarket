package ru.vinogradov.mst.market.auth.exceptions;

public class BanUserException extends RuntimeException {
    public BanUserException(String message) {
        super(message);
    }
}
