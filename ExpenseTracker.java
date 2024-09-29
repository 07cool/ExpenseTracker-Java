package projectfor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ExpenseTracker extends JFrame implements ActionListener {
    private JTextField amountField;
    private JTextField descriptionField;
    private JTextArea expenseList;
    private JLabel totalLabel;
    private ArrayList<Expense> expenses;
    private double total;

    public ExpenseTracker() {
        expenses = new ArrayList<>();
        total = 0.0;
        
        // Frame setup
        setTitle("Personal Expense Tracker");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        amountField = new JTextField(10);
        descriptionField = new JTextField(10);
        JButton addButton = new JButton("Add Expense");
        addButton.addActionListener(this);

        inputPanel.add(new JLabel("Amount (in ₹):"));
        inputPanel.add(amountField);
        inputPanel.add(new JLabel("Description:"));
        inputPanel.add(descriptionField);
        inputPanel.add(addButton);

        // Expense list area
        expenseList = new JTextArea();
        expenseList.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(expenseList);
        scrollPane.setPreferredSize(new Dimension(380, 200));

        // Total label
        totalLabel = new JLabel("Total: ₹0.00");

        // Add components to frame
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(totalLabel, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String amountText = amountField.getText();
        String description = descriptionField.getText();

        try {
            double amount = Double.parseDouble(amountText);
            Expense expense = new Expense(amount, description);
            expenses.add(expense);
            updateExpenseList();
            amountField.setText("");
            descriptionField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateExpenseList() {
        StringBuilder list = new StringBuilder();
        total = 0; // Reset total

        for (Expense expense : expenses) {
            list.append(expense).append("\n");
            total += expense.getAmount();
        }

        expenseList.setText(list.toString());
        totalLabel.setText("Total: ₹" + String.format("%.2f", total)); // Update total label
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ExpenseTracker::new);
    }

    private static class Expense {
        private double amount;
        private String description;

        public Expense(double amount, String description) {
            this.amount = amount;
            this.description = description;
        }

        public double getAmount() {
            return amount;
        }

        @Override
        public String toString() {
            return String.format("₹%.2f - %s", amount, description);
        }
    }
}

