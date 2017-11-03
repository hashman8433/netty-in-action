package book.part1.p8;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * ClassName:BootstrappingAServer.java
 * Reason:	 TODO ADD REASON
 *
 * @author   zhaozj
 * @since    Ver 1.1
 * @Date	 2017年10月28日
 */
public class BootstrappingAServer {

	public static void main(String[] args) {
		
		NioEventLoopGroup group = new NioEventLoopGroup();
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(group)
			.channel(NioServerSocketChannel.class)
			.childHandler(new SimpleChannelInboundHandler<ByteBuf>() {

				@Override
				protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
					System.out.println("Received data");
				}

			});
		
		
		
		ChannelFuture future = bootstrap.bind(new InetSocketAddress(8081));
		future.addListener(new ChannelFutureListener() {

			@Override
			public void operationComplete(ChannelFuture channelFuture) throws Exception {
				if(channelFuture.isSuccess()){
					System.out.println("Server bound");
				}else {
					System.out.println("Bound attempt failed");
					channelFuture.cause().printStackTrace();
				}
			}
			
		});
	}

}
