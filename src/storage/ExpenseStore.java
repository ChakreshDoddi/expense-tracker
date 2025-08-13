package storage;

import model.Expense;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class ExpenseStore {
    private final Path file;

    public ExpenseStore(Path file) { this.file = file; }

    public List<Expense> load() {
        try {
            System.out.println("Testing git");
            if (!Files.exists(file)) return new ArrayList<>();
            List<String> lines = Files.readAllLines(file);
            List<Expense> out = new ArrayList<>();
            for (String s : lines) if (!s.isBlank()) out.add(Expense.fromCsv(s));
            return out;
        } catch (IOException e) { throw new RuntimeException(e); }
    }

    public void saveAll(List<Expense> expenses) {
        try {
            List<String> lines = new ArrayList<>();
            for (Expense e : expenses) lines.add(e.toCsv());
            Files.createDirectories(file.getParent());
            Files.write(file, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) { throw new RuntimeException(e); }
    }
}
