package netty;

import java.util.logging.Level;
import java.util.logging.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class EchoClientHandler extends ChannelInboundHandlerAdapter {
	private static final Logger logger = Logger.getLogger(EchoClientHandler.class.getName());
	private final ByteBuf firstMessage;
	
	
	public EchoClientHandler(int firstMessageSize) {
		if(firstMessageSize<=0) {
			throw new IllegalArgumentException("firstMessageSize "+firstMessageSize);
		}
		firstMessage = Unpooled.buffer(firstMessageSize);
		for (int i = 0; i < firstMessage.capacity(); i++) {
			firstMessage.writeByte((byte)i);
		}
	}


	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		//  ctx.write(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));
		ctx.writeAndFlush(firstMessage);
	}


	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		//System.out.println("Client received: " + ByteBufUtil.hexDump(msg.readBytes(((ByteBuf) msg).readableBytes())));
		ctx.write(msg);
	}


	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		ctx.flush();
	}


	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		logger.log(Level.WARNING, "Unexpected exception from downstream", cause);
		ctx.close();
	}
	
	



}
