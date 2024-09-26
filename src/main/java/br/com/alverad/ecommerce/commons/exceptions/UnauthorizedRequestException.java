package br.com.alverad.ecommerce.commons.exceptions;

import br.com.alverad.ecommerce.commons.enumeration.ErrorType;
import lombok.Getter;

@Getter
public class UnauthorizedRequestException extends Exception {

    private final String internalCode;

    public UnauthorizedRequestException(String message, String internalCode) {
        super(message);
        this.internalCode = internalCode;
    }

    public UnauthorizedRequestException() {
        super(ErrorType.UNAUTHORIZED_TRANSACTION.getMessage());
        this.internalCode = ErrorType.UNAUTHORIZED_TRANSACTION.getInternalCode();
    }
}
