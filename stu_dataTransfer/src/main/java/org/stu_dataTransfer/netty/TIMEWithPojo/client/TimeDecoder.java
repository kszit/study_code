package org.stu_dataTransfer.netty.TIMEWithPojo.client;

import java.util.List;

import org.stu_dataTransfer.netty.TIMEWithPojo.UnixTime;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class TimeDecoder extends ByteToMessageDecoder { // (1)


	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		 if (in.readableBytes() < 4) {
	            return; // (3)
	        }

		 out.add(new UnixTime(in.readInt()));
	}}