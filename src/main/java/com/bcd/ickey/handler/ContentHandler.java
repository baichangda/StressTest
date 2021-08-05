package com.bcd.ickey.handler.impl;

import com.bcd.ickey.data.PacketData;
import io.netty.channel.ChannelHandlerContext;

public abstract class ContentHandler {
    public final static ContentHandler[] contentHandlers=new ContentHandler[256];

    public ContentHandler(int flag) {
        contentHandlers[flag]=this;
    }

    public abstract void handle(PacketData data, ChannelHandlerContext ctx);
}
