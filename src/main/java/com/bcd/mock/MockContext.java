package com.bcd.mock;


public class MockContext {
    //初始化
    public void init(int period,MockClient[] clients,MockDataBlockGenerator mockDataBlockGenerator){
        MockClientBlockSupplier mockClientBlockSupplier=new MockClientBlockSupplier(period, clients);
        MockDataSender mockDataSender=new MockDataSender();
        MockClientBlock[] mockClientBlocks = mockClientBlockSupplier.get();
        MockDataScheduler mockDataScheduler=new MockDataScheduler(period,mockClientBlocks,mockDataBlockGenerator,mockDataSender);
        mockDataScheduler.init();
    }
}
