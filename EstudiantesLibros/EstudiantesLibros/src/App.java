
public class App extends Thread {
    public static void main(String[] args) throws Exception {
        for (int i = 1; i < 5; i++) {
            App hilo = new App();
            hilo.setName("Estudiante " + i);
            hilo.start();
        }
    }

    public static boolean listaLibros[] = new boolean[] { false, false, false, false, false, false, false, false, false,
            false, false };

    public static synchronized void usarLibro(int nLibro1, int nLibro2) {
        try {
            Thread.sleep((long) (3000 + Math.random() * 2000));
        } catch (InterruptedException e) {
            System.out.println("Error de interrupcion");
        }
        listaLibros[nLibro2] = false;
        listaLibros[nLibro1] = false;

    }

    @Override
    public void run() {

        int nLibro1 = 0;
        int nLibro2 = 0;
        boolean librosLeidos = false;
        synchronized (this) {
            do {
                nLibro1 = (int) ((Math.random() * 9) + 1);
                nLibro2 = (int) ((Math.random() * 9) + 1);

            } while (nLibro1 == nLibro2);
            do {

                synchronized (listaLibros) {
                    if (listaLibros[nLibro1] == false && listaLibros[nLibro2] == false) {
                        System.out.println(
                                "El " + this.getName() + " ha cogido el libro " + nLibro1 + " y el libro " + nLibro2);
                        App.usarLibro(nLibro1, nLibro2);
                        this.notifyAll();
                        librosLeidos = true;
                    } else {
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            System.out.println("Hilo interrumpido");
                        }
                    }
                }
            } while (librosLeidos == false);
            System.out.println("El " + this.getName() + " ha devuelto el libro " + nLibro1 + " y el libro " + nLibro2);
        }

    }
}
