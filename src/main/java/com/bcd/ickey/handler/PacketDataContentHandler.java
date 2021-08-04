package com.bcd.ickey.handler;

import com.bcd.ickey.Parser_ICKey;
import com.bcd.ickey.data.PacketData;
import com.bcd.ickey.handler.impl.ContentHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Map;

public class PacketDataContentHandler extends ChannelInboundHandlerAdapter {
    Parser_ICKey context;

    Map<String, ChannelHandlerContext> idToCtx;

    String id;

    public PacketDataContentHandler(String id, Parser_ICKey context, Map<String, ChannelHandlerContext> idToCtx) {
        this.id=id;
        this.context = context;
        this.idToCtx=idToCtx;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //解析数据
        PacketData packetData=(PacketData)msg;
        if(ContentHandler.contentHandlers[packetData.getFlag()]==null){
            ContentHandler.contentHandlers[0].handle(packetData,ctx);
        }else{
            ContentHandler.contentHandlers[packetData.getFlag()].handle(packetData,ctx);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.idToCtx.put(this.id,ctx);
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        this.idToCtx.remove(this.id);
        super.channelInactive(ctx);
    }
}
