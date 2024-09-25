package br.com.goobar_app.DTOS;

import jakarta.validation.constraints.NotBlank;

public record RegisterDto(@NotBlank String username,
                          @NotBlank String email,
                          @NotBlank String cpf,
                          @NotBlank String password
) {
}
