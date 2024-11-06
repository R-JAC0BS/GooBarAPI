package br.com.goobar_app.Strategy.Validation;

import br.com.goobar_app.CustomException.UserException;
import br.com.goobar_app.CustomException.UserStatus;
import br.com.goobar_app.DTOS.AlterPasswordDto;
import br.com.goobar_app.Models.BarModel;
import br.com.goobar_app.Models.UserModel;
import br.com.goobar_app.Strategy.AccountValidation;
import br.com.goobar_app.Strategy.NewPassWordValidation;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class AlterPassWord implements NewPassWordValidation <AlterPasswordDto, UserModel> {


    public AlterPassWord(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    private final PasswordEncoder passwordEncoder ;
    @Override
    @SneakyThrows
    public void PassWordValidation(AlterPasswordDto validationPass, UserModel newValidationPass) {
        if (passwordEncoder.encode(validationPass.password()) == newValidationPass.getPassword() || validationPass.newpassword().length() < 8) {
            throw new UserException(UserStatus.USER_PASSWORD_VALIDATE);
        }
    }
}
