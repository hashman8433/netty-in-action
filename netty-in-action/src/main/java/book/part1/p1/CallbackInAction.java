package book.part1.p1;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * ClassName:CallbackInAction.java
 * Reason:	 TODO ADD REASON
 *
 * @author   zhaozj
 * @since    Ver 1.1
 * @Date	 2017年10月26日
 */
public class CallbackInAction {
	public static void main(String[] args) {
		Channel channel = new NioServerSocketChannel();
		// Does not block
		ChannelFuture future = channel.connect(
				new InetSocketAddress("192.168.0.1", 25));
		future.addListener(new ChannelFutureListener() {

			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				if (future.isSuccess()) {
					ByteBuf buffer = Unpooled.copiedBuffer(
							"Hello", Charset.defaultCharset());
					ChannelFuture wf = future.channel().writeAndFlush(buffer);
				}
			}
			
		});
	}
}
