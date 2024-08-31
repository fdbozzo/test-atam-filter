package com.atam.pruebatecnica.service;

import com.atam.pruebatecnica.model.Book;
import com.atam.pruebatecnica.response.BookDate;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
public class BookService {
    private final ObjectMapper objectMapper;

    public BookService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    public Optional<BookDate> getPublicationDateByFilter(String filter) throws IOException {
        List<Book> books;

        try (InputStream inputStream = getClass().getResourceAsStream("/books.json")) {
            books = objectMapper.readValue(inputStream, new TypeReference<List<Book>>() {
            });
        }

        return filter(filter, books);
    }

    private Optional<BookDate> filter(String filter, List<Book> books) {
//        log.info("Lista de libros recibida:");
//        books.forEach(book -> log.info(book.toString()));

        // 1. Imprimir libros sin fecha de publicación
        var librosSinFechaPub = books.stream()
                .filter(book -> book.getPublicationTimestamp() == null)
                .toList();
        log.info("Libros sin fecha de publicación:");
        librosSinFechaPub.forEach(book -> log.info(book.toString()));



        // 2. Obtención del libro más actual y de bio más corta que contenga la cadena a buscar en alguno de los 3 campos indicados.

        // LIBRE INTERPRETACIÓN:
        // El enunciado (especialmente los punto 2 y 3) es demasiado ambiguo y contradictorio para tener certeza de lo que pide.
        // Esta es mi interpretación del mismo, sobre todo por el orden en el que pide las cosas.

        // ASUNCIONES:
        // a. El orden de los puntos 2 y 3 está invertido, ya que no tiene sentido devolver un valor (2) para luego pedir un ordenamiento (3)
        //    donde no indica qué hacer con el mismo.
        // b. El punto 2 pide devolver "libros", cuando realmente tanto la firma de "filter" como la explicación posterior solo admite 1.
        // c. Un campo date adicional de tipo "timestamp" no puede tener formato de fecha "mm-dd-yyyy". Aquí guardo timestamp y formateo al consultarlo.
        // d. Para el agrupamiento por fecha, como no tiene sentido agrupar por el valor dado (con segundos) ni por "mm-dd-yyyy", he creado
        //    un nuevo método getPublicationDate() con la fecha real que admite comparación de fecha, ignorando la parte horaria.
        String normalizedFilter = filter.toLowerCase();
        return books.stream()
                .filter(book -> book.getPublicationTimestamp() != null && book.getPublicationTimestamp() > 0 &&
                        (book.getTitle().toLowerCase().contains(normalizedFilter) ||
                                book.getSummary().toLowerCase().contains(normalizedFilter) ||
                                book.getAuthor().getBio().toLowerCase().contains(normalizedFilter)))
                .map(book -> new BookDate(book))
                .sorted(Comparator.comparing(Book::getPublicationDate).reversed()
                        .thenComparing(book -> book.getAuthor().getBio().length()))
                .findFirst();
    }

}
