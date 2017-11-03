package book.part1.p4;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;


/**
 * ClassName:PlainOioServer.java
 * Reason:	 TODO ADD REASON
 *
 * @author   zhaozj
 * @since    Ver 1.1
 * @Date	 2017年10月27日
 */
public class PlainOioServer {
	public void serve(int port) throws IOException {
		final ServerSocket socket = new ServerSocket(port);
		
		try {
			for(;;) {
				final Socket clientSocket = socket.accept();
				System.out.println(
						"Accepted connection from " + clientSocket);
				new Thread(new Runnable() {

					@Override
					public void run() {
						OutputStream out;
						
						try {
							out = clientSocket.getOutputStream();
							out.write("Hi!\r\n".getBytes(
									Charset.forName("UTF-8")));
							out.flush();
							clientSocket.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} finally {
							try {
								clientSocket.close();
							} catch (IOException e) {
								// ignore on close
							}
						}
					}
					
				}).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
