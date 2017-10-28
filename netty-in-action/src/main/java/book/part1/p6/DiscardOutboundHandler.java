package book.part1.p6;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;

/**
 * ClassName:DiscardOutboundHandler.java
 * Reason:	 TODO ADD REASON
 *
 * @author   zhaozj
 * @since    Ver 1.1
 * @Date	 2017年10月28日
 */
@Sharable
public class DiscardOutboundHandler extends ChannelOutboundHandlerAdapter {
	
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
		ReferenceCountUtil.release(msg);
		promise.setSuccess();
	}
}
