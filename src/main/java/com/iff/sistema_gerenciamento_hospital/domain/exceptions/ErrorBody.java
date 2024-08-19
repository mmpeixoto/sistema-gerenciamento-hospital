package com.iff.sistema_gerenciamento_hospital.domain.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorBody {
    private HttpStatus status;
    private String message;

    public ErrorBody(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
