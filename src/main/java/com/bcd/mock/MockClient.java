package com.bcd.mock;

import io.netty.channel.ChannelHandlerContext;

public class MockClient {
    String id;
    ChannelHandlerContext context;

    public MockClient(String id, ChannelHandlerContext context) {
        this.id = id;
        this.context = context;
    }

    public String getId() {
        return id;
    }

    public ChannelHandlerContext getContext() {
        return context;
    }
}
