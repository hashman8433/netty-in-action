package book.part1.p9;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
/**
 * ClassName:AbsIntegerEncoderTest.java
 * Reason:	 TODO ADD REASON
 *
 * @author   zhaozj
 * @since    Ver 1.1
 * @Date	 2017年11月2日
 */
public class AbsIntegerEncoderTest {
	
	@Test
	public void testEncoded() {
		ByteBuf buf = Unpooled.buffer();
		for(int i = 1; i < 10; i++){
			buf.writeInt(i * -1);
		}
		
		EmbeddedChannel channel = new EmbeddedChannel(
				new AbsIntegerEncoder());
		assertTrue(channel.writeOutbound(buf));
		assertTrue(channel.finish());
		
		// read bytes
		for(int i = 1; i < 10; i++){
			assertEquals(i, channel.readOutbound());
		}
		assertNull(channel.readOutbound());
	}
}
