package magalu.challenger.challenger.application.v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import magalu.challenger.challenger.application.dto.PageResponseDTO;
import magalu.challenger.challenger.application.dto.UserWithOrdersDTO;
import magalu.challenger.challenger.application.usecase.user.getuserwithorder.GetUserWithOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserControllerV1 {

    final GetUserWithOrder getUsersWithOrders;

    public UserControllerV1(GetUserWithOrder getUsersWithOrders) {
        this.getUsersWithOrders = getUsersWithOrders;
    }

    @Operation(
            summary = "Busca usuários com pedidos",
            description = "Retorna uma lista paginada de usuários e seus pedidos."
    )
    @GetMapping
    public ResponseEntity<PageResponseDTO<UserWithOrdersDTO>> getOrders(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PageResponseDTO<UserWithOrdersDTO> usersWithOrders = getUsersWithOrders.execute(pageable);
        if (usersWithOrders.content().isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(usersWithOrders);
    }
}
