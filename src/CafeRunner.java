import java.util.Random;

public class CafeRunner implements Runnable{

    private Cafe cafe;
    public CafeRunner(Cafe cafe) {
        this.cafe = cafe;
    }
    public static void main(String[] args) throws InterruptedException {
        Cafe cafe = new Cafe(2, 8000);
        Runnable openCafe = () -> {
            while (cafe.isOpen()) {

                new Thread(new CafeRunner(cafe)).start();
                try {
                    Thread.sleep(new Random().nextInt(1000, 3000));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Thread cafeThread = new Thread(openCafe);

        cafeThread.start();

        Thread.sleep(cafe.getWorkingTime());

        cafe.closeShop();

    }

    @Override
    public void run() {
        try {
            cafe.enterShop(new Customer());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
