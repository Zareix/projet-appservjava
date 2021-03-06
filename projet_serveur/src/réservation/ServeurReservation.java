package r�servation;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * A la connexion d'un abonn� : lance le service de r�servation pour ce dernier
 * 
 * @see ServiceReservation
 */
public class ServeurReservation implements Runnable {
	private ServerSocket socketServ;

	public ServeurReservation(int port) throws IOException {
		this.socketServ = new ServerSocket(port);
	}

	@Override
	public void run() {
		try {
			System.err.println("Lancement du serveur de r�servation au port " + this.socketServ.getLocalPort());
			while (true)
				new Thread(new ServiceReservation(socketServ.accept())).start();
		} catch (IOException e) {
			try {
				this.socketServ.close();
			} catch (IOException e1) {
			}
			System.err.println("Arr�t du serveur au port " + this.socketServ.getLocalPort());
		}
	}

	@Override
	protected void finalize() throws Throwable {
		try {
			this.socketServ.close();
		} catch (IOException e1) {
		}
	}
}
