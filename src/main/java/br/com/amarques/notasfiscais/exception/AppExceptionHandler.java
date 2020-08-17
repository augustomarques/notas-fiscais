package br.com.amarques.notasfiscais.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = DeleteEmpresaException.class)
    public ResponseEntity<Object> handleDeleteEmpresaException(DeleteEmpresaException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = EntityAlreadyRegisteredException.class)
    public ResponseEntity<Object> handleEntityAlreadyRegisteredException(EntityAlreadyRegisteredException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = InvalidCNPJException.class)
    public ResponseEntity<Object> handleInvalidCNPJException(InvalidCNPJException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = InvalidNotaFiscalEmpresaTipoException.class)
    public ResponseEntity<Object> handleInvalidNotaFiscalEmpresaTipoException(
            InvalidNotaFiscalEmpresaTipoException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
}
