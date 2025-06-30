package magalu.challenger.challenger.application.usecase.user.getuserwithorder;

import magalu.challenger.challenger.application.dto.OrderDTO;
import magalu.challenger.challenger.application.dto.OrderItemDTO;
import magalu.challenger.challenger.application.dto.PageResponseDTO;
import magalu.challenger.challenger.application.dto.UserWithOrdersDTO;
import magalu.challenger.challenger.domain.entity.Order;
import magalu.challenger.challenger.domain.entity.OrderItem;
import magalu.challenger.challenger.domain.entity.User;
import magalu.challenger.challenger.infraestructure.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetUserWithOrderUseCase implements GetUserWithOrder {

    UserRepository userRepository;

    public GetUserWithOrderUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public PageResponseDTO<UserWithOrdersDTO> execute(Pageable pageable) {
        Page<User> userWithOrders = userRepository.findAll(pageable);

        List<UserWithOrdersDTO> userWithOrdersContent = userWithOrders.getContent().stream().map(this::transformToDTO).collect(Collectors.toList());

        return new PageResponseDTO<>(
                userWithOrdersContent,
                userWithOrders.getNumber(),
                userWithOrders.getSize(),
                userWithOrders.getTotalElements(),
                userWithOrders.getTotalPages()
        );
    }

    private UserWithOrdersDTO transformToDTO(User user) {
        return new UserWithOrdersDTO(
                user.getId(),
                user.getName(),
                user.getOrders().stream().map(this::transformOrderToDTO).collect(Collectors.toList())
        );
    }

    private OrderDTO transformOrderToDTO(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getPurchaseDate().toString(),
                order.getTotalPrice().toString(),
                order.getItems().stream().map(this::transformOrderItemToDTO).collect(Collectors.toList())
        );
    }

    private OrderItemDTO transformOrderItemToDTO(OrderItem orderItem) {
        return new OrderItemDTO(
                orderItem.getProductId(),
                orderItem.getProductValue().toString()
        );
    }

}
