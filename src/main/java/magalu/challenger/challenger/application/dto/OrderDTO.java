package magalu.challenger.challenger.application.dto;

import java.util.List;

public record OrderDTO(
    Long order_id,
    String date,
    String total,
    List<OrderItemDTO> products
) {
}
