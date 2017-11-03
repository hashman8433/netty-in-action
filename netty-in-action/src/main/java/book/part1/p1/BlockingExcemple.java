package book.part1.p1;

import java.io.*;
import java.net.*;

/**
 * ClassName:BlockingExcemple.java
 * Reason:	 TODO ADD REASON
 *
 * @author   zhaozj
 * @since    Ver 1.1
 * @Date	 2017年10月25日
 */
public class BlockingExcemple {
	public static void main(String[] args) throws IOException {
		int portNumber = 8081;
		
		ServerSocket serverSocket = new ServerSocket(portNumber);
		Socket clientSocket = serverSocket.accept();
		BufferedReader in = new BufferedReader(
				new InputStreamReader(clientSocket.getInputStream()));
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		String request, response;
		while((request = in.readLine()) != null) {
			if("Done".equals(request)) {
				break;
			}
			response = processRequest(request);
			out.println(response);
		}
	}
	private static String processRequest(String request) {
		System.out.println("Receive: " + request);
		return "[You send]: " + request;
	}
}
