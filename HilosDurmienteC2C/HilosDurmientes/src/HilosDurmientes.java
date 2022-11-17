import java.util.ArrayList;

public class HilosDurmientes extends Thread {// Heredaremos de Thread
    public static void main(String[] args) {
        ArrayList<HilosDurmientes> listaHilos = new ArrayList<HilosDurmientes>();// Creamos un arraylist de hilos
        for (int i = 0; i < 5; i++) {// LLenamos el array con hilos
            listaHilos.add(new HilosDurmientes());
        }
        for (int i = 0; i < 5; i++) {// Le damos los nombres a los hilos
            listaHilos.get(i).setName("Hilo " + i);
        }
        for (int i = 0; i < 5; i++) {// Ejecutamos los hilos
            listaHilos.get(i).start();

        }
    }

    @Override
    public void run() {

        while (true) {// Iniciamos un bucle infinito
            try {
                System.out.println("Soy el bucle :" + getName() + " y estoy trabajando");// Imprimimos el nombre del
                                                                                         // hilo
                Thread.sleep(1000 * (int) (Math.random() * 10 + 1));// Lo pausamos un valor aleatorio entre 1 segundo y
                                                                    // 10
            } catch (InterruptedException e) {
                throw new RuntimeException(e);// Capturamos la excepcion en caso de que se interrumpa
            }
        }
    }
}
