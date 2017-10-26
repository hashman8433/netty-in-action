package book.part1.p2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;


import book.common.ConfigParameter;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

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
						ch.pipeline().addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
						ch.pipeline().addLast("decoder", new StringDecoder());
						ch.pipeline().addLast("encoder", new StringEncoder());

						ch.pipeline().addLast(new EchoChannelHandler());
					}
				});
			
			Channel channel = b.connect().sync().channel();
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while(true){
                channel.writeAndFlush(in.readLine() + "\r\n");
            }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			group.shutdownGracefully().sync();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		if(args.length != 2) {
			System.err.println(
					 "Usage: " + EchoClient.class.getSimpleName() +
					 " <"+ ConfigParameter.HOST +"> <"+ ConfigParameter.PORT +">");
		}
		
		String host = ConfigParameter.HOST;
		int port = Integer.parseInt(ConfigParameter.PORT);
		new EchoClient(host, port).start();
	}
}
