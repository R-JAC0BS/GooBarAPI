package br.com.goobar_app.components;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validadores {


    public static Boolean validaEmail (String email) throws Exception {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();

    }
}
