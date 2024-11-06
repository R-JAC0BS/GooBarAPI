package br.com.goobar_app.Strategy.Validation;

import br.com.goobar_app.CustomException.UserException;
import br.com.goobar_app.CustomException.UserStatus;
import br.com.goobar_app.DTOS.AlterUser;
import br.com.goobar_app.Strategy.AlterValidation;
import br.com.goobar_app.components.Validadores;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;


@Component
public class AlterUserValidationn implements AlterValidation {

    public AlterUserValidationn() {
        super();

    }

    @Override
    @SneakyThrows
    public void EmailValidation(AlterUser alterUser) {
        if (alterUser.username() == null || !Validadores.validaEmail(alterUser.email())){
            throw new UserException(UserStatus.USER_EMAIL_VALIDATE);
        }


    }



    @Override
    @SneakyThrows
    public void PhoneValidation(AlterUser alterUser) {
        if (alterUser.telefone() == null){
            throw new UserException(UserStatus.USER_TELEFONE_VALIDATE);
        }

    }

    @Override
    @SneakyThrows
    public void UsernameValidation(AlterUser alterUser) {
        if (alterUser.username() == null){
            throw new UserException(UserStatus.USER_USERNAME_VALIDATE);
        }

    }

    public void AlterUser(AlterUser alterUser) {
       UsernameValidation (alterUser);
       EmailValidation(alterUser);
       PhoneValidation(alterUser);

    }

}
