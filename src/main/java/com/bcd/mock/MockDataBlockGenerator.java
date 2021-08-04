package com.bcd.mock;


import java.util.Date;

/**
 * 模拟数据客户端提供接口
 */
public interface MockDataBlockGenerator {
    MockDataBlock generate(MockClientBlock clientBlock, Date currentDate,Date exceptDate);
}
