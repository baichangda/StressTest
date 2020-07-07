package com.bcd.mock;


public class MockDataSender {
    public void send(SendData data){
        MockClient[] clients= data.getMockClientBlock().getClients();
        for (int i=0;i<clients.length;i++) {
            clients[i].getContext().writeAndFlush(data.getMockDataBlock().getData()[i]);
        }
    }
}
