package com.bcd.mock;

import io.netty.buffer.ByteBuf;

public class MockDataBlock {
    int index;

    ByteBuf[] data;

    public MockDataBlock(int index, ByteBuf[] data) {
        this.index = index;
        this.data = data;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public ByteBuf[] getData() {
        return data;
    }

    public void setData(ByteBuf[] data) {
        this.data = data;
    }
}
