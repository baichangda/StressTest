package com.bcd.ickey;

import com.bcd.base.exception.BaseRuntimeException;
import com.bcd.base.util.DateZoneUtil;
import com.bcd.ickey.data.PacketData;
import com.bcd.parser.Parser;
import com.bcd.parser.processer.FieldDeProcessContext;
import com.bcd.parser.processer.FieldProcessContext;
import com.bcd.parser.processer.FieldProcessor;
import com.bcd.parser.util.PerformanceUtil;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
public class Parser_ICKey extends Parser {

    @Override
    protected List<Class> getParsableClass() {
        return getParsableClassByScanPackage("com.bcd.ickey");
    }

    public Parser_ICKey() {
        super(false);
        setDateProcessor(new DateFieldProcessor());
        init();
    }

    public static void main(String[] args) {
        Parser parser= new Parser_ICKey();
        parser.init();

        String data="292932005B0A594B31323031343135370000023900000000FFFFFFFF010AAAA900004155141407060A0D27073E339002527EA4010A00320001000000010D6A0C00000A0B00030200092E3D011301090109010902D503E800910000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000055AA806B899F0D";
        int threadNum=1;
        if(args.length>=1){
            threadNum=Integer.parseInt(args[0]);
        }
        parser.logger.info("param threadNum[{}]",threadNum);
        int num=100000000;
        PerformanceUtil.testMultiThreadPerformance(data,parser, PacketData.class,threadNum,num,true);
    }
}

class DateFieldProcessor extends FieldProcessor<Date> {
    @Override
    public Date process(ByteBuf data, FieldProcessContext processContext) {
        int len = processContext.len;
        if (len == 7) {
            int year = data.readByte() * 100 + data.readByte();
            int month = data.readByte();
            int day = data.readByte();
            int hour = data.readByte();
            int minute = data.readByte();
            int second = data.readByte();
            return Date.from(LocalDateTime.of(year, month, day, hour, minute, second).toInstant(DateZoneUtil.ZONE_OFFSET));
        } else {
            throw BaseRuntimeException.getException("date length must be 7,actual " + len);
        }
    }

    @Override
    public void deProcess(Date data, ByteBuf dest, FieldDeProcessContext processContext) {
        Objects.requireNonNull(data);
        int len = processContext.len;
        if (len == 7) {
            LocalDateTime ldt = LocalDateTime.ofInstant(data.toInstant(), DateZoneUtil.ZONE_OFFSET);
            dest.writeByte(ldt.getYear() / 100);
            dest.writeByte(ldt.getYear() % 100);
            dest.writeByte(ldt.getMonthValue());
            dest.writeByte(ldt.getDayOfMonth());
            dest.writeByte(ldt.getHour());
            dest.writeByte(ldt.getMinute());
            dest.writeByte(ldt.getSecond());
        } else {
            throw BaseRuntimeException.getException("date length must be 7,actual " + len);
        }
    }

}
