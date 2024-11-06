package br.com.goobar_app.components;

import br.com.goobar_app.CustomException.BarException;
import br.com.goobar_app.CustomException.BarStatus;
import br.com.goobar_app.CustomException.UserException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Getter
@Setter
public class Avalicao {

    public static double avaliacao(Double avalicao,
                                   Double existAvalicao, Integer existNumeroDeAvaliacao) throws BarException, UserException,RuntimeException {


        if (avalicao > 5){throw new BarException(BarStatus.AVALIATION_NOT_SEND);}
        Double avalicaoTotal = existAvalicao * existNumeroDeAvaliacao;
        Double v = avalicaoTotal + avalicao;
        Integer i = existNumeroDeAvaliacao + 1;
        Double total = v / i;

        return Math.round(total * 100) / 100.0;

    }

}
