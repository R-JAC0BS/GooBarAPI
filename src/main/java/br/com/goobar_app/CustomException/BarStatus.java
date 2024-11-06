package br.com.goobar_app.CustomException;

public enum BarStatus {

    BAR_ERROR("Aconteceu algum problema durante o registro do bar"),
    ATUALIZAR_BAR_ERROR("Aconteceu algum problema em atualizar os atributos do bar"),
    IMAGE_BAR_ERROR("Aconteceu algum erro em salvar ou alterar a imagem do bar"),
    DELETE_BAR_ERROR("Aconteceu algum erro em deletar o bar"),
    LOCALIZATION_BAR_ERROR("Localização inválida"),
    FAVORITE_BAR_SAVE("Algum problema foi encontrado durante a avaliação do bar"),
    USER_NOT_FOUND("Usuário não encontrado"),
    USER_OR_BAR_NOT_FOUND("Usuário não é associado a esse bar"),
    AVALIATION_NOT_SEND("Não foi possível enviar avaliação"),
    BAR_NOT_FOUND("Não foi possível encontrar nenhum bar"),



    BAR_CREATE ("BAR CRIADO COM SUCESSO"),
    BAR_EDIT ("BAR EDITADO COM SUCESSO"),
    BAR_AVALAIATION ("BAR AVALIADO COM SUCESSO"),
    BAR_DELETED ("BAR DELETADO COM SUCESSO");






    private final String message;

    BarStatus(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

}
