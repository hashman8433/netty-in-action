package book.part1.p2;

import java.net.InetSocketAddress;


import book.common.ConfigParameter;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * ClassName:EchoClient.java
 * Reason:	 TODO ADD REASON
 *
 * @author   zhaozj
 * @since    Ver 1.1
 * @Date	 2017年10月26日
 */
public class EchoClient {
	private String host;
	private int port;
	
	public EchoClient(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public void start() throws InterruptedException {
		EventLoopGroup group = new NioEventLoopGroup();

		try {
			Bootstrap b = new Bootstrap();
			b.group(group)
				.channel(NioSocketChannel.class)
				.remoteAddress(new InetSocketAddress(host, port))
				.handler(new ChannelInitializer() {

					@Override
					protected void initChannel(Channel ch) throws Exception {
						ch.pipeline().addLast(new EchoChannelHandler());
					}
				});
			
			ChannelFuture f = b.connect().sync();
			f.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully().sync();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		String host;
		int port;
		
		// if not input parameter  
		if(args.length != 2) {
			System.err.println(
					 "Usage: " + EchoClient.class.getSimpleName() +
					 " <HOST> <PORT>");
			host = ConfigParameter.HOST;
			port = Integer.parseInt(ConfigParameter.PORT);
			
		} else {
			host = args[0];
			port = Integer.parseInt(args[1]);
		}
		
		
		System.err.println(
				 "Default Server " + host + ":" + port);
		
		new EchoClient(host, port).start();
	}
}
