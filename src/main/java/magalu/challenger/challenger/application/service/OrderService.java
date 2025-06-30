package magalu.challenger.challenger.application.service;

import magalu.challenger.challenger.application.dto.OrderItemDTO;
import magalu.challenger.challenger.application.dto.OrderWithUserDTO;
import magalu.challenger.challenger.application.dto.PageResponseDTO;
import magalu.challenger.challenger.application.dto.UserDTO;
import magalu.challenger.challenger.domain.entity.Order;
import magalu.challenger.challenger.domain.entity.OrderItem;
import magalu.challenger.challenger.infraestructure.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderWithUserDTO getOrderById(Long orderId) {
        Order order = orderRepository.findOrderById(orderId);

        return transformOrderToDTO(order);
    }

    public PageResponseDTO<OrderWithUserDTO> getOrdersByDateRange(
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
    ) {
        Page<Order> orders = orderRepository.findByPurchaseDateBetween(startDate, endDate, pageable);

        List<OrderWithUserDTO> ordersContent = orders
                .getContent()
                .stream()
                .map(this::transformOrderToDTO).collect(Collectors.toList());

        return new PageResponseDTO<>(
            ordersContent,
            orders.getNumber(),
            orders.getSize(),
            orders.getTotalElements(),
            orders.getTotalPages()
        );
    }

    private OrderWithUserDTO transformOrderToDTO(Order order) {
        return new OrderWithUserDTO(
                order.getId(),
                order.getPurchaseDate().toString(),
                order.getTotalPrice().toString(),
                order.getItems().stream().map(this::transformOrderItemToDTO).collect(Collectors.toList()),
                new UserDTO(order.getUser().getId(), order.getUser().getName())
        );
    }

    private OrderItemDTO transformOrderItemToDTO(OrderItem orderItem) {
        return new OrderItemDTO(
                orderItem.getProductId(),
                orderItem.getProductValue().toString()
        );
    }
}
