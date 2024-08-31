package com.atam.pruebatecnica.model;

import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @NonNull
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private Integer pages;
    private String summary;
    @NonNull
    private Author author;
    private Integer publicationTimestamp;

    public LocalDate getPublicationDate() {
        if (publicationTimestamp == null)
            return null;

        return Instant.ofEpochSecond(publicationTimestamp)
                .atZone(ZoneOffset.UTC)
                .toLocalDate();
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", publicationDate=" + getPublicationDate() +
                '}';
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;

        return id.equals(book.id) && title.equals(book.title) && author.equals(book.author) &&
                Objects.equals(publicationTimestamp, book.publicationTimestamp);
    }

    @Override
    public int hashCode() {
        int result = Long.hashCode(id);
        result = 31 * result + title.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + Integer.hashCode(publicationTimestamp);
        return result;
    }
}
