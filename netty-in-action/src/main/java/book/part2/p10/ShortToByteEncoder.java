package book.part2.p10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * ClassName:ShortToByteEncoder.java
 * Reason:	 TODO ADD REASON
 *
 * @author   zhaozj
 * @since    Ver 1.1
 * @Date	 2017年11月2日
 */
public class ShortToByteEncoder extends MessageToByteEncoder<Short> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Short msg, ByteBuf out) throws Exception {
		out.writeShort(msg);
	}

}
