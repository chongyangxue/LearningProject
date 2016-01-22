package TCP.PojoEncoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class Server {

    private static Logger logger = LoggerFactory.getLogger(Server.class);
    
    private Map<String, Channel> map = new HashMap<String, Channel>();

    private int port;

    public Server(int port) {
        this.port = port;
    }

    public void start() {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.option(ChannelOption.SO_BACKLOG, 1024); //连接数
            bootstrap.option(ChannelOption.TCP_NODELAY, true); //不延迟，消息立即发送
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true); //长连接
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ClassResolver resolver = ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader());
                    socketChannel.pipeline()
                                 .addLast(new ObjectDecoder(1024 * 1024, resolver))
                                 .addLast(new ObjectEncoder())
                                 .addLast(new NettyServerHandler());
                }
            });
            ChannelFuture f = bootstrap.bind(port).sync();
            if (f.isSuccess()) {
                logger.info("启动Netty服务成功，端口号：" + this.port);
            }
            
            f.channel().closeFuture().sync();  //Wait until the server socket is closed.
        } catch(Exception e) {
            logger.error("启动Netty服务异常，异常信息：" + e.getMessage(), e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    public class NettyServerHandler extends SimpleChannelInboundHandler<JSONObject>  {
        
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            logger.info("建立新的连接:" + ctx.channel().toString());
            Channel channel = ctx.channel();
            JSONObject json = new JSONObject();
            json.put("msg", "Welcome");
            channel.writeAndFlush(json);
            json.put("name", "server");
            channel.writeAndFlush(json);
            map.put(channel.remoteAddress().toString(), channel);
        }
        
        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            logger.info("连接已关闭:" + ctx.channel().toString());
            map.remove(ctx.channel().remoteAddress().toString());
        }

        @Override
        public void channelRead0(ChannelHandlerContext ctx, JSONObject msg) {
            logger.info("服务器接收到消息：" + msg);
            JSONObject json = new JSONObject();
            json.put("msg", "recved");
            ctx.channel().writeAndFlush(json);
        }
        
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            logger.error(cause.getMessage(), cause);
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        Server server = new Server(9999);
        server.start();
    }
}
