package br.com.goobar_app.DTOS;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


public record LoginDTOS(
        @NotBlank String email,
        @NotBlank String password
) {
}
