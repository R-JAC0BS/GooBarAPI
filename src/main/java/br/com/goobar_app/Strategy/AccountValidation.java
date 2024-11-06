package br.com.goobar_app.Strategy;

import br.com.goobar_app.DTOS.RegisterDto;
import br.com.goobar_app.Models.UserModel;

public interface AccountValidation <T> {

    void execute (T validation);

}
