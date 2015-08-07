package org.stu_dataTransfer.netty.TIMEWithPojo.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import org.stu_dataTransfer.netty.TIMEWithPojo.UnixTime;

/**
 * Handles a server-side channel.
 */
public class TimeServerHandler extends ChannelHandlerAdapter { // (1)

	
	 @Override
	    public void channelActive(final ChannelHandlerContext ctx) { // (1)
		 	final ChannelFuture f = ctx.writeAndFlush(new UnixTime());
//	        f.addListener(ChannelFutureListener.CLOSE); // (4)
		 	f.addListener(new ChannelFutureListener() {
	            public void operationComplete(ChannelFuture future) {
	                assert f == future;
	                ctx.close();
	            }
	        }); // (4)   
	 }
	
	
    

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}