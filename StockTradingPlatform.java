import java.util.*;

class Stock {
    private String symbol;
    private double price;

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public void updatePrice(double newPrice) {
        this.price = newPrice;
    }
}

class Transaction {
    private String type;
    private String stock;
    private int quantity;
    private double price;

    public Transaction(String type, String stock, int quantity, double price) {
        this.type = type;
        this.stock = stock;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public String toString() {
        return type + " | " + stock +
                " | Qty: " + quantity +
                " | Price: ₹" + price;
    }
}

public class StockTradingPlatform {

    static Scanner sc = new Scanner(System.in);

    static double balance = 100000;

    static ArrayList<Stock> market = new ArrayList<>();
    static HashMap<String, Integer> portfolio = new HashMap<>();
    static ArrayList<Transaction> history = new ArrayList<>();

    public static void main(String[] args) {

        initializeMarket();

        while (true) {

            System.out.println("\n==================================");
            System.out.println(" SMART STOCK TRADING PLATFORM ");
            System.out.println("==================================");
            System.out.println("Available Balance : ₹" + balance);
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Transaction History");
            System.out.println("6. Simulate Market Update");
            System.out.println("7. Exit");
            System.out.print("Enter Choice : ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    viewMarket();
                    break;

                case 2:
                    buyStock();
                    break;

                case 3:
                    sellStock();
                    break;

                case 4:
                    viewPortfolio();
                    break;

                case 5:
                    viewHistory();
                    break;

                case 6:
                    updateMarketPrices();
                    break;

                case 7:
                    System.out.println("Thank you for using the platform.");
                    return;

                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }

    private static void initializeMarket() {

        market.add(new Stock("TCS", 3800));
        market.add(new Stock("INFY", 1600));
        market.add(new Stock("RELIANCE", 2900));
        market.add(new Stock("HDFCBANK", 1700));
        market.add(new Stock("WIPRO", 520));
    }

    private static void viewMarket() {

        System.out.println("\n------ MARKET DATA ------");

        for (Stock stock : market) {
            System.out.println(
                    stock.getSymbol() +
                            " --> ₹" +
                            stock.getPrice());
        }
    }

    private static void buyStock() {

        viewMarket();

        System.out.print("\nEnter Stock Symbol : ");
        String symbol = sc.next().toUpperCase();

        Stock stock = findStock(symbol);

        if (stock == null) {
            System.out.println("Stock Not Found!");
            return;
        }

        System.out.print("Enter Quantity : ");
        int quantity = sc.nextInt();

        double totalCost = stock.getPrice() * quantity;

        if (totalCost > balance) {
            System.out.println("Insufficient Balance!");
            return;
        }

        balance -= totalCost;

        portfolio.put(
                symbol,
                portfolio.getOrDefault(symbol, 0) + quantity
        );

        history.add(
                new Transaction(
                        "BUY",
                        symbol,
                        quantity,
                        stock.getPrice())
        );

        System.out.println("Stock Purchased Successfully!");
    }

    private static void sellStock() {

        System.out.print("Enter Stock Symbol : ");
        String symbol = sc.next().toUpperCase();

        if (!portfolio.containsKey(symbol)) {
            System.out.println("You do not own this stock.");
            return;
        }

        System.out.print("Enter Quantity : ");
        int quantity = sc.nextInt();

        int owned = portfolio.get(symbol);

        if (quantity > owned) {
            System.out.println("Not enough shares.");
            return;
        }

        Stock stock = findStock(symbol);

        double amount = stock.getPrice() * quantity;

        balance += amount;

        portfolio.put(symbol, owned - quantity);

        if (portfolio.get(symbol) == 0) {
            portfolio.remove(symbol);
        }

        history.add(
                new Transaction(
                        "SELL",
                        symbol,
                        quantity,
                        stock.getPrice())
        );

        System.out.println("Stock Sold Successfully!");
    }

    private static void viewPortfolio() {

        System.out.println("\n------ PORTFOLIO ------");

        if (portfolio.isEmpty()) {
            System.out.println("No Stocks Purchased Yet.");
            return;
        }

        double totalValue = 0;

        for (String symbol : portfolio.keySet()) {

            int quantity = portfolio.get(symbol);

            Stock stock = findStock(symbol);

            double value = quantity * stock.getPrice();

            totalValue += value;

            System.out.println(
                    symbol +
                            " | Shares: " +
                            quantity +
                            " | Value: ₹" +
                            value
            );
        }

        System.out.println("\nPortfolio Value : ₹" + totalValue);
        System.out.println("Cash Balance    : ₹" + balance);
        System.out.println("Total Assets    : ₹" + (totalValue + balance));
    }

    private static void viewHistory() {

        System.out.println("\n------ TRANSACTION HISTORY ------");

        if (history.isEmpty()) {
            System.out.println("No Transactions Available.");
            return;
        }

        for (Transaction t : history) {
            System.out.println(t);
        }
    }

    private static void updateMarketPrices() {

        Random random = new Random();

        for (Stock stock : market) {

            double changePercent =
                    -5 + (10 * random.nextDouble());

            double newPrice =
                    stock.getPrice() +
                            (stock.getPrice() * changePercent / 100);

            stock.updatePrice(
                    Math.round(newPrice * 100.0) / 100.0
            );
        }

        System.out.println("Market Prices Updated Successfully!");
    }

    private static Stock findStock(String symbol) {

        for (Stock stock : market) {

            if (stock.getSymbol().equalsIgnoreCase(symbol)) {
                return stock;
            }
        }

        return null;
    }
}
