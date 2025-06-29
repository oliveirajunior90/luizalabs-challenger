package magalu.challenger.challenger.application.service;

import magalu.challenger.challenger.application.service.orderimport.OrderFileParser;
import magalu.challenger.challenger.infraestructure.fileimport.parser.OrderFileParserDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;

class OrderFileParserTest {

    private final OrderFileParser parser = new OrderFileParser();

    @Test
    void shouldParseFixedLengthLineCorrectly() {
        String line = "0000000014                                 Clelia Hills00000001460000000001      673.4920211125";

        OrderFileParserDTO record = parser.parseLine(line);

        Assertions.assertThat(record.userName()).isEqualTo("Clelia Hills");
        Assertions.assertThat(record.orderId()).isEqualTo(146L);
        Assertions.assertThat(record.productId()).isEqualTo(1L);
        Assertions.assertThat(record.productValue()).isEqualByComparingTo(new BigDecimal("673.49"));
        Assertions.assertThat(record.purchaseDate()).isEqualTo(LocalDate.of(2021, 11, 25));
    }
}
