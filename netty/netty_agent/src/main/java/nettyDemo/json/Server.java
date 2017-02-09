package nettyDemo.json;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Server {
	public void start() {
		ServerBootstrap bootstrap = new ServerBootstrap(new 
				NioServerSocketChannelFactory(Executors.newCachedThreadPool(), 
				Executors.newCachedThreadPool())); 

        bootstrap.setPipelineFactory(new ChannelPipelineFactory() { 
            @Override 
            public ChannelPipeline getPipeline() { 
            	ChannelPipeline pipeline = Channels.pipeline();
             	pipeline.addLast("decoder", new MessageDecoder()); 
                pipeline.addLast("encoder", new MessageEncoder()); 

        		pipeline.addLast("handler", new testCommandHandler());
        		return pipeline;
            } 
        });
        bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);
		bootstrap.setOption("child.reuseAddress", true);
		bootstrap.bind(new InetSocketAddress(Integer.parseInt("6698")));
		System.out.println("Server started on 6698...");
		try {
            Thread.sleep(3600L*60);
        } catch (Exception e) {
        }
	}
	
	public class testCommandHandler extends SimpleChannelUpstreamHandler {
		@Override
		public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e)
				throws Exception {
			if (e instanceof ChannelStateEvent) {
				System.err.println(e.toString());
	        }
			super.handleUpstream(ctx, e);
		}
		
		@Override
		public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
			//System.out.println(e.getMessage());
			String request = e.getMessage().toString();
			String response;
			try {
				//签名认证并解密
				boolean signSuccess = false;
				JSONObject json = JSONObject.parseObject(request);
				byte[] args = Base64.decode(json.getString("args"));
				byte[] signature = Base64.decode(json.getString("signature"));
				try{
					signSuccess = RsaUtils.verify(RsaUtils.getSignPublicKey(), args, signature);
					if(signSuccess) {
						byte[] result = RsaUtils.decript(RsaUtils.getPrivateKey(), args);
						System.out.println("args:" + new String(result));
					}
				}catch (Exception e1){
					e1.printStackTrace();
				}
				response = "Message Received!";
			} catch (Exception ex) {
				response = "Server Exception: " + ex.toString();
			}
			Channel channel = e.getChannel();
			ChannelFuture future = channel.write(response);
			System.out.println("[" + e.getRemoteAddress() + "] CommandRequest:" + request);
			System.out.println("[" + e.getRemoteAddress() + "] CommandResponse:" + response);
			
			//future.addListener(ChannelFutureListener.CLOSE);
		}
		
		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
				throws Exception {
			System.err.println(e.toString());
			System.err.println("Error message:  " + e.getCause().getMessage());
			e.getChannel().close();
		}
	}
	
	public class MessageDecoder extends FrameDecoder { 
	    @Override 
	    protected Object decode( 
	            ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception { 
	    	buffer.readBytes(buffer.readableBytes());
	    	return new String(buffer.array()); 
	    } 
	}
	
	public class MessageEncoder extends OneToOneEncoder { 
	    @Override 
	    protected Object encode( 
	            ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception { 
	    	byte[] data = null;
	    	if (msg instanceof String) { 
	    		String res = (String)msg;
	    		data = res.getBytes(); 
	        } else if(msg instanceof byte[]) {
	        	data = (byte[])msg;
	        }
	        ChannelBuffer buf = ChannelBuffers.dynamicBuffer(); 
	        buf.writeBytes(data); 
	        return buf;
	    } 
	}
	
	public static void main(String[] args) {
		new Server().start();
	}
}
