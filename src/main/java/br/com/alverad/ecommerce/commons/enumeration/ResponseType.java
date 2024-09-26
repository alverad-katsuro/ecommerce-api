package br.com.alverad.ecommerce.commons.enumeration;

import lombok.Getter;

@Getter
public enum ResponseType {

    SUCCESS_SAVE("Dado salvo com sucesso"),

    FAIL_SAVE("Dado não-salvo com sucesso"),

    SUCCESS_UPDATE("Dado atualizado com sucesso"),

    FAIL_UPDATE("Dado não-atualizado com sucesso"),

    SUCCESS_DELETE("Dado deletado com sucesso"),

    FAIL_DELETE("Dado não deletado"),

    SUCCESS_IMAGE_SAVE("Imagem salva com sucesso"),

    SUCCESS_IMAGE_DELETE("Imagem deletada com sucesso"),

    FAIL_IMAGE_DELETE("Usuário não tem imagem para deletar"),

    SOLICITACAO_REALIZADA_SUCESSO("Solicitação realizada com sucesso."),

    RESUMO_SUBMETIDO_COM_SUCESSO("Resumo submetido com sucesso."),

    COMPROVANTE_ENVIADO_COM_SUCESSO("Comprovante enviado com sucesso.");

    private String message;

    private ResponseType(String message) {
        this.message = message;
    }

}
