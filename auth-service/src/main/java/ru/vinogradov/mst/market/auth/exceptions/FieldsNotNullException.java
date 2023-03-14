package ru.vinogradov.mst.market.auth.exceptions;

public class FieldsNotNullException extends RuntimeException{
    public FieldsNotNullException(String message) {
        super(message);
    }
}
