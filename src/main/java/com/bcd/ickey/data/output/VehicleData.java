package com.bcd.ickey.data.output;

import com.bcd.ickey.Parser_ICKey;
import com.bcd.ickey.data.PacketData;
import com.bcd.parser.anno.PacketField;
import com.bcd.parser.anno.Parsable;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;

import java.util.Date;
@Parsable
public class VehicleData {
    //X1：1字节 【状态 0：空闲，1：租赁中，2：预约中，3：故障，4：亏电,5：运维】（已删）
//    @PacketField(index = 1,len = 1)
//    byte status;

    //X2：4字节 【会员号 空闲、故障、亏电为0xFFFFFFFF】
    @PacketField(index = 2,len = 4)
    int vipNo;

    //X3：4个字节 【配置项 含义见表1】
    @PacketField(index = 3,len = 4)
    int config;

    //X4：4字节 【车辆状态1，含义见表2】
    @PacketField(index = 4,len = 4)
    int status1;

    //X5：4字节 【车辆状态2，含义见表3】
    @PacketField(index = 5,len = 4)
    int status2;

    //X6：7字节 【时间戳】
    @PacketField(index = 6,len = 7)
    Date time;

    //X7：4字节 【GPS经度】
    @PacketField(index = 7,len = 4)
    int lng;

    //X8：4字节 【GPS纬度】
    @PacketField(index = 8,len = 4)
    int lat;

    //X9：2字节 【方向，0~359，单位为度(o)，正北为0，顺时针】
    @PacketField(index = 9,len = 2)
    short direction;

    //X10： 2字节 【海拔高度，单位为米（m）】
    @PacketField(index = 10,len = 2)
    short height;

    //X11：2字节 【GPS速度，单位为千米每小时（km/h）】
    @PacketField(index = 11,len = 2)
    short gpsSpeed;

    //【配置项获取SOC时】
    // X12：1字节 【SOC】
    @PacketField(index = 12,len = 1)
    byte soc;

    //【配置项获取行驶记录速度时】
    // X13：2字节  【行驶记录速度，指车辆行驶记录设备上传的行车速度信息，单位为十米每小时（10m/h）】
    @PacketField(index = 13,len = 2)
    short speed;

    //【配置项获取车辆行驶里程时】
    // X14：3字节  【车辆行驶里程  1bit=0.1KM】
    @PacketField(index = 14,len = 3)
    byte[] mileage;

    //【配置项获取蓄电池电压时】
    // X15：1字节  【蓄电池电压】
    @PacketField(index = 15,len = 1)
    byte storageVoltage;


    //【配置项获取动力电池电压时】
    // X16：2字节  【动力电池电压】
    @PacketField(index = 16,len = 2)
    short engineVoltage;

    //【配置项获取GNSS时】
    //X17：1字节  【GNSS卫星数量】
    @PacketField(index = 17,len = 1)
    byte gnssSatelliteNum;

    //X18：1字节  【GNSS通道数量】
    @PacketField(index = 18,len = 1)
    byte gnssChannelNum;

    //【配置项获取故障信息时】
    //X19：1字节  【故障信息长度】
    @PacketField(index = 19,len = 1,var = 'a')
    byte faultLen;

    //X20：X19字节  【故障信息，多个以逗号分隔】
    @PacketField(index = 20,lenExpr = "a")
    byte[] fault;

    //【配置项获取空调状态时】
    // X21：1字节  【空调状态】
    @PacketField(index = 21,len = 1)
    byte airConditioningStatus;

    //X22：1字节  【空调风扇状态】
    @PacketField(index = 22,len = 1)
    byte airConditioningFanStatus;

    //【配置项获取续航里程时】
    // X23：3字节  【续航里程  1bit=0.1KM】
    @PacketField(index = 23,len = 3)
    byte[] enduranceMileage;

    //【配置项获取剩余油量时】
    // X24：1字节  【单位：%】
    @PacketField(index = 24,len = 1)
    byte remainOil;

    //【配置项获取胎压时】
    // X25：2字节  【左前轮，单位：kPa】
    @PacketField(index = 25,len = 2)
    short leftFront;

    // X26：2字节  【右前轮，单位：kPa】
    @PacketField(index = 26,len = 2)
    short rightFront;

    // X27：2字节  【左后轮，单位：kPa】
    @PacketField(index = 27,len = 2)
    short leftBehind;

    // X28：2字节  【右后轮，单位：kPa】
    @PacketField(index = 28,len = 2)
    short rightBehind;

    //【配置项获取车内温度时】
    // X29：2字节  【单位：0.1℃，偏移量：-40℃】
    @PacketField(index = 29,len = 2)
    short temperature;

