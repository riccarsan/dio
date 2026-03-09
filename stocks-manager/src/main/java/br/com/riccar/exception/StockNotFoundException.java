package br.com.riccar.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Não existe cadastro para a ação informada.")
public class StockNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public StockNotFoundException(String msg) {
        super(msg);
    }

    public StockNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
