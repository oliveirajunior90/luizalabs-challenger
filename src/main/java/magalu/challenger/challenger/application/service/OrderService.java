package magalu.challenger.challenger.application.service;

import magalu.challenger.challenger.application.dto.OrderItemDTO;
import magalu.challenger.challenger.application.dto.OrderWithUserDTO;
import magalu.challenger.challenger.application.dto.UserDTO;
import magalu.challenger.challenger.domain.entity.Order;
import magalu.challenger.challenger.domain.entity.OrderItem;
import magalu.challenger.challenger.infraestructure.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class OrderService {

    OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderWithUserDTO getOrderById(Long orderId) {
        Order order = orderRepository.findOrderById(orderId);

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
