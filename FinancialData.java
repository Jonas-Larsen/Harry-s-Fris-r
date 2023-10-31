package openinghours;
import java.util.Random;

public class FinancialData {
    private double currentMonthEarnings;
    private int currentMonthCustomers;

    public FinancialData() {
        Random random = new Random();
        currentMonthCustomers = random.nextInt(200) + 50;
        currentMonthEarnings = currentMonthCustomers * 150;
    }

    public double getCurrentMonthEarnings() {
        return currentMonthEarnings;
    }

    public int getCurrentMonthCustomers() {
        return currentMonthCustomers;
    }

}
