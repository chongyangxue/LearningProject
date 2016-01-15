package nettyDemo.protobuf;

import java.net.InetSocketAddress;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;

import nettyDemo.protobuf.AgentProtocol.CommandRequest;
import nettyDemo.protobuf.AgentProtocol.CommandResponse;

import org.jboss.netty.bootstrap.ClientBootstrap;
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
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.protobuf.ProtobufDecoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufEncoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProtobufClient{
	private final static Logger log = LoggerFactory.getLogger(ProtobufClient.class);
	
    private final ClientBootstrap bootstrap; 
    
    public ProtobufClient() {
    	log.info("ClientBootstrap is starting..."); 
    	bootstrap = new ClientBootstrap( 
                new NioClientSocketChannelFactory( 
                Executors.newCachedThreadPool(), 
                Executors.newCachedThreadPool())); 

        bootstrap.setPipelineFactory(new ChannelPipelineFactory() { 

            @Override 
            public ChannelPipeline getPipeline() { 
            	ChannelPipeline pipeline = Channels.pipeline();
        		pipeline.addLast("frameDecoder", new ProtobufVarint32FrameDecoder());
        		pipeline.addLast("protobufDecoder", new ProtobufDecoder(AgentProtocol.CommandResponse.getDefaultInstance()));

        		pipeline.addLast("frameEncoder", new ProtobufVarint32LengthFieldPrepender());
        		pipeline.addLast("protobufEncoder", new ProtobufEncoder());
        		
        		pipeline.addLast("handler", new CommandHandler());

                return pipeline; 
            } 
        }); 

        bootstrap.setOption("connectTimeoutMillis", 20000);
        bootstrap.setOption("tcpNoDelay", true); 
        //bootstrap.setOption("keepAlive", true); 
        bootstrap.setOption("reuseAddress", true);
    }
    

	public CommandResponse run(String ipAddress, String port, CommandRequest request) {
		CommandResponse response = null;
		Throwable throwable = null;
		try {
			// Start the connection attempt.
			ChannelFuture future = bootstrap.connect(new InetSocketAddress(ipAddress, Integer.parseInt(port)));

			// Wait until the connection attempt succeeds or fails.
			Channel channel = future.awaitUninterruptibly().getChannel();
			if (!future.isSuccess()) {
				throwable = future.getCause();
				log.error(throwable.getMessage(), throwable);
			}
			
			ChannelFuture writeFuture = channel.write(request);
			
			// Wait until all messages are flushed before closing the channel.
			if (writeFuture != null) {
				writeFuture.awaitUninterruptibly();
			}
			
			if(!writeFuture.isSuccess()) {
				throwable = future.getCause();
				log.error(throwable.getMessage(), throwable);
			}
			
			CommandHandler handler = (CommandHandler) channel.getPipeline().getLast();
			response = handler.getCommandResponse();
			
			// 纠正操作类型
			response = CommandResponse.newBuilder(response)
					.setCommand(request.getCommand()).build();
		} catch (Exception e) {
			throwable = e;
			log.error(throwable.getMessage(), throwable);
		}
		
		if(response == null && throwable != null) {
			response = CommandResponse.newBuilder()
					.setResult("new Result")
					.setCommand(request.getCommand())
					.build();
		}

        return response;
	}
	
	public void close() { 
        log.info("ClientBootstrap releases the external resources..."); 
        bootstrap.releaseExternalResources(); 
    } 
	
	public class CommandHandler extends SimpleChannelUpstreamHandler {
		
		final BlockingQueue<CommandResponse> answer = new SynchronousQueue<CommandResponse>();
		
		public CommandResponse getCommandResponse() {
			boolean interrupted = false;
	          for (;;) {
	              try {
	            	  CommandResponse response = answer.take();
	                  if (interrupted) {
	                      Thread.currentThread().interrupt();
	                  }
	                  return response;
	              } catch (InterruptedException e) {
	                  interrupted = true;
	              }
	          }
		}
		
		@Override
		public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e)
				throws Exception {
			if (e instanceof ChannelStateEvent) {
				log.debug(e.toString());
	        }
			super.handleUpstream(ctx, e);
		}
		
		@Override
		public void messageReceived(ChannelHandlerContext ctx, final MessageEvent e)
				throws Exception {
			e.getChannel().close().addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture future) {
                	CommandResponse response = (CommandResponse) e.getMessage();
                    boolean offered = answer.offer(response);
                    assert offered;
                }
            });
		}
		
		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, final ExceptionEvent e)
				throws Exception {
			log.error(e.getCause().getMessage(), e);
			e.getChannel().close().addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture future) {
                	// 无法获取操作类型
                	CommandResponse response = CommandResponse.newBuilder()
        					.setResult(e.getCause().toString())
        					.build();
                    boolean offered = answer.offer(response);
                    assert offered;
                }
            });
		}
	}
	
}