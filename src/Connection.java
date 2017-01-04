import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Connection implements Runnable {
	DataInputStream in;
	DataOutputStream out;
	Socket client;
	ServerSocket server;
	String clientIn, tmpIn, sender, receiver, subject, date;
	ArrayList<String> body;
	PrintWriter write;
	boolean ifMail, ifRcpt;
	int j;

	public Connection(Socket aClientSocket, ServerSocket aServerSocket) {
		try {
			client = aClientSocket;
			server = aServerSocket;
			in = new DataInputStream(client.getInputStream());
			out = new DataOutputStream(client.getOutputStream());
			body = new ArrayList<String>();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void run() {
		try {
			// String data = in.readUTF();
			// out.writeUTF(data);
			ifMail = false;
			ifRcpt = false;
			date = new java.util.Date().toString();

			/* email counter */
			j = 0;

			/* Print welcome msg to client */
			PrintWriter pout = new PrintWriter(client.getOutputStream(), true);
			pout.println("Welcome! " + client.getInetAddress().getHostName()
					+ ". Ready at " + date);

			/* communicate with client */
			BufferedReader buffer = new BufferedReader(
					new InputStreamReader(in));
			while ((clientIn = buffer.readLine()) != null) {

				/* ignore the case problem */
				tmpIn = clientIn.toUpperCase();

				/* HELO command */
				if (tmpIn.equals("HELO")) {
					pout.println("Good day, "
							+ client.getInetAddress().getHostName()
							+ " :-), I am "
							+ server.getInetAddress().getHostName());
				}

				/* MAIL FROM: command */
				else if (tmpIn.contains("MAIL FROM:")) {
					ifMail = true;
					sender = clientIn.substring(10);
					pout.println(sender + " sender received OK");
				}

				/* RCPT TO: command */
				else if (tmpIn.contains("RCPT TO:")) {
					ifRcpt = true;
					receiver = clientIn.substring(8);
					pout.println(receiver + " user received OK");
				}

				/* DATA command */
				else if (tmpIn.equals("DATA")) {

					/* check if the Mail and RCPT commands are allocated */
					if (ifMail == false)
						pout.println("Sorry, I need a MAIL command first");
					if (ifMail == true && ifRcpt == false)
						pout.println("Sorry, I need a RCPT command first");

					/* ask user to input data */
					if (ifMail == true && ifRcpt == true) {

						/* reset the booleans */
						ifMail = ifRcpt = false;

						/* read the subject */
						pout.print("Subject: ");
						subject = buffer.readLine();

						/* read the body */
						pout.println("Enter the mail - end with a '.' on a line");
						while ((clientIn = buffer.readLine()) != null) {
							if (clientIn.equals(".")) {
								pout.println("I got that thank you. :-)");
								break;
							}
							body.add(clientIn);
						}
						write = new PrintWriter("./emails/email" + j + ".txt",
								"UTF-8");
						write.println("Message <" + j + ">");
						write.println("From: <" + sender + ">");
						write.println("To: <" + receiver + ">");
						write.println("Date: <" + date + ">");
						write.println("Subject: <" + subject + ">");
						write.println("Body: <");
						for (String s : body) {
							write.println(s);
						}
						write.println(">");
						j++;
						write.close();
					}
				}

				/* QUIT command */
				else if (tmpIn.equals("QUIT")) {
					pout.println("Closing connection ...");
					in.close();
					out.close();
					client.close();
					pout.println("Connection closed, bye bye.");
				} else {
					pout.println("I think you entered wrong command, please try again.");
				}

				// System.out.println(clientIn);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (client != null)
				try {
					client.close();
				} catch (IOException e) {
					/* close failed */
				}
		}
	}

}
