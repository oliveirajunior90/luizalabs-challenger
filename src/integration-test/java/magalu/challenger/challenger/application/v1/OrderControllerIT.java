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
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class OrderControllerIT extends IntegrationTest {

    OrderRepository orderRepository;
    UserRepository userRepository;
    OrderItemRepository orderItemRepository;

    @Autowired
    public OrderControllerIT(OrderRepository orderRepository, UserRepository userRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void shouldImportOrdersFromFile() {
        ClassPathResource resource = new ClassPathResource("data_1.txt");
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", resource);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(
                "/api/v1/order/upload",
                requestEntity,
                String.class
        );

        assert response.getStatusCode().is2xxSuccessful();
    }

    @Test
    public void shouldGetOrders() {
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

        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/order", String.class);

        assert response.getBody().contains("John Doe");
        assert response.getBody().contains("Jane Doe");
    }

    @Test
    public void shouldGetOrderById() {
        User user = new User(1L, "John Doe");
        Order order = new Order(1L, user, LocalDate.of(2023, 10, 1));
        OrderItem orderItem = new OrderItem(order.getId(), 3L, new BigDecimal("100.00"));

        userRepository.save(user);
        orderRepository.save(order);
        orderItemRepository.save(orderItem);

        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/order/1", String.class);

        assert response.getBody().contains("John Doe");
        assert response.getBody().contains("100.00");
    }

    @Test
    public void shouldGetOrdersByDateRange() {
        User user = new User(1L, "John Doe");
        Order firstOrder = new Order(1L, user, LocalDate.of(2023, 10, 1));
        Order secondOrder = new Order(2L, user, LocalDate.of(2024, 10, 2));
        OrderItem firstOrderItem = new OrderItem(firstOrder.getId(), 3L, new BigDecimal("100.00"));
        OrderItem secondOrderItem = new OrderItem(secondOrder.getId(), 2L, new BigDecimal("200.00"));

        userRepository.save(user);
        orderRepository.saveAll(List.of(firstOrder, secondOrder));
        orderItemRepository.saveAll(List.of(firstOrderItem, secondOrderItem));

        ResponseEntity<String> response = restTemplate.getForEntity(
                "/api/v1/order?startDate=2023-10-01&endDate=2023-10-02&asc=true",
                String.class
        );

        assert response.getBody().contains("John Doe");
        assert response.getBody().contains("100.00");
        assert !response.getBody().contains("200.00");
    }
}
