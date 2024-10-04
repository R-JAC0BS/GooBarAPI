package br.com.goobar_app.DTOS;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


public record AvaliacaoDTO(
        @NotBlank Double avaliacao,
        @NotBlank Integer numerodeavaliacao

) {
}
