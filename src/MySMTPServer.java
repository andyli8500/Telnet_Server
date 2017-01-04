import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MySMTPServer {

	public static void main(String[] args) {
		try {
			ServerSocket listenSock = new ServerSocket(6013);
			while (true) {
				Socket client = listenSock.accept();
				Thread t = new Thread(new Connection(client, listenSock));
				t.start();
			}
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
	}

}
