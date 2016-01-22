package UDP;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class UdpServer {
	public static void main(String[] args) throws InterruptedException {
		Bootstrap bootstrap = new Bootstrap();
		EventLoopGroup group = new NioEventLoopGroup();
		bootstrap.group(group)
				 .channel(NioDatagramChannel.class)
				 .handler(new UdpServerHandler());

		bootstrap.bind(9999).sync().channel().closeFuture().await();
	}
}
