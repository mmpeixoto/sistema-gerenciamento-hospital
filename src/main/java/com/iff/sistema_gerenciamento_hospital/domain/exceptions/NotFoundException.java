package com.iff.sistema_gerenciamento_hospital.domain.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String e) {
        super(e);
    }
}
