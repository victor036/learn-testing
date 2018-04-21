package netty;

import java.util.logging.Level;
import java.util.logging.Logger;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoServerHandler extends ChannelInboundHandlerAdapter{
	private static final Logger logger = Logger.getLogger(EchoServerHandler.class.getName());
	/***
	 *  每当从客户端收到新的数据时， 这个方法会在收到消息时被调用
	 */
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("Server received: " + msg);
		//将接收到的消息写回去。注意并没有立即冲刷消息
		ctx.write(msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// 冲刷先前的所有的消息到客户端，并且在此操作完成之后关闭通道 
		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
		.addListener(ChannelFutureListener.CLOSE);
	}

	 /**
     * exceptionCaught() 事件处理方法是当出现 Throwable 对象才会被调用，即当 Netty 由于 IO
     * 错误或者处理器在处理事件时抛出的异常时。在大部分情况下，捕获的异常应该被记录下来 并且把关联的 channel
     * 给关闭掉。然而这个方法的处理方式会在遇到不同异常的情况下有不 同的实现，比如你可能想在关闭连接之前发送一个错误码的响应消息。
     */
    // 出现异常就关闭
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//		super.exceptionCaught(ctx, cause);
		 // 出现异常就关闭
		logger.log(Level.WARNING, "Unexpected exception from downstream", cause);
		ctx.close();
	}
	
}
