package magalu.challenger.challenger.application.dto;

import java.util.List;

public record UserWithOrdersDTO(
    Long id,
    String name,
    List<OrderDTO> orders
) {
}
