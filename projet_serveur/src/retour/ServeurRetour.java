package retour;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * A la connexion d'un abonn� : lance le service de retour pour ce dernier
 * 
 * @see ServiceRetour
 */
public class ServeurRetour implements Runnable {
	private ServerSocket socketServ;

	public ServeurRetour(int port) throws IOException {
		this.socketServ = new ServerSocket(port);
	}

	@Override
	public void run() {
		try {
			System.err.println("Lancement du serveur de retour au port " + this.socketServ.getLocalPort());
			while (true)
				new Thread(new ServiceRetour(socketServ.accept())).start();
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
