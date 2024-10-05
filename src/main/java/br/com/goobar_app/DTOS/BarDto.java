package br.com.goobar_app.DTOS;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

public record BarDto(
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
        Boolean arlivre

) {
}
