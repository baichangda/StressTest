package com.bcd.ickey;

import com.bcd.base.exception.BaseRuntimeException;
import com.bcd.ickey.handler.PacketDataContentHandler;
import com.bcd.ickey.handler.PacketDataHandler;
import com.bcd.ickey.mock.MockProp;
import com.bcd.mock.MockClient;
import com.bcd.mock.MockContext;
import com.bcd.mock.MockDataBlockGenerator;
import com.bcd.mock.MockIdGenerator;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TcpClient implements ApplicationListener<ContextRefreshedEvent> {

    Logger logger= LoggerFactory.getLogger(TcpClient.class);

    @Autowired
    MockProp mockProp;

    String [] ids;

    @Autowired
    Parser_ICKey context;

    @Autowired
    MockDataBlockGenerator mockDataBlockGenerator;

    @Autowired
    MockIdGenerator mockIdGenerator;

    Map<String, ChannelHandlerContext> idToCtx=new ConcurrentHashMap<>();

    public ChannelFuture newChannel(String id) throws InterruptedException {
        // 首先，netty通过ServerBootstrap启动服务端
        Bootstrap client = new Bootstrap();
        //第1步 定义线程组，处理读写和链接事件，没有了accept事件
        EventLoopGroup group = new NioEventLoopGroup();
        client.group(group );

        //第2步 绑定客户端通道
        client.channel(NioSocketChannel.class);

        //第3步 给NIoSocketChannel初始化handler， 处理读写事件
        client.handler(new ChannelInitializer<NioSocketChannel>() {  //通道是NioSocketChannel
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ch.pipeline().addLast(new PacketDataHandler(context));
                ch.pipeline().addLast(new PacketDataContentHandler(id,context,idToCtx));
            }
        });
        //连接服务器
        return client.connect(mockProp.getHost(), mockProp.getPort()).sync();
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initIds();

        startConnect();

        initMockContext();


    }

    private void initMockContext(){
        MockContext mockContext=new MockContext();
        List<MockClient> mockClientList=new ArrayList<>();
        idToCtx.forEach((k,v)->{
            MockClient mockClient=new MockClient(k,v);
            mockClientList.add(mockClient);
        });
        mockContext.init(mockProp.getPeriod(),
                mockClientList.toArray(new MockClient[0]),
                mockDataBlockGenerator
                );
        logger.info("finish initMockContext");
    }

    private void initIds(){
        ids=mockIdGenerator.generate();
    }

    private void startConnect(){
        try {
            for(int i=0;i< ids.length;i++){
                newChannel(ids[i]);
            }
        } catch (InterruptedException e) {
            throw BaseRuntimeException.getException(e);
        }
        try {
            while(idToCtx.size()<ids.length){
                logger.info("wait connecting,except started:{},actually:{}",ids.length,idToCtx.size());
                Thread.sleep(1000L);
            }
        } catch (InterruptedException e) {
            throw BaseRuntimeException.getException(e);
        }
        logger.info("all connected succeed,except started:{},actually:{}",ids.length,idToCtx.size());
    }
}
