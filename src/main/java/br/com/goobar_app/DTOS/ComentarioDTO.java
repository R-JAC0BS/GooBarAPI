package br.com.goobar_app.DTOS;

import jakarta.validation.constraints.NotBlank;

public record ComentarioDTO(
        @NotBlank String comentario


) {
}
