package br.com.alverad.ecommerce.commons.exceptions;

import br.com.alverad.ecommerce.commons.enumeration.ErrorType;
import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private final String internalCode;

    public NotFoundException(String message, String internalCode) {
        super(message);
        this.internalCode = internalCode;
    }

    public NotFoundException() {
        super(ErrorType.REPORT_002.getMessage());
        this.internalCode = ErrorType.REPORT_002.getInternalCode();
    }
}
