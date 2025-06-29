package magalu.challenger.challenger.application.dto;

import java.util.List;

public record OrderWithUserDTO(
        Long order_id,
        String date,
        String total,
        List<OrderItemDTO> products,
        UserDTO user
) {
}
