package magalu.challenger.challenger.application.usecase.user.getuserwithorder;

import magalu.challenger.challenger.application.dto.OrderDTO;
import magalu.challenger.challenger.application.dto.OrderItemDTO;
import magalu.challenger.challenger.application.dto.UserWithOrdersDTO;
import magalu.challenger.challenger.domain.entity.Order;
import magalu.challenger.challenger.domain.entity.OrderItem;
import magalu.challenger.challenger.domain.entity.User;
import magalu.challenger.challenger.infraestructure.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
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

    public List<UserWithOrdersDTO> execute() {
        Pageable pageable = PageRequest.of(0, 10);
        List<User> userWithOrders = userRepository.findAllWithOrders(pageable);
        return userWithOrders.stream().map(this::transformToDTO).collect(Collectors.toList());
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
