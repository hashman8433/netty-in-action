package book.part3.p12;

import java.net.InetSocketAddress;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;

/**
 * ClassName:SecureChatServer.java Reason: TODO ADD REASON
 *
 * @author zhaozj
 * @since Ver 1.1
 * @Date 2017年11月3日
 */
public class SecureChatServer extends ChatServer {
	private final SslContext context;

	public SecureChatServer(SslContext context) {
		this.context = context;
	}

	@Override
	protected ChannelInitializer<Channel> createInitializer(ChannelGroup group) {
		return new SecureChatServerInitializer(group, context);
	}

	public static void main(String[] args) throws Exception {
		/*if (args.length != 1) {
			System.err.println("Please give port as argument");
			System.exit(1);
		}*/
		
		int port = 8081;
		SelfSignedCertificate cert = new SelfSignedCertificate();
		SslContext context = SslContext.newServerContext(cert.certificate(), cert.privateKey());
		final SecureChatServer endpoint = new SecureChatServer(context);
		ChannelFuture future = endpoint.start(new InetSocketAddress(port));
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				endpoint.destroy();
			}
		});
		future.channel().closeFuture().syncUninterruptibly();
	}
}
