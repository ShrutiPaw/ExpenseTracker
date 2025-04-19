import java.util.*;
import java.util.stream.Collectors;

public class ExpenseManager {
    private List<Expense> expenses = new ArrayList<>();

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public List<Expense> getAllExpenses() {
        return expenses;
    }

    public double getTotal() {
        return expenses.stream().mapToDouble(Expense::getAmount).sum();
    }

    public List<Expense> filterByCategory(String category) {
        return expenses.stream()
                .filter(e -> e.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }
}
