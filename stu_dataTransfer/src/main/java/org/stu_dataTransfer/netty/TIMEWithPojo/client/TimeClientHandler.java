package org.stu_dataTransfer.netty.TIMEWithPojo.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

import org.stu_dataTransfer.netty.TIMEWithPojo.UnixTime;

/**
 * 可能存在一次不能读取到4个字节的问题。
 * @author Administrator
 *
 */
public class TimeClientHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
    	UnixTime m = (UnixTime) msg;
    	System.out.println(m);
    	ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
