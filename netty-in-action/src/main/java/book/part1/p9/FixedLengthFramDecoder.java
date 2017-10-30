package book.part1.p9;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * ClassName:FixedLengthFramDecoder.java
 * Reason:	 TODO ADD REASON
 *
 * @author   zhaozj
 * @since    Ver 1.1
 * @Date	 2017年10月30日
 */
public class FixedLengthFramDecoder extends ByteToMessageDecoder {

	private final int frameLength;
	public FixedLengthFramDecoder(int frameLength) {
		if(frameLength <= 0) {
			throw new IllegalArgumentException(
					"frameLength must be a positive integer: " + frameLength);
		}
		this.frameLength = frameLength;
	}
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		while(in.readableBytes() >= frameLength) {
			ByteBuf buf = in.readBytes(frameLength);
			out.add(buf);
		}
	}

}
