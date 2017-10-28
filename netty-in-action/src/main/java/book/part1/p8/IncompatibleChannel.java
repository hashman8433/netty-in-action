package book.part1.p8;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.oio.OioSocketChannel;

/**
 * ClassName:IncompatibleChannel.java
 * Reason:	 TODO ADD REASON
 *
 * @author   zhaozj
 * @since    Ver 1.1
 * @Date	 2017年10月28日
 */
public class IncompatibleChannel {
	
	public static void main(String[] args) {
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(group)
			.channel(OioSocketChannel.class)
			.handler(new SimpleChannelInboundHandler<ByteBuf>() {

				@Override
				protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
					System.out.println("Received data");
				}
				
			});
		ChannelFuture future = bootstrap.connect(
				new InetSocketAddress("www.manning.com", 80));
		future.syncUninterruptibly();
	}
}
