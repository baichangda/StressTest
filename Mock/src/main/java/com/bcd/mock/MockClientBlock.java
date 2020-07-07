package com.bcd.mock;

public class MockClientBlock {
    //start from 0
    int index;

    MockClient[] clients;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public MockClient[] getClients() {
        return clients;
    }

    public void setClients(MockClient[] clients) {
        this.clients = clients;
    }
}
