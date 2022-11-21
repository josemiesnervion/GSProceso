import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class GestorHojas extends Thread {
	private static ConcurrentSkipListSet<String> lista = new ConcurrentSkipListSet<String>();// En principio con este
																								// funciona
	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {

			lista.add(new String("Texto" + i));
		}
		synchronized (lista) { // tendremos que hacer sincronizada esta parte del codigo para que se
								// reproduzcan en orden porque sino mezcla los numeros
			for (String string : lista) {
				System.out.println(string);
			}
		}
	}
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			new GestorHojas().start();
		}
	}
}
