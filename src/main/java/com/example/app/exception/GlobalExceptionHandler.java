package com.example.app.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class) ResponseEntity<Map<String,Object>> notFound(RuntimeException e) { return error(HttpStatus.NOT_FOUND, e.getMessage()); }
    @ExceptionHandler(ConflictException.class) ResponseEntity<Map<String,Object>> conflict(RuntimeException e) { return error(HttpStatus.CONFLICT, e.getMessage()); }
    @ExceptionHandler(BadRequestException.class) ResponseEntity<Map<String,Object>> bad(RuntimeException e) { return error(HttpStatus.BAD_REQUEST, e.getMessage()); }
    @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<Map<String,Object>> validation(MethodArgumentNotValidException e) {
        Map<String,String> fields = new LinkedHashMap<>(); e.getBindingResult().getFieldErrors().forEach(x -> fields.put(x.getField(), x.getDefaultMessage()));
        Map<String,Object> body = body(HttpStatus.BAD_REQUEST, "Validation failed"); body.put("errors", fields); return ResponseEntity.badRequest().body(body);
    }
    @ExceptionHandler(DataIntegrityViolationException.class) ResponseEntity<Map<String,Object>> integrity(DataIntegrityViolationException e) { return error(HttpStatus.CONFLICT, "Database constraint violation"); }
    @ExceptionHandler(Exception.class) ResponseEntity<Map<String,Object>> unexpected(Exception e) { return error(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred"); }
    private ResponseEntity<Map<String,Object>> error(HttpStatus status, String message) { return ResponseEntity.status(status).body(body(status, message)); }
    private Map<String,Object> body(HttpStatus status, String message) { Map<String,Object> m=new LinkedHashMap<>(); m.put("timestamp", Instant.now()); m.put("status", status.value()); m.put("error", status.getReasonPhrase()); m.put("message", message); return m; }
}
