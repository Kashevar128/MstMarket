package ru.vinogradov.mst.market.auth.exceptions;

public class IncorrectRoleUserException extends RuntimeException{
    public IncorrectRoleUserException(String message) {
        super(message);
    }
}
