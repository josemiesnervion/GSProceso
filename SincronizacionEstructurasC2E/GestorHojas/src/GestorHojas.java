import java.util.concurrent.CopyOnWriteArrayList;

public class GestorHojas extends Thread {
//Ejercicio sin terminar, Investigar  CopyOnWriteArrayList
	private static CopyOnWriteArrayList<String> lista = new CopyOnWriteArrayList<String>();//hay que usar este que es thread safe

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			lista.add(new String("Texto" + i));
		}

		for (String string : lista) {
			System.out.println(string);
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			new GestorHojas().start();
		}

	}

}
