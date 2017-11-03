package book.part2.p11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * ClassName:HttpAggregatorInitializer.java
 * Reason:	 TODO ADD REASON
 *
 * @author   zhaozj
 * @since    Ver 1.1
 * @Date	 2017年11月2日
 */
public class HttpAggregatorInitializer extends ChannelInitializer<Channel> {
	private final boolean isClient;
	
	public HttpAggregatorInitializer(boolean isClient) {
		this.isClient = isClient;
	}

	@Override
	protected void initChannel(Channel ch) throws Exception {
		ChannelPipeline pipeline= ch.pipeline();		
		if(isClient) {
			pipeline.addLast("codec",  new HttpClientCodec());
		} else {
			pipeline.addLast("codec", new HttpServerCodec());
		}
		pipeline.addLast("aggregator", 
				new HttpObjectAggregator(512 * 1024));
	}
}
