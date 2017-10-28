package book.part1.p2;

import java.net.InetSocketAddress;

import book.common.ConfigParameter;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * ClassName:EchoServer.java
 * Reason:	 TODO ADD REASON
 *
 * @author   zhaozj
 * @since    Ver 1.1
 * @Date	 2017年10月26日
 */

public class EchoServer {
	
	
	private final int port;
	
	public EchoServer(int port) {
		this.port = port;
	}
	
	public static void main(String[] args) throws Exception {
		if(args.length != 1) {
			System.out.println(
					"Usage: " + EchoServer.class.getSimpleName() +
					" <"+ ConfigParameter.PORT + ">");
		}
		
		int port = Integer.parseInt(args.length != 1? ConfigParameter.PORT: args[0]);
		new EchoServer(port).start();
	}
	
	public void start() throws Exception {
		final EchoServerHandler serverHandler = new EchoServerHandler();
		EventLoopGroup group = new NioEventLoopGroup();
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(group, workerGroup)
				.channel(NioServerSocketChannel.class)
				.localAddress(new InetSocketAddress(port))
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(serverHandler);
					}
				})
				.option(ChannelOption.SO_BACKLOG, 128)          // (5)
	            .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)
			
			ChannelFuture f = b.bind().sync();
		
		} finally {
			group.shutdownGracefully().sync(); 
		}
	}
	
}
