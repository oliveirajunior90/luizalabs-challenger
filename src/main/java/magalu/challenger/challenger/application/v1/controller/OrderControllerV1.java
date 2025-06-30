package magalu.challenger.challenger.application.v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import magalu.challenger.challenger.application.dto.OrderWithUserDTO;
import magalu.challenger.challenger.application.dto.PageResponseDTO;
import magalu.challenger.challenger.application.service.OrderService;
import magalu.challenger.challenger.application.usecase.importordersfromfile.ImportOrdersFromFile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/order")
public class OrderControllerV1 {

    final ImportOrdersFromFile importOrdersFromFile;
    final OrderService orderService;

    public OrderControllerV1(ImportOrdersFromFile importOrdersFromFile, OrderService orderService) {
        this.importOrdersFromFile = importOrdersFromFile;
        this.orderService = orderService;
    }

    @GetMapping("/healthcheck")
    public String healthcheck () {
        return "OK Tico";
    }

    @Operation(summary = "Importa um arquivo de pedidos", description = "Faz upload de um arquivo contendo pedidos para importação em lote.")
    @PostMapping("/upload")
    public ResponseEntity<String> uploadOrdersFile(
            @Parameter(
                    description = "Arquivo CSV de pedidos",
                    required = true,
                    schema = @Schema(type = "string", format = "binary")
            )
            @RequestParam("file") MultipartFile file) {
        importOrdersFromFile.execute(file);
        return ResponseEntity.ok("File imported successfully.");
    }

    @Operation(summary = "Busca pedidos por intervalo de datas", description = "Retorna pedidos realizados entre as datas informadas, ordenados por data de compra.")
    @GetMapping(params = {"startDate", "endDate"})
    public ResponseEntity<PageResponseDTO<OrderWithUserDTO>> getOrdersByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(name = "asc", required = false, defaultValue="false") boolean asc,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size
    ) {

        if (startDate == null || endDate == null) {
            return ResponseEntity.noContent().build();
        }

        Sort sort = asc ? Sort.by("purchaseDate").ascending() : Sort.by("purchaseDate").descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        PageResponseDTO<OrderWithUserDTO> orders = orderService.getOrdersByDateRange(startDate, endDate, pageable);

        if (orders.content().isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orders);
    }

    @Operation(summary = "Busca pedido por ID")
    @GetMapping("/{id}")
    public ResponseEntity<OrderWithUserDTO> getOrderById(@PathVariable Long id) {
        OrderWithUserDTO order = orderService.getOrderById(id);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }

}
