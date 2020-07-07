package com.bcd.mock;

public class SendData {
    public final static SendData EMPTY=new SendData(null,null);

    MockClientBlock mockClientBlock;
    MockDataBlock mockDataBlock;

    public SendData(MockClientBlock mockClientBlock, MockDataBlock mockDataBlock) {
        this.mockClientBlock = mockClientBlock;
        this.mockDataBlock = mockDataBlock;
    }

    public MockClientBlock getMockClientBlock() {
        return mockClientBlock;
    }

    public void setMockClientBlock(MockClientBlock mockClientBlock) {
        this.mockClientBlock = mockClientBlock;
    }

    public MockDataBlock getMockDataBlock() {
        return mockDataBlock;
    }

    public void setMockDataBlock(MockDataBlock mockDataBlock) {
        this.mockDataBlock = mockDataBlock;
    }
}
