package br.com.goobar_app.DTOS;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EnderecoDTO(
        @NotNull Double latitude,
        @NotNull Double longitude


) {
}
