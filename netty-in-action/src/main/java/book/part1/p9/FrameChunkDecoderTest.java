package book.part1.p9;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
/**
 * ClassName:FrameChunkDecoderTest.java
 * Reason:	 TODO ADD REASON
 *
 * @author   zhaozj
 * @since    Ver 1.1
 * @Date	 2017年11月2日
 */
public class FrameChunkDecoderTest {
	
	@Test
	public void testFrameDecoded() {
		ByteBuf buf = Unpooled.buffer();
		for(int i = 0; i < 10; i++){
			buf.writeByte(i);
		}
		ByteBuf input = buf.duplicate();
		
		EmbeddedChannel channel = new EmbeddedChannel(
				new FrameChunkDecoder(3));
		
		assertTrue(channel.writeInbound(input.readBytes(2)));
		
		try {
			channel.writeInbound(input.readBytes(4));
			Assert.fail();
		} catch (Exception e) {
			// excepted exception
			System.out.println("channel.writeInbound(input.readBytes(4))  exception");
		}
		assertTrue(channel.writeInbound(input.readBytes(3)));
		assertTrue(channel.finish());
		
		// Read frames
		ByteBuf read = (ByteBuf) channel.readInbound();
		assertEquals(buf.readSlice(2), read);
		read.release();
		
		read = (ByteBuf) channel.readInbound();
		assertEquals(buf.skipBytes(4).readSlice(3), read);
		buf.release();
	}
}
