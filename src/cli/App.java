package cli;

import service.ExpenseService;
import storage.ExpenseStore;

import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        var service = new ExpenseService(new ExpenseStore(Paths.get("data/expenses.csv")));
        var in = new Scanner(System.in);

        while (true) {
            System.out.println("\n1.Add  2.List  3.Filter(CAT)  4.SumByCat  5.SumByMonth  0.Exit");
            System.out.print("Choose: ");
            String opt = in.nextLine().trim();
            try {
                switch (opt) {
                    case "1" -> {
                        System.out.print("Date (YYYY-MM-DD): "); LocalDate d = LocalDate.parse(in.nextLine().trim());
                        System.out.print("Category: "); String c = in.nextLine();
                        System.out.print("Amount: "); BigDecimal a = new BigDecimal(in.nextLine().trim());
                        System.out.print("Note: "); String note = in.nextLine();
                        service.add(d, c, a, note);
                        System.out.println("Saved.");
                    }
                    case "2" -> service.listAll().forEach(System.out::println);
                    case "3" -> { System.out.print("Category: "); String c = in.nextLine();
                        service.filterByCategory(c).forEach(System.out::println); }
                    case "4" -> service.sumByCategory().forEach((k,v)-> System.out.println(k+" -> "+v));
                    case "5" -> { System.out.print("Year-Month (YYYY-MM): ");
                        var ym = YearMonth.parse(in.nextLine().trim());
                        System.out.println("Total: " + service.sumByMonth(ym)); }
                    case "0" -> { System.out.println("Bye!"); return; }
                    default -> System.out.println("Invalid option.");
                }
            } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
        }
    }
}
