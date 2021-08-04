package com.bcd.ickey.handler;

import com.bcd.ickey.Parser_ICKey;
import com.bcd.ickey.data.PacketData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class PacketDataHandler extends ChannelInboundHandlerAdapter{

    Parser_ICKey context;

    public PacketDataHandler(Parser_ICKey context) {
        this.context = context;
    }

    static Logger logger= LoggerFactory.getLogger(PacketDataHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //解析数据
        PacketData packetData=context.parse(PacketData.class,(ByteBuf)msg);
        super.channelRead(ctx,packetData);
    }
}
