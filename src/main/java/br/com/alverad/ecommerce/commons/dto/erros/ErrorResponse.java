package br.com.alverad.ecommerce.commons.dto.erros;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ErrorResponse {

    private String message;

    private String technicalMessage;

    private String internalCode;

    private Map<String, String> details;

    public ErrorResponse(String message, String technicalMessage, String internalCode) {
        this.message = message;
        this.technicalMessage = technicalMessage;
        this.internalCode = internalCode;
    }

}
