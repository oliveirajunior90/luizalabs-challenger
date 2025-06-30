package magalu.challenger.challenger.application.service.orderimport;

import java.io.UncheckedIOException;
import java.nio.file.Path;

public interface OrderImportService {
    void importFromFile(Path path) throws UncheckedIOException;
}
