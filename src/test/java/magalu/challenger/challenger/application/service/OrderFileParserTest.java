package magalu.challenger.challenger.application.service;

import magalu.challenger.challenger.infraestructure.fileimport.parser.OrderFileParserDTO;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class OrderFileParserTest {

    private final OrderFileParser parser = new OrderFileParser();

    @Test
    void shouldParseFixedLengthLineCorrectly() {
        String line = "0000000014                                 Clelia Hills00000001460000000001      673.4920211125";

        OrderFileParserDTO record = parser.parseLine(line);

        assertThat(record.userId()).isEqualTo(70L);
        assertThat(record.userName()).isEqualTo("Palmer Prosacco");
        assertThat(record.orderId()).isEqualTo(753L);
        assertThat(record.productId()).isEqualTo(3L);
        assertThat(record.productValue()).isEqualByComparingTo(new BigDecimal("1836.74"));
        assertThat(record.purchaseDate()).isEqualTo(LocalDate.of(2021, 3, 8));
    }
}
