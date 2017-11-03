package book.part3.p12;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;

import com.waylau.netty.demo.websocketchat.TextWebSocketFrameHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * ClassName:HttpRequestHandler.java
 * Reason:	 TODO ADD REASON
 *
 * @author   zhaozj
 * @since    Ver 1.1
 * @Date	 2017年11月3日
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
	private final String wsUri;
	private static final File INDEX;
	
	static {
		URL location = HttpRequestHandler.class
				.getProtectionDomain()
				.getCodeSource()
				.getLocation();
		
		try {
			String path = location.toURI() + "index.html";
			path = !path.contains("file:") ? path : path.substring(5);
			INDEX = new File(path);
		} catch (URISyntaxException e) {
			throw new IllegalStateException(
					 "Unable to locate index.html", e);
		}
	}
	
	public HttpRequestHandler(String wsUri) {
		this.wsUri = wsUri;
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println(ctx.channel().remoteAddress() + "加入了");
		super.channelActive(ctx);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
		System.out.println("INDEX: " + INDEX);
		System.out.println("wsUri: " + wsUri);
		
		if(wsUri.equalsIgnoreCase(request.getUri())) {
			ctx.fireChannelRead(request.retain());
		} else {
			if (HttpHeaders.is100ContinueExpected(request)) {
				send100Continue(ctx);
			}
			RandomAccessFile file = new RandomAccessFile(INDEX, "r");
			HttpResponse response = new DefaultHttpResponse(
					request.getProtocolVersion(), HttpResponseStatus.OK);
			response.headers().set(
					HttpHeaders.Names.CONTENT_TYPE,
					"text/plain; charset=UTF-8");
			boolean keepAlive = HttpHeaders.isKeepAlive(request);
			if (keepAlive) {
				response.headers().set(
						HttpHeaders.Names.CONTENT_LENGTH, file.length());
				response.headers().set( HttpHeaders.Names.CONNECTION,
						HttpHeaders.Values.KEEP_ALIVE);
			}
			ctx.write(response);
			if (ctx.pipeline().get(SslHandler.class) == null) {
				ctx.write(new DefaultFileRegion(
						file.getChannel(), 0, file.length()));
			} else {
				ctx.write(new ChunkedNioFile(file.getChannel()));
			}
			ChannelFuture future = ctx.writeAndFlush(
					LastHttpContent.EMPTY_LAST_CONTENT);
			if (keepAlive) {
				future.addListener(ChannelFutureListener.CLOSE);
			}
		}
	}
	
	private static void send100Continue(ChannelHandlerContext ctx) {
		FullHttpResponse response = new DefaultFullHttpResponse(
				HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
		ctx.writeAndFlush(response);
	}


	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		EventLoopGroup listenerGroup = new NioEventLoopGroup();
		EventLoopGroup handlerGroup = new NioEventLoopGroup();
		
		ServerBootstrap b = new ServerBootstrap();
		b.group(listenerGroup,handlerGroup)
			.channel(NioServerSocketChannel.class)
			.childHandler(new ChannelInitializer() {

				@Override
				protected void initChannel(Channel ch) throws Exception {
					// TODO Auto-generated method stub
					ChannelPipeline pipeline = ch.pipeline();
					
					pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
					pipeline.addLast(new TextWebSocketFrameHandler());
					
				}
				
			});
		
		b.bind(8081).sync();
		System.out.println("Server: " + "8081");
	}
}
