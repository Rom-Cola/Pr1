import java.util.Random;

class Customer {
    private final String name;

    public Customer(String name) {
        this.name = name;
    }
    public Customer() {
        this.name = generateRandomName();
    }

    public String getName() {
        return name;
    }

    private static String generateRandomName() {
        String[] possibleNames = {"John", "Emma", "Oliver", "Sophia", "Liam", "Ava", "Noah", "Mia", "James", "Isabella"};
        return possibleNames[new Random().nextInt(possibleNames.length)];
    }
}