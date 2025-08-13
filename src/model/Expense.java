package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Expense {
    private final LocalDate date;
    private final String category;
    private final BigDecimal amount;
    private final String note;

    public Expense(LocalDate date, String category, BigDecimal amount, String note) {
        this.date = date; this.category = category; this.amount = amount; this.note = note;
    }
    public LocalDate getDate() { return date; }
    public String getCategory() { return category; }
    public BigDecimal getAmount() { return amount; }
    public String getNote() { return note; }

    // CSV helpers
    public String toCsv() {
        return date + "," + category + "," + amount + "," + note.replace(",", " ");
    }
    public static Expense fromCsv(String line) {
        String[] p = line.split(",", 4);
        return new Expense(LocalDate.parse(p[0]), p[1], new BigDecimal(p[2]), p[3]);
    }

    @Override public String toString() { return date+" | "+category+" | "+amount+" | "+note; }
    @Override public boolean equals(Object o){ return o instanceof Expense e &&
            Objects.equals(date,e.date)&&Objects.equals(category,e.category)&&Objects.equals(amount,e.amount)&&Objects.equals(note,e.note); }
    @Override public int hashCode(){ return Objects.hash(date,category,amount,note); }
}
