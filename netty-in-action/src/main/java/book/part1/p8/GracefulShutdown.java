package book.part1.p8;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;

/**
 * ClassName:GracefulShutdown.java
 * Reason:	 TODO ADD REASON
 *
 * @author   zhaozj
 * @since    Ver 1.1
 * @Date	 2017年10月28日
 */
public class GracefulShutdown {
	public static void main(String[] args) {
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(group)
			.channel(NioSocketChannel.class)
			.handler(new SimpleChannelInboundHandler() {

				@Override
				protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
					System.out.println("Received data");
				}
				
			});
		bootstrap.connect(new InetSocketAddress("www.maning.com", 80));
		Future<?> future = group.shutdownGracefully();
		// block until the group has shutdown
		future.syncUninterruptibly();
	}
}
