package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class EchoClient {
	private final String host;
	private final int port;
	private final int firstMessageSize;
	public EchoClient(String host,int port,int firstMessage) {
		this.host = host;
		this.port = port;
		this.firstMessageSize = firstMessage;
	}
	
	
	public void run() throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
//			创建客户端的引导程序
			Bootstrap b = new Bootstrap();
			//指定EventLoopGroup处理客户端事件，使用了 NioEventLoopGroup来处理非阻塞传输
			b.group(group)
			// 指定通道类型
			.channel(NioSocketChannel.class)
			//设置客户端连接地址
			//.remoteAddress(new InetSocketAddress(host, port))
			.option(ChannelOption.TCP_NODELAY,true)
			//使用ChannelInitializer来指定ChannelHandler, 一旦连接建立，通道创建，就会被调用
			.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					//将EchoClientHandler添加到ChannelPipeline中。 ChannelPipeline 持有通道的所有处理器
					ch.pipeline().addLast(new EchoClientHandler(firstMessageSize));
				}
			});
			
			//连接到服务器端，直到连接完成
			ChannelFuture f = b.connect(host,port).sync();
			//关闭客户端通道直到关闭完成。这里将阻塞
			f.channel().closeFuture().sync();
		}finally {
			//关闭引导和线程池，释放所有的资源
			group.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) throws Exception{
		new EchoClient("127.0.0.1",8080,56).run();
	}
}
