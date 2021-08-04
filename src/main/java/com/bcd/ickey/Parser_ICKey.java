package com.bcd.ickey;

import com.bcd.base.exception.BaseRuntimeException;
import com.bcd.base.util.DateZoneUtil;
import com.bcd.parser.Parser;
import com.bcd.parser.process.FieldDeProcessContext;
import com.bcd.parser.process.FieldProcessContext;
import com.bcd.parser.process.FieldProcessor;
import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Component
public class Parser_ICKey extends Parser {

    public Parser_ICKey() {
        this.dateProcessor=new DateFieldProcessor();
        init();
    }

    @Override
    protected void initPacketInfo() {
        initPacketInfoByScanClass("com.bcd");
    }
}

class DateFieldProcessor extends FieldProcessor<Date> {

    @Override
    public boolean support(Class clazz) {
        return Date.class==clazz;
    }

    @Override
    public Date process(ByteBuf data, FieldProcessContext processContext) {
        int len=processContext.getLen();
        if(len==7){
            int year=data.readByte()*100+data.readByte();
            int month=data.readByte();
            int day=data.readByte();
            int hour=data.readByte();
            int minute=data.readByte();
            int second=data.readByte();
            return Date.from(LocalDateTime.of(year,month,day,hour,minute,second).toInstant(DateZoneUtil.ZONE_OFFSET));
        }else{
            throw BaseRuntimeException.getException("date length must be 7,actual "+len);
        }
    }

    @Override
    public void deProcess(Date data, ByteBuf dest, FieldDeProcessContext processContext) {
        Objects.requireNonNull(data);
        int len=processContext.getLen();
        if(len==7){
            LocalDateTime ldt= LocalDateTime.ofInstant(data.toInstant(),DateZoneUtil.ZONE_ID);
            dest.writeByte(ldt.getYear()/100);
            dest.writeByte(ldt.getYear()%100);
            dest.writeByte(ldt.getMonthValue());
            dest.writeByte(ldt.getDayOfMonth());
            dest.writeByte(ldt.getHour());
            dest.writeByte(ldt.getMinute());
            dest.writeByte(ldt.getSecond());
        }else{
            throw BaseRuntimeException.getException("date length must be 7,actual "+len);
        }
    }
}
