package br.com.ota.cpjbackend.exception;

public class AppException extends Exception {

    private static final long serialVersionUID = 1L;

    public AppException(String msg) {
        super(msg);
    }
}
