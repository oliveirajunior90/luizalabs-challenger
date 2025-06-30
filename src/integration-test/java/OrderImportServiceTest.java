package magalu.challenger.challenger.application.service;

import magalu.challenger.challenger.application.service.orderimport.OrderFileParser;
import magalu.challenger.challenger.application.service.orderimport.OrderImportService;
import magalu.challenger.challenger.application.service.orderimport.OrderImportServiceImpl;
import magalu.challenger.challenger.infraestructure.repository.OrderItemRepository;
import magalu.challenger.challenger.infraestructure.repository.OrderRepository;
import magalu.challenger.challenger.infraestructure.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class OrderImportServiceTest {

    private final OrderFileParser parser;
    private UserRepository userRepository;
    private OrderRepository orderRepository;
    private OrderItemRepository itemRepository;
    private OrderImportService service;

    public OrderImportServiceTest() {
        this.parser = new OrderFileParser();
    }

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        orderRepository = mock(OrderRepository.class);
        itemRepository = mock(OrderItemRepository.class);
        service = new OrderImportServiceImpl(parser, userRepository, orderRepository, itemRepository);
    }

    @Test
    void shouldImportFileSuccessfully() throws Exception {
        String lines = String.join("\n",
                "0000000070                              Palmer Prosacco00000007530000000003     1836.7420210308",
                "0000000075                                  Bobbie Batz00000007980000000002     1578.5720211116",
                "0000000049                               Ken Wintheiser00000005230000000003      586.7420210903"
        );
        Path tempFile = Files.createTempFile("order", ".txt");
        Files.write(tempFile, List.of(lines));

        service.importFromFile(tempFile);

        verify(userRepository).saveAll(any());
        verify(orderRepository).saveAll(any());
        verify(itemRepository).saveAll(any());
        Files.deleteIfExists(tempFile);
    }
}
