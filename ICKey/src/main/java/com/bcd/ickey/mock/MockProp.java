package com.bcd.ickey.mock;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ic-key")
public class MockProp {
    private String host;
    private int port;
    private int period;
    private String sample;
    private String idPre;
    private int idLen;
    private int num;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }

    public String getIdPre() {
        return idPre;
    }

    public void setIdPre(String idPre) {
        this.idPre = idPre;
    }

    public int getIdLen() {
        return idLen;
    }

    public void setIdLen(int idLen) {
        this.idLen = idLen;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }


}
