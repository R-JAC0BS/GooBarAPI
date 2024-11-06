package br.com.goobar_app.CustomException;


import org.springframework.stereotype.Component;


@Component
public final class UserException extends RuntimeException {

    // Construtor sem parâmetros
    public UserException() {
        super();  // Chama o construtor da classe Exception
    }

    // Construtor que recebe uma String
    public UserException(String message) {
        super(message);  // Passa a mensagem para a classe Exception
    }

    // Construtor que recebe um UserStatus e passa a mensagem para a classe Exception
    public UserException(UserStatus userStatus) {
        super(userStatus.toString());  // Passa a mensagem do enum UserStatus
    }
}
