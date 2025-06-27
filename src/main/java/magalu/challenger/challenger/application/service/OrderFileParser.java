package magalu.challenger.challenger.application.service;

import magalu.challenger.challenger.infraestructure.fileimport.parser.OrderFileParserDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class OrderFileParser {

    public OrderFileParserDTO parseLine(String line) {
        Long userId = Long.parseLong(line.substring(0, 10));
        String userName = line.substring(10, 55).trim();
        Long orderId = Long.parseLong(line.substring(55, 65));
        Long productId = Long.parseLong(line.substring(65, 75));
        BigDecimal productValue = new BigDecimal(line.substring(75, 87).trim());
        LocalDate purchaseDate = LocalDate.parse(line.substring(87, 95), DateTimeFormatter.ofPattern("yyyyMMdd"));

        return new OrderFileParserDTO(userId, userName, orderId, productId, productValue, purchaseDate);
    }
}
