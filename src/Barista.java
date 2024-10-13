import java.util.concurrent.CountDownLatch;

class Barista {
    private CountDownLatch latch = new CountDownLatch(1);


    public void finishWork() {
        latch.countDown();
    }

    public void waitToGoHome() throws InterruptedException {
        latch.await();
    }
}