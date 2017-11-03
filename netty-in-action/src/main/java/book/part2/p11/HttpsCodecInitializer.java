package book.part2.p11;

import javax.net.ssl.SSLEngine;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

/**
 * ClassName:HttpsCodecInitializer.java
 * Reason:	 TODO ADD REASON
 *
 * @author   zhaozj
 * @since    Ver 1.1
 * @Date	 2017年11月2日
 */
public class HttpsCodecInitializer extends ChannelInitializer<Channel> {
	private final SslContext context;
	private final boolean isClient;
	
	public HttpsCodecInitializer(SslContext context, boolean isClient) {
		this.context = context;
		this.isClient = isClient;
	}
	
	@Override
	protected void initChannel(Channel ch) throws Exception {
		ChannelPipeline pipeline= ch.pipeline();
		SSLEngine engine = context.newEngine(ch.alloc());
		pipeline.addFirst("ssl", new SslHandler(engine));
		
		if(isClient) {
			pipeline.addLast(new HttpClientCodec());
		} else {
			pipeline.addLast(new HttpServerCodec());
		}
	}

}
