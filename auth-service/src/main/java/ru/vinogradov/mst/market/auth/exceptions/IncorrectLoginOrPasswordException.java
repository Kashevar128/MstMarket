package ru.vinogradov.mst.market.auth.exceptions;

public class IncorrectLoginOrPasswordException extends RuntimeException{
    public IncorrectLoginOrPasswordException(String message) {
        super(message);
    }
}
