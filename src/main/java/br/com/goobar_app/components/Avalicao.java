package br.com.goobar_app.components;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Avalicao {

    public static double avaliacao(Double avalicao,
                                   Double existAvalicao, Integer existNumeroDeAvaliacao) throws Exception {


        if (avalicao > 5){throw new Exception("Numero da avaliação não pode ser maior que 5");}
        Double avalicaoTotal = existAvalicao * existNumeroDeAvaliacao; // retorna total da avalicao
        Double v = avalicaoTotal + avalicao; //total avalicao
        Integer i = existNumeroDeAvaliacao + 1; //Soma
        Double total = v / i;

        return Math.round(total * 100) / 100.0;

    }

}
