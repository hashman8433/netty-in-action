package book.part1.p8;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * ClassName:BootstrappingAServer1.java
 * Reason:	 TODO ADD REASON
 *
 * @author   zhaozj
 * @since    Ver 1.1
 * @Date	 2017年10月28日
 */
@Sharable
public class BootstrappingAServer1 {
	
	public static void main(String[] args) {
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup())
			.channel(NioServerSocketChannel.class)
			.childHandler(new SimpleChannelInboundHandler<ByteBuf>() {
				ChannelFuture connectFuture;
				@Override
				public void channelActive(ChannelHandlerContext ctx) throws Exception {
					Bootstrap bootstrap = new Bootstrap();
					bootstrap.channel(NioSocketChannel.class).handler(
							new SimpleChannelInboundHandler<ByteBuf>() {
								@Override
								protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
									System.out.println("Received data");
								}
							});
					bootstrap.group(ctx.channel().eventLoop());
					connectFuture = bootstrap.connect(
							new InetSocketAddress("www.maning.com", 80));
				}
				
				@Override
				protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
					if(connectFuture.isDone()) {
						// do something with the data
					}	
				}
			});
		ChannelFuture future = bootstrap.bind(new InetSocketAddress(8081));
		future.addListener(new ChannelFutureListener() {

			@Override
			public void operationComplete(ChannelFuture channelFuture) throws Exception {
				if (channelFuture.isSuccess()) {
					System.out.println("Server bound");
				} else {
					System.out.println("Bind attempt failed");
					channelFuture.cause().printStackTrace();
				}
			}
			
		});
		
	}
}
