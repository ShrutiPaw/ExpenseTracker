import java.time.LocalDate;

public class Expense {
    private String category;
    private double amount;
    private LocalDate date;

    public Expense(String category, double amount, LocalDate date) {
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String toCSV() {
        return category + "," + amount + "," + date;
    }
}
