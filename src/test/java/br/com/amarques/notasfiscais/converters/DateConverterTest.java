package br.com.amarques.notasfiscais.converters;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class DateConverterTest {

    @Test
    void shouldConvertTextToLocalDate() {
        assertThat(DateConverter.toLocalDate("15/08/2020"), is(equalTo(LocalDate.of(2020, 8, 15))));
    }

    @Test
    void shouldConvertLocalDateToText() {
        assertThat(DateConverter.toString(LocalDate.of(2020, 8, 15)), is(equalTo("15/08/2020")));
    }
}
