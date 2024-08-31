package com.atam.pruebatecnica.response;

import com.atam.pruebatecnica.model.Author;
import com.atam.pruebatecnica.model.Book;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
public class BookDate extends Book {
    public static final String MM_DD_YYYY = "MM-dd-yyyy";
    private String date;

    public BookDate(long id, String title, int pages, String summary, Author author, int publicationTimestamp) {
        super(id, title, pages, summary, author, publicationTimestamp);
        this.date = getDate();
    }

    public BookDate(Book book) {
        super(book.getId(), book.getTitle(), book.getPages(), book.getSummary(), book.getAuthor(), book.getPublicationTimestamp());
    }

    @Override
    public String toString() {
        return "BookDate{" +
                "date='" + this.getDate() + '\'' +
                ", id=" + this.getId() +
                ", title='" + this.getTitle() + '\'' +
                ", author=" + this.getAuthor() +
                ", date=" + this.getDate() +
                '}';
    }

    public String getDate() {
        Instant instant = Instant.ofEpochSecond(this.getPublicationTimestamp());
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MM_DD_YYYY);
        return dateTime.format(formatter);
    }
}
