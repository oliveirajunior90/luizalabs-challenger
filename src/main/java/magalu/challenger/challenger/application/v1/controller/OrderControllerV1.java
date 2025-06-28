package magalu.challenger.challenger.application.v1.controller;

import magalu.challenger.challenger.application.usecase.ImportOrdersFromFile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/v1/order")
public class OrderControllerV1 {

    final ImportOrdersFromFile importOrdersFromFile;

    public OrderControllerV1(ImportOrdersFromFile importOrdersFromFile) {
        this.importOrdersFromFile = importOrdersFromFile;
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

}
