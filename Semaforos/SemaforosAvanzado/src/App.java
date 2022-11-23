import java.util.concurrent.Semaphore;

public class App extends Thread {
    private static Semaphore semaCarniceria = new Semaphore(4);
    private static Semaphore semaCharcute = new Semaphore(2);
    private boolean atendidoCarni = false;
    private boolean atendidoCharcu = false;

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            App hilo = new App();
            hilo.setName("Proceso " + i);
            hilo.start();
        }
    }

    public void gestionarCarnice() {
        try {
            semaCarniceria.acquire();
            System.out.println("El cliente: " + this.getName() + " esta siendo atendido en carniceria");
            Thread.sleep((long) (Math.random() * 10000));
            System.out.println("El cliente: " + this.getName() + " ha siendo atendido en carniceria");
            semaCarniceria.release();
            atendidoCarni = true;
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void gestionarCharcu() {
        try {
            semaCarniceria.acquire();
            System.out.println("El cliente: " + this.getName() + " esta siendo atendido en charcuteria");
            Thread.sleep((long) (Math.random() * 10000));
            System.out.println("El cliente: " + this.getName() + " ha siendo atendido en charcuteria");
            semaCarniceria.release();
            atendidoCharcu = true;
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        this.gestionarCarnice();
        do {
            if (semaCarniceria.availablePermits() > 0 && !atendidoCarni) {
                this.gestionarCarnice();
            }
            if (semaCharcute.availablePermits() > 0 && !atendidoCharcu) {
                this.gestionarCharcu();
            }

        } while (!atendidoCarni && !atendidoCharcu);
        System.out.println("El hilo: " + this.getName() + " ha finalizado");
    }

}
