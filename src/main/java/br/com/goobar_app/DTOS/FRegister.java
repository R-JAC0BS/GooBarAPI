package br.com.goobar_app.DTOS;

import br.com.goobar_app.Models.EnderecoModel;
import jakarta.validation.constraints.NotBlank;

public record FRegister(
        @NotBlank String nomebar,
        @NotBlank String descricao,
        @NotBlank String imagemurl,
        Boolean wifi,
        Boolean tv,
        Boolean arcondicionado,
        Boolean estacionamento ,
        Boolean mesabilhar,
        Boolean musicaaovivo,
        Boolean praia,
        Boolean arlivre,
        EnderecoModel endereco

) {
}
