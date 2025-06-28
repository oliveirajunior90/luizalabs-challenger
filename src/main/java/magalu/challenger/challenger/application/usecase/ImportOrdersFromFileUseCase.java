package magalu.challenger.challenger.application.usecase;

import magalu.challenger.challenger.application.service.OrderImportService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@Service
public class ImportOrdersFromFileUseCase implements ImportOrdersFromFile {

    OrderImportService orderImportService;

    public ImportOrdersFromFileUseCase(OrderImportService orderImportService) {
        this.orderImportService = orderImportService;
    }

    public void execute(MultipartFile file) {

        if (file.isEmpty() || !Objects.requireNonNull(file.getOriginalFilename()).endsWith(".txt")) {
            throw new IllegalArgumentException("Arquivo inválido. Por favor, envie um arquivo .txt.");
        }

        try {
            Path tempFile = Files.createTempFile("orders-", ".txt");
            file.transferTo(tempFile.toFile());

            orderImportService.importFromFile(tempFile);

            Files.deleteIfExists(tempFile);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