    //【瞬时油耗】
    // X30：2字节  【单位：0.1L/100KM】
    @PacketField(index = 30,len = 2)
    short currentOilCost;

    //【平均油耗】
    // X31：2字节  【单位：0.1L/100KM】
    @PacketField(index = 31,len = 2)
    short avgOilCost;

    public int getVipNo() {
        return vipNo;
    }

    public void setVipNo(int vipNo) {
        this.vipNo = vipNo;
    }

    public int getConfig() {
        return config;
    }

    public void setConfig(int config) {
        this.config = config;
    }

    public int getStatus1() {
        return status1;
    }

    public void setStatus1(int status1) {
        this.status1 = status1;
    }

    public int getStatus2() {
        return status2;
    }

    public void setStatus2(int status2) {
        this.status2 = status2;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getLng() {
        return lng;
    }

    public void setLng(int lng) {
        this.lng = lng;
    }

    public int getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    public short getDirection() {
        return direction;
    }

    public void setDirection(short direction) {
        this.direction = direction;
    }

    public short getHeight() {
        return height;
    }

    public void setHeight(short height) {
        this.height = height;
    }

    public short getGpsSpeed() {
        return gpsSpeed;
    }

    public void setGpsSpeed(short gpsSpeed) {
        this.gpsSpeed = gpsSpeed;
    }

    public byte getSoc() {
        return soc;
    }

    public void setSoc(byte soc) {
        this.soc = soc;
    }

    public short getSpeed() {
        return speed;
    }

    public void setSpeed(short speed) {
        this.speed = speed;
    }

    public byte[] getMileage() {
        return mileage;
    }

    public void setMileage(byte[] mileage) {
        this.mileage = mileage;
    }

    public byte getStorageVoltage() {
        return storageVoltage;
    }

    public void setStorageVoltage(byte storageVoltage) {
        this.storageVoltage = storageVoltage;
    }

    public short getEngineVoltage() {
        return engineVoltage;
    }

    public void setEngineVoltage(short engineVoltage) {
        this.engineVoltage = engineVoltage;
    }

    public byte getGnssSatelliteNum() {
        return gnssSatelliteNum;
    }

    public void setGnssSatelliteNum(byte gnssSatelliteNum) {
        this.gnssSatelliteNum = gnssSatelliteNum;
    }

    public byte getGnssChannelNum() {
        return gnssChannelNum;
    }

    public void setGnssChannelNum(byte gnssChannelNum) {
        this.gnssChannelNum = gnssChannelNum;
    }

    public byte getFaultLen() {
        return faultLen;
    }

    public void setFaultLen(byte faultLen) {
        this.faultLen = faultLen;
    }

    public byte[] getFault() {
        return fault;
    }

    public void setFault(byte[] fault) {
        this.fault = fault;
    }

    public byte getAirConditioningStatus() {
        return airConditioningStatus;
    }

    public void setAirConditioningStatus(byte airConditioningStatus) {
        this.airConditioningStatus = airConditioningStatus;
    }

    public byte getAirConditioningFanStatus() {
        return airConditioningFanStatus;
    }

    public void setAirConditioningFanStatus(byte airConditioningFanStatus) {
        this.airConditioningFanStatus = airConditioningFanStatus;
    }

    public byte[] getEnduranceMileage() {
        return enduranceMileage;
    }

    public void setEnduranceMileage(byte[] enduranceMileage) {
        this.enduranceMileage = enduranceMileage;
    }

    public byte getRemainOil() {
        return remainOil;
    }

    public void setRemainOil(byte remainOil) {
        this.remainOil = remainOil;
    }

    public short getLeftFront() {
        return leftFront;
    }

    public void setLeftFront(short leftFront) {
        this.leftFront = leftFront;
    }

    public short getRightFront() {
        return rightFront;
    }

    public void setRightFront(short rightFront) {
        this.rightFront = rightFront;
    }

    public short getLeftBehind() {
        return leftBehind;
    }

    public void setLeftBehind(short leftBehind) {
        this.leftBehind = leftBehind;
    }

    public short getRightBehind() {
        return rightBehind;
    }

    public void setRightBehind(short rightBehind) {
        this.rightBehind = rightBehind;
    }

    public short getTemperature() {
        return temperature;
    }

    public void setTemperature(short temperature) {
        this.temperature = temperature;
    }

    public short getCurrentOilCost() {
        return currentOilCost;
    }

    public void setCurrentOilCost(short currentOilCost) {
        this.currentOilCost = currentOilCost;
    }

    public short getAvgOilCost() {
        return avgOilCost;
    }

    public void setAvgOilCost(short avgOilCost) {
        this.avgOilCost = avgOilCost;
    }

}























