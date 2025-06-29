package magalu.challenger.challenger.application.v1.controller;

import magalu.challenger.challenger.application.dto.UserWithOrdersDTO;
import magalu.challenger.challenger.application.usecase.ImportOrdersFromFile;
import magalu.challenger.challenger.application.usecase.order.GetUserWithOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/api/v1/order")
public class OrderControllerV1 {

    final ImportOrdersFromFile importOrdersFromFile;
    final GetUserWithOrder getUsersWithOrders;

    public OrderControllerV1(ImportOrdersFromFile importOrdersFromFile, GetUserWithOrder getUsersWithOrders) {
        this.importOrdersFromFile = importOrdersFromFile;
        this.getUsersWithOrders = getUsersWithOrders;
    }

    @GetMapping("/healthcheck")
    public String healthcheck () {
        return "OK Tico";
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadOrdersFile(@RequestParam("file") MultipartFile file) {
        importOrdersFromFile.execute(file);
        return ResponseEntity.ok("File imported successfully.");
    }

    @GetMapping
    public ResponseEntity<List<UserWithOrdersDTO>> getOrders() {
        List<UserWithOrdersDTO> usersWithOrders = getUsersWithOrders.execute();
        if (usersWithOrders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(usersWithOrders);
    }

}
