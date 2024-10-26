package br.com.goobar_app.DTOS;

public record BarAlter(
        String nomeBar,
        String descricao,
        boolean wifi,
        boolean tv,
        boolean arCondicionado,
        boolean estacionamento,
        boolean mesaBilhar,
        boolean musicaAoVivo,
        boolean praia,
        boolean arLivre

) {
}
