import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class FileHandler {
    private static final String FILE_PATH = "data/expenses.csv";

    public static void saveExpenses(List<Expense> expenses) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Expense e : expenses) {
                pw.println(e.toCSV());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Expense> loadExpenses() {
        List<Expense> expenses = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return expenses;

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String[] parts = sc.nextLine().split(",");
                if (parts.length == 3) {
                    String category = parts[0];
                    double amount = Double.parseDouble(parts[1]);
                    LocalDate date = LocalDate.parse(parts[2]);
                    expenses.add(new Expense(category, amount, date));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return expenses;
    }
}
