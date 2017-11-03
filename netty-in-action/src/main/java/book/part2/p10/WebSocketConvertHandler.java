package book.part2.p10;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

/**
 * ClassName:WebSocketConvertHandler.java Reason: TODO ADD REASON
 *
 * @author zhaozj
 * @since Ver 1.1
 * @Date 2017年11月2日
 */
public class WebSocketConvertHandler
		extends MessageToMessageCodec<WebSocketFrame, WebSocketConvertHandler.MyWebSocketFrame> {

	@Override
	protected void encode(ChannelHandlerContext ctx, WebSocketConvertHandler.MyWebSocketFrame msg, List<Object> out)
			throws Exception {
		// TODO Auto-generated method stub
		ByteBuf payload = msg.getData().duplicate().retain();
		switch (msg.getType()) {
		case BINARY:
			out.add(new BinaryWebSocketFrame(payload));
			break;
		case TEXT:
			out.add(new TextWebSocketFrame(payload));
		case CLOSE:
			out.add(new CloseWebSocketFrame(true, 0, payload));
		case CONTINUATION:
			out.add(new ContinuationWebSocketFrame(payload));
		case PONG:
			out.add(new PongWebSocketFrame(payload));
		case PING:
			out.add(new PingWebSocketFrame(payload));
		default:
			throw new IllegalStateException("Unsupported websocket msg " + msg);
		}
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, WebSocketFrame msg, List<Object> out) throws Exception {
		ByteBuf payload = msg.content();
		if (msg instanceof BinaryWebSocketFrame) {
			out.add(new MyWebSocketFrame(MyWebSocketFrame.FrameType.BINARY, payload));
		
		} else if (msg instanceof CloseWebSocketFrame) {
			out.add(new MyWebSocketFrame(MyWebSocketFrame.FrameType.CLOSE, payload));
		
		} else if (msg instanceof PingWebSocketFrame) {
			out.add(new MyWebSocketFrame(MyWebSocketFrame.FrameType.PING, payload));
		
		} else if (msg instanceof PongWebSocketFrame) {
			out.add(new MyWebSocketFrame(MyWebSocketFrame.FrameType.PONG, payload));
		
		} else if (msg instanceof TextWebSocketFrame) {
			out.add(new MyWebSocketFrame(MyWebSocketFrame.FrameType.TEXT, payload));
		
		} else if (msg instanceof ContinuationWebSocketFrame) {
			out.add(new MyWebSocketFrame(MyWebSocketFrame.FrameType.CONTINUATION, payload));
		
		} else {
			throw new IllegalStateException("Unsupported websocket msg " + msg);
		}
	}

	public static final class MyWebSocketFrame {
		public enum FrameType {
			BINARY, CLOSE, PING, PONG, TEXT, CONTINUATION
		}

		private final FrameType type;
		private final ByteBuf data;

		public MyWebSocketFrame(FrameType type, ByteBuf data) {
			this.type = type;
			this.data = data;
		}

		public FrameType getType() {
			return type;
		}

		public ByteBuf getData() {
			return data;
		}
	}
}
