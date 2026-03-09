package br.com.riccar.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StockAlreadyExistException extends RuntimeException {


    public StockAlreadyExistException(String message) {
        super(message);
    }
}
