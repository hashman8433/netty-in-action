package book.part1.p8;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.oio.OioDatagramChannel;

/**
 * ClassName:UsingBootstrapWithDatagramChannel.java
 * Reason:	 TODO ADD REASON
 *
 * @author   zhaozj
 * @since    Ver 1.1
 * @Date	 2017年10月28日
 */
public class UsingBootstrapWithDatagramChannel {
	
	public static void main(String[] args) {
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(new OioEventLoopGroup())
			.channel(OioDatagramChannel.class)
			.handler(new SimpleChannelInboundHandler<DatagramPacket>() {

				@Override
				protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
					// Do something with the packet
					System.out.println("Received data");
				}
				
			});
		ChannelFuture future = bootstrap.bind(new InetSocketAddress(0));
		future.addListener(new ChannelFutureListener() {

			@Override
			public void operationComplete(ChannelFuture channelFuture) throws Exception {
				if (channelFuture.isSuccess()) {
					System.out.println("Channel bound");
				} else {
					System.out.println("Bind attempt failed");
					channelFuture.cause().printStackTrace();
				}
				
			}
			
		});
	}
}
