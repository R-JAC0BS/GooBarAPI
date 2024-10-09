package br.com.goobar_app.components;

import br.com.goobar_app.Models.UserModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
public class ExtractEmail {

    public static String extrairEmail () throws Exception{

        try {

            /*
            Responsavel por extrair os emails do usuario Autenticado
             */
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

               /*
                Retorna o o User
             */
            Optional<UserModel> optionalUserModel = (Optional<UserModel>) authentication.getPrincipal();

            /*
                faz dowcast do email e retorna no metodo
             */
            return optionalUserModel.map(UserModel::getEmail).orElse("");
        }catch (Exception e){
            throw new Exception ("Usuario não existe ou não esta autenticado");
        }


    }

}
