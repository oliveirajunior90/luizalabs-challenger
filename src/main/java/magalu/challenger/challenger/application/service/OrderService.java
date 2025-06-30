package magalu.challenger.challenger.application.service;

import magalu.challenger.challenger.application.dto.OrderWithUserDTO;
import magalu.challenger.challenger.application.dto.PageResponseDTO;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface OrderService {
    OrderWithUserDTO getOrderById(Long orderId);

    PageResponseDTO<OrderWithUserDTO> getOrdersByDateRange(
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
    );
}
