package com.bcd.parser.process.impl;

import com.bcd.parser.process.FieldDeProcessContext;
import com.bcd.parser.process.FieldProcessContext;
import com.bcd.parser.process.FieldProcessor;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Objects;

public class IntegerProcessor extends FieldProcessor {
    public final static int BYTE_LENGTH=4;
    @Override
    public boolean support(Class clazz) {
        return clazz==int.class||clazz==Integer.class;
    }

    @Override
    public Object process(ByteBuf data, FieldProcessContext processContext){
        int len=processContext.getLen();
        if(len==2){
            //优化处理 short->int
            return data.readUnsignedShort();
        }else {
            if (len == BYTE_LENGTH) {
                return data.readInt();
            } else if (len > BYTE_LENGTH) {
                data.skipBytes(len - BYTE_LENGTH);
                return data.readInt();
            } else {
                ByteBuf temp= Unpooled.buffer(BYTE_LENGTH,BYTE_LENGTH);
                temp.writeBytes(new byte[BYTE_LENGTH-len]);
                temp.writeBytes(data,len);
                return temp.readInt();
            }
        }
    }

    @Override
    public void deProcess(Object data, ByteBuf dest, FieldDeProcessContext processContext) {
        Objects.requireNonNull(data);
        int len=processContext.getLen();
        if(len==BYTE_LENGTH){
            dest.writeInt((int)data);
        }else if(len>BYTE_LENGTH){
            dest.writeBytes(new byte[len-BYTE_LENGTH]);
            dest.writeInt((int)data);
        }else{
            for(int i=len;i>=1;i--){
                int move=8*(i-1);
                dest.writeByte((byte)((int)data>>>move));
            }
        }
    }
}
