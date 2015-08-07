package org.stu_dataTransfer.netty.DISCARD;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/**
 * Handles a server-side channel.
 */
public class DiscardServerHandler extends ChannelHandlerAdapter { // (1)

	

	
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
    	//将接受的字符串输出到控制台
    	ByteBuf in = (ByteBuf) msg;
    	try {
        	while (in.isReadable()) { // (1)
	            System.out.print((char) in.readByte());
	            System.out.flush();
	        }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    
    
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}