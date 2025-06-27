package magalu.challenger.challenger.infraestructure.fileimport.parser;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OrderFileParserDTO(
    Long userId,
    String userName,
    Long orderId,
    Long productId,
    BigDecimal productValue,
    LocalDate purchaseDate
) {
}
