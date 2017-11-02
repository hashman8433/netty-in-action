package book.part1.p9;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

/**
 * ClassName:FrameChunkDecoder.java
 * Reason:	 TODO ADD REASON
 *
 * @author   zhaozj
 * @since    Ver 1.1
 * @Date	 2017年11月2日
 */
public class FrameChunkDecoder extends ByteToMessageDecoder{
	private final int maxFrameSize;
	
	public FrameChunkDecoder(int maxFrameSize) {
		this.maxFrameSize = maxFrameSize;
	}
	
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		int readableBytes = in.readableBytes();
		if(readableBytes > maxFrameSize) {
			in.clear();
			throw new TooLongFrameException();
		}
		ByteBuf buf = in.readBytes(readableBytes);
		out.add(buf);
	}

}
