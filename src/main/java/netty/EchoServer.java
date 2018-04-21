package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class EchoServer {
	private final int port;

	public EchoServer(int port) {
		this.port = port;
	}

	public void run() throws Exception {
		// Configure the server
		//用来accept客户端连接
		// NioEventLoopGroup 是用来处理I/O操作的多线程事件循环器，
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		//处理客户端数据的读写操作
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		//启动服务的辅助类
		ServerBootstrap bootstrap = new ServerBootstrap();
		System.out.println("server is ready running  端口="+port);
		try {
			bootstrap.group(bossGroup, workerGroup)
			//初始化channel用来接受客户端请求
			   /***
             * ServerSocketChannel以NIO的selector为基础进行实现的，用来接收新的连接
             * 这里告诉Channel如何获取新的连接.
             */
					.channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 100)
					.childOption(ChannelOption.SO_KEEPALIVE, true)
					.handler(new LoggingHandler(LogLevel.INFO))
					.childHandler(new ChannelInitializer<Channel>() {
						@Override
						protected void initChannel(Channel ch) throws Exception {
							ch.pipeline().addLast(new EchoServerHandler());
						}
					});

			// 绑定端口并启动去接收进来的连接
			ChannelFuture f = bootstrap.bind(port).sync();
			 System.out.println(EchoServer.class.getName() +" started and listen on " + f.channel().localAddress());
			// 这里会一直等待，直到socket被关闭
			f.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}

	}
	
	
	public static void main(String[] args) throws Exception{
		final int port;
		if(args.length>0) {
			port = Integer.parseInt(args[0]);
		}else {
			port=8080;
		}
		
		new EchoServer(port).run();
		System.out.println("server is running ......"+port);
	}
}
