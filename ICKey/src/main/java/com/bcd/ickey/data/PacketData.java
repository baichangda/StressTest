package com.bcd.ickey.data;


import com.bcd.parser.anno.PacketField;
import com.bcd.parser.anno.Parsable;

@Parsable
public class PacketData<T> {
    //包头
    @PacketField(index = 1,len = 2)
    byte[] header;

    //主信令
    @PacketField(index = 2,len = 1)
    short flag;

    //包长
    @PacketField(index = 3,len = 2,var = 'a')
    int len;

    //标识长度
    @PacketField(index = 4,len = 1,var = 'b')
    short idLen;

    //标识
    @PacketField(index = 5,lenExpr ="b" )
    String id;

    //指令id
    @PacketField(index = 6,len = 4)
    long cmdId;

    //内容 (包长(len)-标识长度(idLen+1)-指令(4)-校验(6)-结束符(1))
    @PacketField(index = 7,lenExpr = "a-b-12")
    byte[] content;
    T contentBean;

    //校验
    @PacketField(index = 8,len = 6)
    byte[] check;

    //校验
    @PacketField(index = 9,len = 1)
    byte end;

    public PacketData() {
    }

    public PacketData(String id, long cmdId, byte[] content, byte[] check) {
        this.header=new byte[]{41,41};
        this.flag=0xEE;
        this.id=id;
        this.idLen=(short) id.getBytes().length;
        this.len=this.idLen+12+content.length;
        this.content= content;
        this.cmdId=cmdId;
        this.check= check;
        this.end=0X0D;
    }

    public byte[] getHeader() {
        return header;
    }

    public short getFlag() {
        return flag;
    }

    public int getLen() {
        return len;
    }

    public short getIdLen() {
        return idLen;
    }

    public String getId() {
        return id;
    }

    public long getCmdId() {
        return cmdId;
    }

    public byte[] getContent() {
        return content;
    }

    public byte[] getCheck() {
        return check;
    }

    public byte getEnd() {
        return end;
    }

    public T getContentBean() {
        return contentBean;
    }

    public void setContentBean(T contentBean) {
        this.contentBean = contentBean;
    }

    public void setHeader(byte[] header) {
        this.header = header;
    }

    public void setFlag(short flag) {
        this.flag = flag;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public void setIdLen(short idLen) {
        this.idLen = idLen;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCmdId(long cmdId) {
        this.cmdId = cmdId;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public void setCheck(byte[] check) {
        this.check = check;
    }

    public void setEnd(byte end) {
        this.end = end;
    }

}
