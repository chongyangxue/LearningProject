package TCP.StringEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * Copyright @ 2012 sohu.com Inc. All right reserved.
 * <p>
 * 实现通信Command
 * </p>
 * 
 * @author chongyangxue
 * @since 2012-8-3
 */
public class Client {
    
    private static Logger logger = LoggerFactory.getLogger(Client.class);
    
    private int port;
    private String host;

    public Client(int port, String host) throws InterruptedException {
        this.port = port;
        this.host = host;
    }

    public void start() throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.remoteAddress(host, port);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline()
                                 //.addLast("frameDecoder", new LineBasedFrameDecoder(1000))
                                 .addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()))
                                 .addLast("stringDecoder", new StringDecoder(CharsetUtil.UTF_8))
                                 .addLast("stringEncoder", new StringEncoder(CharsetUtil.UTF_8))
                                 .addLast(new NettyClientHandler());
                }
            });
            ChannelFuture future = bootstrap.connect(host, port).sync();
            if (future.isSuccess()) {
                logger.info("-------connect server success--------" );
            }
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            logger.error("Connect to {}:{} failed", host, port);
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public class NettyClientHandler extends SimpleChannelInboundHandler<String> {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            logger.info("channleActive");
            ctx.writeAndFlush("Hello Server\r\n");
        }

        @Override
        public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            logger.info("客户端收到服务器数据:" + msg);
        }
        
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            logger.error("Connection lost, try another!", cause);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Client client = new Client(9999, "localhost");
        client.start();
    }
}