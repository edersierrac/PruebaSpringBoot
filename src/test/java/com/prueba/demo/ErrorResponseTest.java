package com.prueba.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.prueba.demo.model.ErrorResponse;

public class ErrorResponseTest {

    @Test
    public void testGetMessage() {
        String message = "Error en la solicitud";
        ErrorResponse errorResponse = new ErrorResponse(message);
        assertEquals(message, errorResponse.getMessage());
    }

    @Test
    public void testSetMessage() {
        String message = "Error en la solicitud";
        ErrorResponse errorResponse = new ErrorResponse("");
        errorResponse.setMessage(message);
        assertEquals(message, errorResponse.getMessage());
    }
}