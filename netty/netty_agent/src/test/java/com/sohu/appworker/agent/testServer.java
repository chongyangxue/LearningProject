package com.sohu.appworker.agent;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import nettyDemo.protobuf.AgentProtocol;
import nettyDemo.protobuf.RsaUtils;
import nettyDemo.protobuf.AgentProtocol.CommandRequest;
import nettyDemo.protobuf.AgentProtocol.CommandResponse;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufDecoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufEncoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import org.junit.Test;


public class testServer {

	@Test
	public void test() {
		ServerBootstrap bootstrap = new ServerBootstrap(new 
				NioServerSocketChannelFactory(Executors.newCachedThreadPool(), 
				Executors.newCachedThreadPool())); 

        bootstrap.setPipelineFactory(new ChannelPipelineFactory() { 
            @Override 
            public ChannelPipeline getPipeline() { 
            	ChannelPipeline pipeline = Channels.pipeline();
        		pipeline.addLast("frameDecoder", new ProtobufVarint32FrameDecoder());
        		pipeline.addLast("protobufDecoder", new ProtobufDecoder(AgentProtocol.CommandRequest.getDefaultInstance()));

        		pipeline.addLast("frameEncoder", new ProtobufVarint32LengthFieldPrepender());
        		pipeline.addLast("protobufEncoder", new ProtobufEncoder());
            	
  /*           	pipeline.addLast("decoder", new MessageDecoder()); 
                pipeline.addLast("encoder", new MessageEncoder()); */

        		pipeline.addLast("handler", new testCommandHandler());
        		return pipeline;
            } 
        });
        bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);
		bootstrap.setOption("child.reuseAddress", true);
		Channel serverChannel = bootstrap.bind(new InetSocketAddress(Integer.parseInt("6698")));
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
			System.out.println(e + "__________" + e.getMessage());
			CommandRequest request = (CommandRequest) e.getMessage();
			CommandResponse response;
			try {
				System.out.println(request.getArgs());
				//签名认证并解密
				boolean signSuccess = false;
				try{
					signSuccess = RsaUtils.verify(RsaUtils.getSignPublicKey(), 
							request.getArgs().toByteArray(), 
							request.getSignature().toByteArray());
					if(signSuccess) {
						byte[] result = RsaUtils.decript(RsaUtils.getPrivateKey(), request.getArgs().toByteArray());
						System.out.println("cmd___________" + new String(result));
					}		
				}catch (Exception e1){
					e1.printStackTrace();
				}
				response = CommandResponse.newBuilder()
						.setCommand(request.getCommand())
						.setResult("SUCCESS RESULT")
						.build();
			} catch (Exception ex) {
				response = CommandResponse.newBuilder()
						.setCommand(request.getCommand())
						.setResult(ex.toString())
						.build();
			}
			Channel channel = e.getChannel();
			ChannelFuture future = channel.write(response);
			System.out.println("[" + e.getRemoteAddress() + "] CommandRequest:" + request);
			System.out.println("[" + e.getRemoteAddress() + "] CommandResponse:" + response);
			
			future.addListener(ChannelFutureListener.CLOSE);
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
	        if (buffer.readableBytes() < 4) { 
	            return null;//(1) 
	        } 
	        int dataLength = buffer.getInt(buffer.readerIndex()); 
	        if (buffer.readableBytes() < dataLength + 4) { 
	            return null;//(2) 
	        } 

	        buffer.skipBytes(4);//(3) 
	        byte[] decoded = new byte[dataLength]; 
	        buffer.readBytes(decoded); 
	        String msg = new String(decoded);//(4) 
	        return msg; 
	    } 
	}
	
	public class MessageEncoder extends OneToOneEncoder { 
	    @Override 
	    protected Object encode( 
	            ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception { 
	        if (!(msg instanceof String)) { 
	            return msg;//(1) 
	        } 

	        String res = (String)msg; 
	        byte[] data = res.getBytes(); 
	        int dataLength = data.length; 
	        ChannelBuffer buf = ChannelBuffers.dynamicBuffer();//(2) 
	        buf.writeInt(dataLength); 
	        buf.writeBytes(data); 
	        return buf;//(3) 
	    } 
	}
}
