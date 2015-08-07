package org.stu_dataTransfer.netty.TIMEWithPojo.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import org.stu_dataTransfer.netty.TIMEWithPojo.UnixTime;

public class TimeEncoder2 extends MessageToByteEncoder<UnixTime> {

	@Override
	protected void encode(ChannelHandlerContext ctx, UnixTime msg, ByteBuf out)
			throws Exception {
		out.writeInt(msg.value());
	}

}
