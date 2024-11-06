package br.com.goobar_app.Strategy.Validation;

import br.com.goobar_app.CustomException.UserException;
import br.com.goobar_app.CustomException.UserStatus;
import br.com.goobar_app.DTOS.RegisterDto;
import br.com.goobar_app.Strategy.AccountValidation;
import br.com.goobar_app.UserRepository.UserRepository;
import br.com.goobar_app.components.Validadores;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;


@Component
public class EmailValidation implements AccountValidation <RegisterDto> {


    private final UserRepository userRepository;

    public EmailValidation (UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    @SneakyThrows
    public void execute(RegisterDto validation) {
        if (!Validadores.validaEmail(validation.email()) || this.userRepository.existsByEmail(validation.email())){
            throw new UserException(UserStatus.USER_EMAIL_VALIDATE);
        }
    }




}
