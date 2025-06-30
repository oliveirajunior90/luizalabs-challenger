package magalu.challenger.challenger.application.v1;

import magalu.challenger.challenger.domain.entity.Order;
import magalu.challenger.challenger.domain.entity.OrderItem;
import magalu.challenger.challenger.domain.entity.User;
import magalu.challenger.challenger.infraestructure.repository.OrderItemRepository;
import magalu.challenger.challenger.infraestructure.repository.OrderRepository;
import magalu.challenger.challenger.infraestructure.repository.UserRepository;
import magalu.challenger.challenger.shared.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class UserControllerIT extends IntegrationTest {

    OrderRepository orderRepository;
    UserRepository userRepository;
    OrderItemRepository orderItemRepository;

    @Autowired
    public UserControllerIT(OrderRepository orderRepository, UserRepository userRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void shouldGetUser() {
        User firstUser = new User(1L, "John Doe");
        User secondUser = new User(2L, "Jane Doe");
        Order firstOrder = new Order(1L, firstUser, LocalDate.of(2023,10, 1));
        Order secondOrder = new Order(2L, secondUser, LocalDate.of(2023,10, 1));
        OrderItem firstOrderItem = new OrderItem(firstOrder.getId(), 3L, new BigDecimal("100.00"));
        OrderItem secondOrderItem = new OrderItem(firstOrder.getId(), 2L, new BigDecimal("200.00"));
        OrderItem thirdOrderItem = new OrderItem(secondOrder.getId(), 3L, new BigDecimal("300.00"));
        OrderItem fourthOrderItem = new OrderItem(secondOrder.getId(), 2L, new BigDecimal("400.00"));

        userRepository.saveAll(List.of(firstUser, secondUser));
        orderRepository.saveAll(List.of(firstOrder, secondOrder));
        orderItemRepository.saveAll(List.of(firstOrderItem, secondOrderItem,thirdOrderItem, fourthOrderItem));

        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/user", String.class);

        assert response.getBody().contains("John Doe");
        assert response.getBody().contains("Jane Doe");
    }

}
