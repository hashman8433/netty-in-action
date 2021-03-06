package book.part2.p10;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

/**
 * ClassName:SafeByteToMassageDecoder.java
 * Reason:	 TODO ADD REASON
 *
 * @author   zhaozj
 * @since    Ver 1.1
 * @Date	 2017年11月2日
 */
public class SafeByteToMassageDecoder extends ByteToMessageDecoder {
	private static final int MAS_FRAME_SIZE = 1024;
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		int readable = in.readableBytes();
		if(readable > MAS_FRAME_SIZE) {
			in.skipBytes(readable);
			throw new TooLongFrameException("Frame too big");
		}
		// do something
	}

}
