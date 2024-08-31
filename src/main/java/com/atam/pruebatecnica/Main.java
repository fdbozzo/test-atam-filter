package com.atam.pruebatecnica;

import com.atam.pruebatecnica.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class Main {

    public static void main(String[] args) {
        BookService bookService = new BookService(new ObjectMapper());
        try {

            // STRING A BUSCAR
            String filter = "the";

            var response = bookService.getPublicationDateByFilter(filter);
            log.info("Respuesta: {}", response);

        } catch (IOException e) {
            log.info(e.getMessage());
        }

    }

}