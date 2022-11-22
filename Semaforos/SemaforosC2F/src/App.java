import java.util.concurrent.Semaphore;

public class App extends Thread {
    private static Semaphore semaforo = new Semaphore(4);

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            App hilo = new App();
            hilo.setName("Proceso " + i);
            hilo.start();
        }
    }

    public void gestionar() {
        try {
            semaforo.acquire();
            System.out.println("El cliente: " + this.getName() + " esta siendo atendido");
            Thread.sleep((long) (Math.random() * 10000));
            System.out.println("El cliente: " + this.getName() + " ha siendo atendido");
            semaforo.release();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        this.gestionar();
    }

}
