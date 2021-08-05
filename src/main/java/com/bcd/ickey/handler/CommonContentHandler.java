package com.bcd.ickey.handler;

import com.bcd.base.util.DateZoneUtil;
import com.bcd.ickey.Parser_ICKey;
import com.bcd.ickey.data.PacketData;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
public class CommonContentHandler extends ContentHandler {

    @Autowired
    Parser_ICKey context;

    public CommonContentHandler() {
        super(0);
    }

    @Override
    public void handle(PacketData data, ChannelHandlerContext ctx) {
        //构造通用响应
        ByteBuf byteBuf= Unpooled.buffer(7,7);
        LocalDateTime ldt= LocalDateTime.ofInstant(new Date().toInstant(),DateZoneUtil.ZONE_OFFSET);
        byteBuf.writeByte(ldt.getYear()/100);
        byteBuf.writeByte(ldt.getYear()%100);
        byteBuf.writeByte(ldt.getMonthValue());
        byteBuf.writeByte(ldt.getDayOfMonth());
        byteBuf.writeByte(ldt.getHour());
        byteBuf.writeByte(ldt.getMinute());
        byteBuf.writeByte(ldt.getSecond());
        byte[] dateBytes= ByteBufUtil.getBytes(byteBuf);

        byte[] content=new byte[13];
        content[0]=(byte)data.getFlag();
        content[1]=0;
        content[2]=0;
        content[3]=0;
        content[4]=0;
        content[5]=0;
        System.arraycopy(dateBytes,0,content,6,7);

        PacketData replyData=new PacketData(data.getId(),
                data.getCmdId(),
                content,
                data.getCheck());

        ctx.writeAndFlush(context.toByteBuf(replyData));
    }
}
