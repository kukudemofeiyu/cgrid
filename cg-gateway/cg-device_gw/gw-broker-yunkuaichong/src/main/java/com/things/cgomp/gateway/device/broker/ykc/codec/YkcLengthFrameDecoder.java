package com.things.cgomp.gateway.device.broker.ykc.codec;

import com.things.cgomp.gateway.device.broker.ykc.constant.YkcFrameConstants;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
public class YkcLengthFrameDecoder extends LengthFieldBasedFrameDecoder {

    public YkcLengthFrameDecoder() {
        super(8192,
                1,
                2,
                2,
                0);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        // 校验包头是否存在起始字符
        int frameStartFlag = in.getUnsignedByte(in.readerIndex());
        if (!YkcFrameConstants.frame_start.equals(frameStartFlag)) {
            byte[] dataPackage = new byte[in.readableBytes()];
            in.getBytes(in.readerIndex(), dataPackage);
            int fixCharIndex = in.indexOf(in.readerIndex(), in.readableBytes(), YkcFrameConstants.frame_start.byteValue());
            if (fixCharIndex == -1) {
                return null;
            }
            in.readerIndex(fixCharIndex);
        }
        return super.decode(ctx, in);
    }
}
