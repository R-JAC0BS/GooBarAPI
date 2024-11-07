package br.com.goobar_app.CustomException;


import org.springframework.stereotype.Component;


@Component
public final class UserException extends RuntimeException {


    public UserException() {
        super();
    }


    public UserException(String message) {
        super(message);
    }

    public UserException(UserStatus userStatus) {
        super(userStatus.toString());
    }
}
