package br.com.amarques.notasfiscais.converters;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateConverter {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static LocalDate toLocalDate(String date) {
        return LocalDate.parse(date, FORMATTER);
    }

    public static String toString(LocalDate date) {
        return FORMATTER.format(date);
    }
}
