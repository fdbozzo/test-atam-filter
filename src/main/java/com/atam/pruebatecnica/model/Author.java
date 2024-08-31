package com.atam.pruebatecnica.model;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    @NonNull
    private String name;
    @NonNull
    private String firstSurname;
    private String bio;

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                ", firstSurname='" + firstSurname + '\'' +
                ", bioSize=" + bio.length() +
                '}';
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author author)) return false;

        return name.equals(author.name) && firstSurname.equals(author.firstSurname);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + firstSurname.hashCode();
        return result;
    }
}
