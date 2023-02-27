package com.hcmute.project.instagram.backend.controller.exception;

import com.hcmute.project.instagram.backend.domain.exception.ServiceExceptionBase;
import com.hcmute.project.instagram.backend.domain.exception.ServiceExceptionFactory;
import com.hcmute.project.instagram.backend.domain.exception.ServiceExceptionResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ServiceExceptionResolver extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = { ServiceExceptionBase.class })
  protected ResponseEntity<Object> handleServiceExceptionBase(RuntimeException ex, WebRequest request) {

    ServiceExceptionResponse serviceExceptionResponse = ((ServiceExceptionBase)ex).getServiceExceptionResponse();

    return handleExceptionInternal(ex, serviceExceptionResponse,
            new HttpHeaders(), serviceExceptionResponse.getStatusCode(), request);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    List<Violation> errors = ex.getBindingResult().getFieldErrors().stream()
            .map(x -> new Violation(x.getField(), x.getDefaultMessage()))
            .collect(Collectors.toList());

    ServiceExceptionResponse response = ServiceExceptionFactory.badRequest()
            .addMessage("Lỗi validation")
            .withData(errors)
            .getServiceExceptionResponse();

    return handleExceptionInternal(ex, response,
            new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler(value = { RuntimeException.class })
  protected ResponseEntity<Object> handleAllExceptions(RuntimeException ex, WebRequest request) {

    ServiceExceptionResponse responseBody = ServiceExceptionFactory.badRequest()
            .addMessage("Lỗi hệ thống")
            .withData(ex)
            .getServiceExceptionResponse();

    return handleExceptionInternal(ex, responseBody,
            new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }
}