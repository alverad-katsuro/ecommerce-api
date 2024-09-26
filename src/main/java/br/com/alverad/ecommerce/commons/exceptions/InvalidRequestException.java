package br.com.alverad.ecommerce.commons.exceptions;

import br.com.alverad.ecommerce.commons.enumeration.ErrorType;
import lombok.Getter;

@Getter
public class InvalidRequestException extends Exception {

    private final String internalCode;

    public InvalidRequestException(String message, String internalCode) {
        super(message);
        this.internalCode = internalCode;
    }

    public InvalidRequestException(String message) {
        super(message);
        this.internalCode = ErrorType.REPORT_001.getInternalCode();
    }

    public InvalidRequestException() {
        super(ErrorType.REPORT_001.getMessage());
        this.internalCode = ErrorType.REPORT_001.getInternalCode();
    }
}
