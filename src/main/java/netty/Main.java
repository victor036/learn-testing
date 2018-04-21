package netty;

public class Main {
	public static void main(String[] args){
	   /* EventLoopGroup bossGroup=new NioEventLoopGroup();
	    EventLoopGroup workerGroup = new NioEventLoopGroup();
	    try {
	        ServerBootstrap serverBootstrap=new ServerBootstrap();
	        serverBootstrap.group(bossGroup,workerGroup)
	            .channel(NioServerSocketChannel.class)
	            .childHandler(new ChannelInitializer<SocketChannel>() {
	                @Override
	                protected void initChannel(SocketChannel ch) throws Exception {
	                    ch.pipeline().addLast(new TcpServerHandler());
	                }
	            });
	        ChannelFuture f=serverBootstrap.bind(8080).sync();
	        f.channel().closeFuture().sync();
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }finally {  
	        workerGroup.shutdownGracefully();  
	        bossGroup.shutdownGracefully();  
	    } */ 
	}
}
