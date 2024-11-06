package br.com.goobar_app.CustomException;


import org.springframework.stereotype.Component;

@Component
public final class BarException extends RuntimeException {
    public BarException() {
        super();
    }


    public BarException(String message) {
        super(message);
    }

    public BarException(BarStatus barStatus) {
        super (barStatus.toString());
    }


}
