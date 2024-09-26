package br.com.alverad.ecommerce.commons.enumeration;

import lombok.Getter;

@Getter
public enum ErrorType {

    REPORT_001("Requisição inválida.", "RP-001"),

    REPORT_002("Recurso não encontrado.", "RP-002"),

    REPORT_003("Erro no JSON do corpo da requisição.", "RP-003"),

    REPORT_004("Erro ao enviar o email.", "RP-004"),

    REPORT_005("Não é permitido enviar lista vazia no corpo da requisição!", "RP-005"),

    REPORT_006("Os dados do relatório estão todos preenchido com zero", "RP-006"),

    REPORT_007("Recurso já existente", "RP-007"),

    DATABASE_001("Falha ao tentar salvar no banco de dados.", "DB-001"),

    DATABASE_002("Nenhuma mensagem de email do tipo %s cadastrada.", "DB-002"),

    DATABASE_003("Falha ao atualizar o recurso.", "DB-003"),

    DATABASE_004("Falha ao salvar o recurso.", "DB-004"),

    SOLICITACAO_EXPIRADA("Solicitação Expirada", "SE-001"),

    UNAUTHORIZED_TRANSACTION("Permissões insuficientes para realizar esta transação",
            "SECURITY-001"),

    DOCUMENTO_INVALIDO("Documento inválido.", "DI-001"),

    QRCODE_ERROR("Falha ao gerar o QRCODE.", "QRCODE-001"),

    REPORT_008("Prazo para recurso encerrado.", "RP-008"),

    REPORT_009("Prazo para inscrição encerrado.", "RP-009"),

    REPORT_010("Documento inexistente para essa inscrição.", "RP-010"),

    REPORT_011("Etapa encerrada.", "RP-011"),

    CANDIDATO_001("Candidato já avaliado.", "CA-001");

    private String message;
    private String internalCode;

    private ErrorType(String message, String internalCode) {
        this.message = message;
        this.internalCode = internalCode;
    }
}
