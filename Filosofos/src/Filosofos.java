import java.util.ArrayList;

/*
 * Utilizar prioridades no seria una solucion correcta para este programa ya que las prioridades no siempre se cumplen y
 *  son algo aleatorias aparte si asignamos una prioridad alta a un filosofo seria el unico que comeria y el resto de hilos moririan
 */

//Esta solucion implementa en principio una solucion pero creo que si no fuera por los tiempos de espera se podria producir un deadLock
public class Filosofos extends Thread {
    // id que usaremos para identificar al filosofo
    int id;
    // Array de palillos que seran los que usaran los filosofos
    private static Palillo[] palillos = new Palillo[5];

    /**
     * Realizaremos un overray del run el cual asignara a cada filosofo sus
     * correspondientes palillos con los cuales tiene que comer
     */
    @Override
    public void run() {
        int palillo1 = 0; // Este sera el palillo de la izquierda
        int palillo2 = 0;// Este sera el palillo de la derecha

        if (id == 0) {// Para el filosofo 0 le asignaremos que pille el palillo n4 que sera el ultimo
                      // del array y le asignaremos el 0, como ya lo tenemos inicializado a 0 no
                      // tendremos que modificar su vcalor de nuevo
            palillo1 = palillos.length - 1;
        } else {// Para el resto de los casos los palillos se asignaran el id del filosofo que
                // equivaldra a un palillo el de la derecha y el de la izquierda que sera el
                // numero del filosofo -1
            palillo1 = id - 1;
            palillo2 = id;
        }
        while (true) {// Iniciaremos un bucle infinito para que los filosofos nunca paren de comer,
                      // realizaremos una llamada al metodo intentar comer
            intentarComer(palillo1, palillo2);
        }

    }

    public static void main(String[] args) {
        for (int i = 0; i < palillos.length; i++) {// Instanciaremos los palillos
            palillos[i] = new Palillo();
        }
        ArrayList<Filosofos> listaHilosFilosofos = new ArrayList<Filosofos>(); // tendremos una lista con los hilos que
                                                                               // usaremos para poder monitorearos
                                                                               // posteriormente en bucle
        Filosofos filosofo = null; // crearemos un filosofo el cual estara a nulo y lo instanciaremos a posterior

        for (int i = 0; i < 5; i++) { // Ejecutaremos 5 filosofos los cuales estaran con un id del 0 al 4
            filosofo = new Filosofos(); // instanciamos el filosofo
            filosofo.id = i; // le damos un id
            filosofo.start(); // iniciamos el hilo
            listaHilosFilosofos.add(filosofo);// añadiremos el filosofo a la lista de los hilos
        }
        while (true) {// con este bucle y la lista de hilos iremos verificando en el estado en el que
                      // se encuentran
            for (Filosofos filosofos : listaHilosFilosofos) {
                Thread.State state = filosofos.getState();// obtendremos el estado
                System.out.println("El filosofo:  " + filosofos.id + " se encuentra en el estado " + state);// imprimiremos
                                                                                                            // el estado
                                                                                                            // junto con
                                                                                                            // el id del
                                                                                                            // filosofo
                                                                                                            // correspondiente
            }
            try {
                Thread.sleep(3000); // realizaremos una pausa para no sobrecargar la cpu
            } catch (InterruptedException e) {
                System.out.println("Error se ha interrumpido el hilo");
            }
        }

    }

    private void intentarComer(int palillo1, int palillo2) {

        if (palillos[palillo1].getEstaSiendoUsado()) {// Imprimiremos en pantalla los palillos que estan siendo ocupados
                                                      // y en que ubicacion
            System.out.println("El palillo a la izquierda del filosofo: " + (id) + " esta siendo usado");
        }
        if (palillos[palillo2].getEstaSiendoUsado()) {
            System.out.println("El palillo a la derecha del filosofo: " + (id) + " esta siendo usado");
        }
        try { // realizaremos una pausa para que evitar problemass de sincronizacion y que
              // todos los filosofos puedan comer
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("Error se ha interrumpido el hilo");
        }

        synchronized (palillos) {// sincronizamos el objeto ya que vamos a modificar valores de estos, el primer
                                 // ciclo no entrara en el bucle pero cambiara el estado para que el resto si
                                 // tienen alguno de los palillos en true entren dentro del bucle con un wait el
                                 // que hara que se queden esperando un notify con el cambio de estado
            while (palillos[palillo1].getEstaSiendoUsado() || palillos[palillo2].getEstaSiendoUsado()) {
                try {
                    palillos.wait();// pausamos el hilo hasta que nos notifiquen
                } catch (InterruptedException e) {
                    System.out.println("se ha interrumpido el hilo");
                }
            }
            palillos[palillo1].setEstaSiendoUsado(true);// Ponemos a true los palillos que estan siendo usado para que
                                                        // otros no los usen
            palillos[palillo2].setEstaSiendoUsado(true);
        }
        System.out.println("El filosofo: " + id + " esta finalmente comiendo");// indicaremos que el filosofo esta
                                                                               // comiendo ya que aqui solo llegaran los
                                                                               // que tengan los dos palillos en true
        try {
            Thread.sleep((int) (2000 * (Math.random() * 5)));// asignamos una pausa para que coman
        } catch (InterruptedException e) {
            System.out.println("se ha producido un error mientras comia");
        }
        synchronized (palillos) {// realizamos una sincronizacion ya que habra dos filosofos comiendo a la vez y
                                 // si terminan de comer a la vez y intentan introducir valores pueden generar
                                 // problemas por lo tanto esta parte sera sincronizada
            palillos[palillo1].setEstaSiendoUsado(false);
            palillos[palillo2].setEstaSiendoUsado(false);

            palillos.notifyAll();// una vez los palillos esten a null los volveremos a notificar
        }
        System.out.println("El filosofo: " + id + " ha terminado de comer"); // indicaremos que el filosofo ha dejado de
                                                                             // comer

    }

}
