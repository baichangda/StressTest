package com.bcd.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MockDataScheduler{
    ScheduledExecutorService mockDataBlockPool = Executors.newScheduledThreadPool(1);
    ScheduledExecutorService sendPool = Executors.newScheduledThreadPool(1);


    Logger logger= LoggerFactory.getLogger(MockDataScheduler.class);

    MockDataBlockGenerator mockDataBlockGenerator;
    AtomicInteger mockDataBlockIndex=new AtomicInteger(0);

    MockClientBlock [] mockClientBlocks;

    LinkedBlockingQueue<SendData> queue=new LinkedBlockingQueue<>();

    MockDataSender mockDataSender;

    ScheduledExecutorService monitorPool = Executors.newSingleThreadScheduledExecutor();
    boolean monitor=false;
    AtomicInteger generateNum;
    AtomicInteger sendNum;

    /**
     * 构造模拟数据任务 和 发送任务 启动间隔
     */
    int taskDelayDiff;

    public MockDataScheduler(int period,MockClientBlock[] mockClientBlocks,MockDataBlockGenerator mockDataBlockGenerator,MockDataSender mockDataSender) {
        this.mockClientBlocks=mockClientBlocks;
        this.mockDataBlockGenerator=mockDataBlockGenerator;
        this.mockDataSender =mockDataSender;
        this.taskDelayDiff=3;
    }

    private void generateMockDataBlockAndOffer() {
        try {
            int curIndex=mockDataBlockIndex.getAndIncrement()%mockClientBlocks.length;
            if(mockClientBlocks[curIndex]==null){
                queue.put(SendData.EMPTY);
            }else{
                Date date=new Date();
                MockDataBlock dataBlock= mockDataBlockGenerator.generate(mockClientBlocks[curIndex],date,new Date(date.getTime()+taskDelayDiff* 1000L));
                queue.put(new SendData(mockClientBlocks[curIndex],dataBlock));
                if(monitor) {
                    generateNum.addAndGet(dataBlock.getData().length);
                }
            }
        }catch (InterruptedException e){
            logger.error("mockDataBlockPool error",e);
        }
    }

    public void init(){
        startMonitor();
        mockDataBlockPool.scheduleAtFixedRate(this::generateMockDataBlockAndOffer,3,1, TimeUnit.SECONDS);
        sendPool.scheduleAtFixedRate(()->{
            SendData data= queue.poll();
            if(data!=SendData.EMPTY){
                mockDataSender.send(data);
                if(monitor){
                    sendNum.addAndGet(data.getMockDataBlock().getData().length);
                }
            }
        },3+taskDelayDiff,1,TimeUnit.SECONDS);
    }

    public void startMonitor(){
        monitor=true;
        generateNum= new AtomicInteger();
        sendNum= new AtomicInteger();
        monitorPool.scheduleAtFixedRate(()-> {
            int num1= generateNum.getAndSet(0);
            int num2= sendNum.getAndSet(0);
            logger.info("generate speed:{}/s   send speed:{}/s",num1,num2);
        },3,3,TimeUnit.SECONDS);
    }


}
