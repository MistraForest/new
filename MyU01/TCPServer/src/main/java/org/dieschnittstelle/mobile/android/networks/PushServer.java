package org.dieschnittstelle.mobile.android.networks;



import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Weitgehend unkommentierte Implementierung eines rudiment�ren Push Servers
 * 
 * @author N.N.
 * @author Martin Schaff�ner
 */
public class PushServer {

	protected static Logger logger = LoggerFactory.getLogger(PushServer.class);

	private static final int PORT = 1234;

	public static void main(String[] args) {
		PushServer client = new PushServer();
		client.doit();
	}

	private void doit() {
		try (ServerSocket serverSock = new ServerSocket(PORT)) {

			final PushSender pushSender = new PushSender();
			new Thread(pushSender).start();

			while (true) {
				logger.info("Waiting for a client...");

				// Achtung: sobald sich ein neuer Client mit dem Server
				// verbindet, werden Meldungen nur noch an diesen geschickt, da
				// der PushSender die Instanzvariable clientSocket f�r den
				Socket clientSocket = serverSock.accept();
				clientSocket.setSoTimeout(60000);
				pushSender.setClientSocket(clientSocket);
				// Versand von Nachrichten nutzt.

				new SocketObserver(clientSocket).run();
				clientSocket.close();
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}

}
