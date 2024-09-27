package br.com.alverad.ecommerce.commons.controller.adviser;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import br.com.alverad.ecommerce.commons.dto.ErrorResponse;
import br.com.alverad.ecommerce.commons.enumeration.ErrorType;

/**
 * Responsável por tratar as exceções gerais da aplicação.
 *
 * @author Alfredo Gabriel
 * @since 26/03/2023
 * @version 1.0
 */
@RestControllerAdvice
public class ControllerAdvise {


    @ExceptionHandler(SQLException.class)
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public ErrorResponse handleSQLException(SQLException ex) {

        return ErrorResponse.builder().message(extrairParesColunaValor(ex.getMessage()))
                .technicalMessage(ex.getLocalizedMessage())
                .internalCode(ErrorType.DATABASE_001.getInternalCode()).build();
    }

    private String extrairParesColunaValor(String mensagemErro) {
        Pattern pattern = Pattern.compile("(Chave|Key) \\((.*?)\\)=\\((.*?)\\)");
        Matcher matcher = pattern.matcher(mensagemErro);
        StringBuilder builder = new StringBuilder();

        while (matcher.find()) {
            String nomeColuna = matcher.group(1);
            String valorColuna = matcher.group(2);
            builder.append(String.format("Valor %s=%s já existe%n", nomeColuna, valorColuna));
        }

        return builder.toString();
    }

    /**
     * Caso alguma entidade não seja valida (dados diferentes do esperado), será retornado o
     * atributo e a menssagem pre definida.
     *
     * @param ex Excessão capturada.
     * @return Mapa contendo atributo e mensagem de erro.
     * @author Alfredo Gabriel
     * @since 26/03/2023
     */
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ErrorResponse.builder().message(ErrorType.REPORT_001.getMessage())
                .technicalMessage(ex.getLocalizedMessage())
                .internalCode(ErrorType.REPORT_001.getInternalCode()).details(errors).build();
    }

    /**
     * Caso a autenticação não seja encontrado nenhum elemento correspondente.
     *
     * @param ex Excessão capturada.
     * @return Mensagem
     * @author Alfredo Gabriel
     * @since 21/04/2023
     */
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ErrorResponse handleNoSuchElementException(NoSuchElementException ex) {
        return new ErrorResponse(ErrorType.REPORT_002.getMessage(), ex.getLocalizedMessage(),
                ErrorType.REPORT_002.getInternalCode());
    }

    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({AccessDeniedException.class})
    public ErrorResponse handleAccessDeniedException(AccessDeniedException ex) {
        return new ErrorResponse(ErrorType.UNAUTHORIZED_TRANSACTION.getMessage(),
                ex.getLocalizedMessage(), ErrorType.UNAUTHORIZED_TRANSACTION.getInternalCode());
    }

}
