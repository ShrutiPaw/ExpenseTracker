import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

public class GUI extends JFrame {
    private ExpenseManager manager = new ExpenseManager();
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel totalLabel;

    public GUI() {
        setTitle("💸 Modern Expense Tracker");
        setSize(800, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));

        manager.getAllExpenses().addAll(FileHandler.loadExpenses());

        // Top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Expense Tracker", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(new Color(0x3F51B5));

        totalLabel = new JLabel("Total: ₹ " + manager.getTotal(), JLabel.CENTER);
        totalLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        totalLabel.setForeground(new Color(0x333333));

        topPanel.add(titleLabel, BorderLayout.NORTH);
        topPanel.add(totalLabel, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(new Object[]{"Category", "Amount", "Date"}, 0);
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        refreshTable();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new TitledBorder("Expenses List"));
        add(scrollPane, BorderLayout.CENTER);

        // Form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(new TitledBorder("Add New Expense"));

        JPanel fields = new JPanel(new GridLayout(2, 2, 12, 12));
        JTextField categoryField = new JTextField();
        JTextField amountField = new JTextField();

        fields.add(new JLabel("Category:"));
        fields.add(categoryField);
        fields.add(new JLabel("Amount (₹):"));
        fields.add(amountField);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton addBtn = new JButton("➕ Add");
        JButton clearBtn = new JButton("🧹 Clear");

        styleButton(addBtn, new Color(0x4CAF50));
        styleButton(clearBtn, new Color(0xF44336));

        buttons.add(addBtn);
        buttons.add(clearBtn);

        formPanel.add(fields);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(buttons);

        add(formPanel, BorderLayout.SOUTH);

        // Listeners
        addBtn.addActionListener(e -> {
            try {
                String category = categoryField.getText().trim();
                double amount = Double.parseDouble(amountField.getText().trim());
                if (category.isEmpty() || amount <= 0) throw new IllegalArgumentException();
                Expense expense = new Expense(category, amount, LocalDate.now());
                manager.addExpense(expense);
                FileHandler.saveExpenses(manager.getAllExpenses());
                refreshTable();
                updateTotal();
                categoryField.setText("");
                amountField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Check amount and category.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        clearBtn.addActionListener(e -> {
            categoryField.setText("");
            amountField.setText("");
        });

        setVisible(true);
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Expense e : manager.getAllExpenses()) {
            tableModel.addRow(new Object[]{e.getCategory(), e.getAmount(), e.getDate()});
        }
    }

    private void updateTotal() {
        totalLabel.setText("Total: ₹ " + manager.getTotal());
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });
    }
}
