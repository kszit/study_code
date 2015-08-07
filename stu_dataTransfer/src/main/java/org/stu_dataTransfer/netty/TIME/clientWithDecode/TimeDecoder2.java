package org.stu_dataTransfer.netty.TIME.clientWithDecode;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class TimeDecoder2 extends ByteToMessageDecoder { // (1)


	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		 if (in.readableBytes() < 4) {
	            return; // (3)
	        }

	        out.add(in.readBytes(4)); // (4)
	}}