public class NumeroOculto extends Thread {
    private final static int NUMERO_AZAR = (int) (Math.random() * 101);// Generamos un numero aleatorio que sera el que
                                                                       // los hilos tengan que adivinar

    public static void main(String[] args) {// Crearemos y iniciaremos 10 hilos
        for (int i = 0; i < 10; i++) {
            new NumeroOculto().start();
        }

    }

    private static int numeroCompartido = 0;// numero compartido a la cual le asignaremos el valor 1 cuando se encuentre
                                            // por primera vez el numero

    /**
     * Metodo al cual se le pasara un numero y en base a unas condiciones devolvera
     * un numero u otro, tambien en caso de que encuentre el numero actualizara el
     * numero compartido a 1
     * 
     * @param num
     * @return
     */
    private synchronized static int PropuestaNumero(int num) {
        int numeroDevolver;
        if (numeroCompartido == 1) {
            numeroDevolver = -1;
        } else {
            if (num == NUMERO_AZAR) {
                numeroDevolver = 1;
                numeroCompartido = 1;
                Thread.interrupted();
            } else {
                numeroDevolver = 0;
            }
        }
        return numeroDevolver;
    }

    @Override
    public void run() {
        boolean finalizarBucle = false;
        int numeroDevuelto = 0;
        while (!finalizarBucle) {// Bucle infinito que solamente terminara cuando el metodo devuelva -1 es decir
                                 // entrar en el metodo y ver que ya otro hilo ha encontrado el numero
            switch (numeroDevuelto) {
                case (0):// Tiene que seguir buscando el numero
                    numeroDevuelto = PropuestaNumero((int) (Math.random() * 101));
                    break;
                case (1):// Es el hilo que ha encontrado el numero, dice cual es y finaliza el bucle
                    System.out.println("El numero se ha encontrado es: " + NUMERO_AZAR);
                    finalizarBucle = true;
                    break;
                case (-1):// Otro hilo ya ha encontrado el resultado por lo que se interrumpe el proceso y
                          // se sale del bucle
                    Thread.interrupted();
                    finalizarBucle = true;
                    break;

            }
        }
    }

}
