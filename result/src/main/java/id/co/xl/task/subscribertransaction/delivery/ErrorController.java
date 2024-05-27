package id.co.xl.task.subscribertransaction.delivery;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import id.co.xl.task.subscribertransaction.model.response.GenericResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ErrorController {

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<GenericResponse<String>> constraintViolationsExecption(ConstraintViolationException exception) {

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(GenericResponse.<String>builder()
        .message(exception.getMessage())
        .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
        .code("01")
        .build());
  }
}
