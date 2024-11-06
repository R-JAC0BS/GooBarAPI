package br.com.goobar_app.DTOS;

public record AlterUser(
        String username,
        String email,
        String telefone
) {
}
