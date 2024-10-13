import java.util.concurrent.Semaphore;

public class Cafe {
    private final Semaphore semaphore;
    private boolean open;
    private int workingTime;
    private static int activeGuests = 0;
    private Barista barista;

    public Cafe(int maxOrders, int workingTime) {
        this.barista = new Barista();
        this.semaphore = new Semaphore(maxOrders);
        this.workingTime = workingTime;
        this.open = true;
    }

    public int getWorkingTime() {
        return workingTime;
    }

    public void customerLeft() {
        activeGuests--;
        if (activeGuests == 0 && !isOpen()) {
            barista.finishWork();
            System.out.println("Бариста завершив роботу та пішов додому.");
        }
    }

    public void enterShop(Customer customer) throws InterruptedException {
        activeGuests++;
        System.out.println(customer.getName() + " увійшов/увійшла до кав'ярні та планує зробити замовлення.");
        semaphore.acquire();
        Thread.sleep(1000);

        serveCoffee(customer);
    }

    public void serveCoffee(Customer customer) throws InterruptedException {
        if (isOpen()) {
            System.out.println(customer.getName() + " зробив/зробила замовлення.");
            Thread.sleep(400);
            System.out.println("Бариста готує каву для " + customer.getName());
            Thread.sleep(3000);
            System.out.println("Бариста завершив готування кави для " + customer.getName());
        } else {
            System.out.println("Баристі довелось відмовити " + customer.getName() + " у зв'язку з закриттям кав'ярні");
        }

        semaphore.release();
        customerLeft();
    }

    public synchronized void closeShop() {
        this.open = false;
        System.out.println("Кав'ярня зачинилась.");
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
