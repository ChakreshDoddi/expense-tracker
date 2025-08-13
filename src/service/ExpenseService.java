package service;

import model.Expense;
import storage.ExpenseStore;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

public class ExpenseService {
    private final ExpenseStore store;
    private final List<Expense> data;

    public ExpenseService(ExpenseStore store) {
        this.store = store;
        this.data = new ArrayList<>(store.load());
    }

    public void add(LocalDate date, String category, BigDecimal amount, String note) {
        data.add(new Expense(date, category.trim().toUpperCase(), amount, note));
        store.saveAll(data);
    }

    public List<Expense> listAll() { return Collections.unmodifiableList(data); }

    public List<Expense> filterByCategory(String category) {
        String c = category.trim().toUpperCase();
        return data.stream().filter(e -> e.getCategory().equals(c)).collect(Collectors.toList());
    }

    public Map<String, BigDecimal> sumByCategory() {
        return data.stream().collect(Collectors.groupingBy(
                Expense::getCategory,
                Collectors.mapping(Expense::getAmount, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
        ));
    }

    public BigDecimal sumByMonth(YearMonth ym) {
        return data.stream()
                .filter(e -> YearMonth.from(e.getDate()).equals(ym))
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
