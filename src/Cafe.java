import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Cafe {

    private boolean open;
    private int workingTime;
    private static int activeCustomers = 0;
    private Barista barista;
    private Queue<Customer> customersOrders = new ConcurrentLinkedDeque<>();

    public Cafe(int maxOrders, int workingTime) {
        this.barista = new Barista(this, maxOrders);
        this.workingTime = workingTime;
        this.open = true;
        new Thread(barista).start();
    }

    public Queue<Customer> getCustomersOrders() {
        return customersOrders;
    }

    public int getWorkingTime() {
        return workingTime;
    }

    public void customerLeft() {
        activeCustomers--;
        if (activeCustomers == 0 && !isOpen()) {
            barista.finishWork();

        }
    }

    public void enterShop(Customer customer) throws InterruptedException {
        activeCustomers++;
        System.out.println(Color.BLUE + customer.getName() + " увійшов/увійшла до кав'ярні та планує зробити замовлення." + Color.DEFAULT);
        Thread.sleep(1000);
        System.out.println(Color.BLUE + customer.getName() + " зробив/зробила замовлення." + Color.DEFAULT);
        customersOrders.add(customer);
    }

    public synchronized void closeCafe() {
        this.open = false;
        System.out.println(Color.RED + "Кав'ярня зачинилась." + Color.DEFAULT);
        try {
            barista.waitToGoHome();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isOpen() {
        return open;
    }
}
