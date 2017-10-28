package book.part1.p6;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * ClassName:SimpleDiscardHandler.java
 * Reason:	 TODO ADD REASON
 *
 * @author   zhaozj
 * @since    Ver 1.1
 * @Date	 2017年10月28日
 */
@Sharable
public class SimpleDiscardHandler extends SimpleChannelInboundHandler<Object> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		// No need to do anything special
	}

}
