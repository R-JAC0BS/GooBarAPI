package br.com.goobar_app.Strategy.Validation;

import br.com.goobar_app.CustomException.BarStatus;
import br.com.goobar_app.CustomException.UserException;
import br.com.goobar_app.CustomException.UserStatus;
import br.com.goobar_app.DTOS.RegisterDto;
import br.com.goobar_app.Strategy.AccountValidation;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;


@Component
public class PassowrdValidation implements AccountValidation <RegisterDto> {

    public PassowrdValidation() {
    }

    @SneakyThrows
    @Override
    public void execute(RegisterDto validation) {
        if (validation.password().length() < 8){
            throw new UserException(UserStatus.USER_PASSWORD_VALIDATE);
        }
    }


}
