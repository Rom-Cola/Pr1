import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class Barista implements Runnable {
    private final Semaphore semaphore; // семафор для контролю кількості одночасних замовлень
    private Cafe cafe;
    private int maxOrders;
    private boolean isWorking = true;
    private CountDownLatch latch = new CountDownLatch(1);

    public Barista(Cafe cafe, int maxOrders) {
        this.cafe = cafe;
        this.maxOrders = maxOrders;
        this.semaphore = new Semaphore(maxOrders);
    }

    public void finishWork() {
        latch.countDown();
        isWorking = false;
        System.out.println(Color.RED + "Бариста завершив роботу та пішов додому." + Color.DEFAULT);
    }

    public void waitToGoHome() throws InterruptedException {
        latch.await();
    }

    public void serveCoffee(Customer customer) throws InterruptedException {
        if (cafe.isOpen()) {

            System.out.println(Color.YELLOW +  "Бариста почав готувати каву для " +
                    customer.getName() + "(Бариста одночасно виконує " +
                    (maxOrders - semaphore.availablePermits()) + " замовлень.)" + Color.DEFAULT);
            Thread.sleep(3000);
            System.out.println(Color.GREEN + "Бариста завершив готування кави для " + customer.getName() + Color.DEFAULT);
        } else {
            System.out.println(Color.PURPLE + "Баристі довелось відмовити " + customer.getName() + " у зв'язку з закриттям кав'ярні" + Color.DEFAULT);
            Thread.sleep(500);
        }
        semaphore.release();
        cafe.customerLeft();
    }

    @Override
    public void run() {
        while (isWorking) {
            try {
                if (!cafe.getCustomersOrders().isEmpty() ) {
                    semaphore.acquire();
                    Customer customer = cafe.getCustomersOrders().poll();
                    Runnable order = () -> {
                        try {
                            serveCoffee(customer);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                    };
                    new Thread(order).start();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }
    }
}