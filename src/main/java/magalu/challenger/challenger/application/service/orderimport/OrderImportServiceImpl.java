package magalu.challenger.challenger.application.service.orderimport;

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
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class OrderImportServiceImpl implements OrderImportService {

    private final OrderFileParser parser;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository itemRepository;

    public OrderImportServiceImpl(
            OrderFileParser parser,
            UserRepository userRepository,
            OrderRepository orderRepository,
            OrderItemRepository itemRepository
    ) {
        this.parser = parser;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
    }

    @Transactional
    public void importFromFile(Path path) {
        Map<Long, User> newUsers = new HashMap<>();
        Map<Long, Order> newOrders = new HashMap<>();
        List<OrderItem> orderItems = new ArrayList<>();

        try (Stream<String> lines = Files.lines(path)) {
            List<OrderFileParserDTO> dtos = lines
                    .map(parser::parseLine)
                    .toList();

            List<Long> fileUserIds = dtos.stream()
                    .map(OrderFileParserDTO::userId)
                    .distinct()
                    .toList();

            List<Long> fileOrderIds = dtos.stream()
                    .map(OrderFileParserDTO::orderId)
                    .distinct()
                    .toList();

            Map<Long, User> usersAlreadyExists = userRepository.findAllById(fileUserIds).stream()
                    .collect(Collectors.toMap(User::getId, u -> u));

            Map<Long, Order> orderAlreadyExists = orderRepository.findAllById(fileOrderIds).stream()
                    .collect(Collectors.toMap(Order::getId, o -> o));

            for (OrderFileParserDTO dto : dtos) {

                final User user = usersAlreadyExists.get(dto.userId());
                if (user == null) {
                    newUsers.computeIfAbsent(dto.userId(),
                            id -> new User(id, dto.userName()));
                }

                final Order order = orderAlreadyExists.get(dto.orderId());
                if (order == null) {
                    newOrders.computeIfAbsent(dto.orderId(),
                            id -> new Order(id, user, dto.purchaseDate()));
                }

                OrderItem item = new OrderItem(dto.orderId(), dto.productId(), dto.productValue());
                orderItems.add(item);
            }
            
            if (!newUsers.isEmpty()) {
                userRepository.saveAll(newUsers.values());
            }

            if (!newOrders.isEmpty()) {
                orderRepository.saveAll(newOrders.values());
            }

            if (!orderItems.isEmpty()) {
                itemRepository.saveAll(orderItems);
            }

        } catch (IOException e) {
            throw new UncheckedIOException("Erro ao ler arquivo", e);
        }
    }
}
