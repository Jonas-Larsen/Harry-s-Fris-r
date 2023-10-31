import java.util.*;
import java.io.*;

public class DisplayAmountAndCredit {
    private static Scanner console = new Scanner(System.in);
    private static HashMap<String, Customer> customerList = new HashMap<>();

    public void run() {
        loadCustomerList();
        System.out.println("Here are your options: ");
        listOptions();
        chooseOption();
    }

    private void chooseOption() {
        System.out.print("Pick an option (0 to exit) [1-5]: ");
        String pick = console.nextLine().strip();

        switch (pick) {
            case "0":
                break;
            case "1":
                listCredit();
                break;
            case "2":
                listFullyPaid();
                break;
            case "3":
                registerPaidDebt();
                break;
            case "4":
                showPaymentDetails();
                break;
            case "5":
                System.out.print("Type name of customer: ");
                customerExists(bigFirstLetter(console.nextLine().strip()));
                break;
            default:
                chooseOption();
        }
    }

    private void listCredit() {
        String owingCustomers = "";
        for (Customer customer : customerList.values()) {
            if (customer.amount() != 0.0) {
                owingCustomers += customer.name() + "\n";
            }
        }    

        if (owingCustomers.equals("")) {
            System.out.println("No one owes any money.");
        } else {
            System.out.print(owingCustomers);
        }
    }

    private void listFullyPaid() {
        String hasPaid = "";
        for (Customer customer : customerList.values()) {
            if (customer.amount() == 0.0) {
                hasPaid += customer.name() + "\n";
            }
        }    

        if (hasPaid.equals("")) {
            System.out.println("No one has paid yet.");
        } else {
            System.out.print(hasPaid);
        }
    }

    private void registerPaidDebt() {
        System.out.print("Choose customer to register payment for: ");
        String choice = console.nextLine().strip();
        choice = bigFirstLetter(choice);

        if (customerList.containsKey(choice)) {
            if (customerList.get(choice).amount() != 0.0) {
                var customer = new Customer(choice, 0.0);
                customerList.put(choice, customer);
                saveCustomerList();
                System.out.println("Registered customer as having paid.");
            } else {
                System.out.println(choice + " has already paid.");
            }
        } else {
            System.out.println("No customer with that name.");
            registerPaidDebt();
        }

    }

    private void showPaymentDetails() {
        System.out.print("Choose customer to show details for: ");
        String choice = console.nextLine().strip();
        choice = bigFirstLetter(choice);

        if (customerList.containsKey(choice)) {
            if (customerList.get(choice).amount() == 0.0) {
                System.out.println(choice + " has paid in full.");
            } else {
                System.out.println(choice + " owes " + customerList.get(choice).amount() + " DKK.");
            }
        } else {
            System.out.println("No customer with that name.");
            showPaymentDetails();
        }
    }

    private void registerPayment(String name) {
        System.out.print("Type amount owed (0 if customer paid in full): ");
        double amount;
        try {
            amount = Double.parseDouble(console.nextLine().strip());
        } catch (Exception e) {
            System.out.println("Amount must be a number.");
            registerPayment(name);
            return;
        }
        var customer = new Customer(name, amount);
        customerList.put(name, customer);
        saveCustomerList();
    }

    private void saveCustomerList() {
        try {
            var fileWriter = new FileWriter(new File("PaymentStatus.csv"));
            for (Customer customer : customerList.values()) {
                fileWriter.write(customer.name() + ";" + customer.amount() + "\n");
            }
            fileWriter.close();
        } catch (Exception e) {}
    }

    private void loadCustomerList() {
        try {
            Scanner file = new Scanner(new File("PaymentStatus.csv"));
            while (file.hasNextLine()) {
                Scanner currentLine = new Scanner (file.nextLine());
                currentLine.useDelimiter(";");
                var customer = new Customer(currentLine.next(), Double.parseDouble(currentLine.next()));
                customerList.put(customer.name(), customer);                
            }
            file.close();
        } catch (Exception e) {}
    }

    private void listOptions() {
        System.out.println("""

                \t1) List customers who paid with credit
                \t2) List customers who have paid in full
                \t3) Register payment of owed debt
                \t4) Show specific customer payment details
                \t5) Register debt for customer
                """);
    }

    private void customerExists(String name) {
        if (customerList.containsKey(name)) {
            System.out.println("Customer already exists.");
            System.out.print("Do you want to override? [Y/n]: ");
            String choice = console.nextLine().strip().toLowerCase();

            switch (choice) {
                case "n":
                    break;
                case "y":
                default:
                    registerPayment(name);
            }

        } else {
            registerPayment(name);
        }
    }

    private String bigFirstLetter(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

}
