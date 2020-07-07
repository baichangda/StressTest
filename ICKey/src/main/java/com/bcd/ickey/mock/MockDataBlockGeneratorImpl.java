package com.bcd.ickey.mock;

import com.bcd.base.exception.BaseRuntimeException;
import com.bcd.ickey.Parser_ICKey;
import com.bcd.ickey.data.PacketData;
import com.bcd.ickey.data.out.VehicleData;
import com.bcd.mock.MockClientBlock;
import com.bcd.mock.MockDataBlock;
import com.bcd.mock.MockDataBlockGenerator;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Date;

@Component
public class MockDataBlockGeneratorImpl implements MockDataBlockGenerator , ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    MockProp mockProp;

    @Autowired
    Parser_ICKey parser;

    PacketData<VehicleData> mockData;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        mockData=parser.parse(PacketData.class,Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(mockProp.getSample())));
        mockData.setContentBean(parser.parse(VehicleData.class,Unpooled.wrappedBuffer(mockData.getContent())));
    }

    @Override
    public MockDataBlock generate(MockClientBlock clientBlock, Date currentDate,Date exceptDate) {
        ByteBuf[] data = Arrays.stream(clientBlock.getClients()).map(e -> {
            return generate(e.getId(),exceptDate);
        }).toArray(len -> new ByteBuf[len]);
        return new MockDataBlock(clientBlock.getIndex(),data);
    }

    public ByteBuf generate(String id,Date exceptDate){
        PacketData<VehicleData> curPacketData=new PacketData<>();
        ByteBuf content= Unpooled.buffer();
        try {
            VehicleData curVehicleData=new VehicleData();
            BeanUtils.copyProperties(curVehicleData,mockData.getContentBean());
            curVehicleData.setTime(exceptDate);
            parser.deParse(curVehicleData,content);
            BeanUtils.copyProperties(curPacketData,mockData);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw BaseRuntimeException.getException(e);
        }
        curPacketData.setContent(content.array());
        curPacketData.setId(id);
        ByteBuf res= Unpooled.buffer();
        parser.deParse(curPacketData,res);
        return res;
    }
}
