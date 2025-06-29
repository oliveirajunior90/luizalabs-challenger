package magalu.challenger.challenger.application.service;

import jakarta.transaction.Transactional;
import magalu.challenger.challenger.domain.entity.Order;
import magalu.challenger.challenger.domain.entity.OrderItem;
import magalu.challenger.challenger.domain.entity.User;
import magalu.challenger.challenger.infraestructure.repository.OrderItemRepository;
import magalu.challenger.challenger.infraestructure.repository.OrderRepository;
import magalu.challenger.challenger.infraestructure.repository.UserRepository;
import magalu.challenger.challenger.infraestructure.fileimport.parser.OrderFileParserDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class OrderImportService {

    private final OrderFileParser parser;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository itemRepository;

    public OrderImportService(OrderFileParser parser, UserRepository userRepository, OrderRepository orderRepository, OrderItemRepository itemRepository) {
        this.parser = parser;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
    }

    @Transactional
    public void importFromFile(Path path) {
        Map<Long, User> users = new HashMap<>();
        Map<Long, Order> orders = new HashMap<>();
        List<OrderItem> orderItems = new ArrayList<>();

        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(line -> {
                OrderFileParserDTO dto = parser.parseLine(line);

                users.putIfAbsent(dto.userId(), new User(dto.userId(), dto.userName()));
                orders.putIfAbsent(
                            dto.orderId(),
                            new Order(dto.orderId(), users.get(dto.userId()), dto.purchaseDate())
                );

                OrderItem item = new OrderItem(dto.orderId(), dto.productId(), dto.productValue());
                orderItems.add(item);
            });

            userRepository.saveAll(users.values());
            orderRepository.saveAll(orders.values());
            itemRepository.saveAll(orderItems);

        } catch (IOException e) {
            throw new UncheckedIOException("Erro ao ler arquivo", e);
        }
    }
}
