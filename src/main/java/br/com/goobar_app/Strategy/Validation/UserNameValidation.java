package br.com.goobar_app.Strategy.Validation;

import br.com.goobar_app.CustomException.UserException;
import br.com.goobar_app.CustomException.UserStatus;
import br.com.goobar_app.DTOS.RegisterDto;
import br.com.goobar_app.Strategy.AccountValidation;
import jakarta.validation.ValidationException;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;


@Component
public class UserNameValidation implements AccountValidation<RegisterDto> {
    @Override
    @SneakyThrows
    public void execute(RegisterDto validation) {
        if (validation.username().length() < 4 || validation.username().length() > 40) {
            throw new UserException(UserStatus.USER_USERNAME_VALIDATE);
        }
    }
}
