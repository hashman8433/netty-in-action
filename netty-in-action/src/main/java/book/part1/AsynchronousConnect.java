package book.part1;

import java.net.InetSocketAddress;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * ClassName:AsynchronousConnect.java
 * Reason:	 TODO ADD REASON
 *
 * @author   zhaozj
 * @since    Ver 1.1
 * @Date	 2017年10月26日
 */
public class AsynchronousConnect {
	public static void main(String[] args) {
		Channel channel = new NioServerSocketChannel();
		// Does not block
		ChannelFuture future = channel.connect(
				new InetSocketAddress("192.168.0.1", 25));
	}
}
