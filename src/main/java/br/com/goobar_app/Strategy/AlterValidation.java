package br.com.goobar_app.Strategy;

import br.com.goobar_app.DTOS.AlterUser;

public interface AlterValidation {
    void EmailValidation(AlterUser alterUser);
    void PhoneValidation(AlterUser alterUser);
    void UsernameValidation(AlterUser alterUser);
}
