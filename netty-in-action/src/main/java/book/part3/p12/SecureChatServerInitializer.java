package book.part3.p12;

import javax.net.ssl.SSLEngine;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

/**
 * ClassName:SecureChatServerInitializer.java Reason: TODO ADD REASON
 *
 * @author zhaozj
 * @since Ver 1.1
 * @Date 2017年11月3日
 */
public class SecureChatServerInitializer extends ChatServerInitializer {
	private final SslContext context;

	public SecureChatServerInitializer(ChannelGroup group, SslContext context) {
		super(group);
		this.context = context;
	}

	@Override
	protected void initChannel(Channel ch) throws Exception {
		super.initChannel(ch);
		SSLEngine engine = context.newEngine(ch.alloc());
		ch.pipeline().addFirst(new SslHandler(engine));
	}

}
