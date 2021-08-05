package com.bcd.mock;



/**
 * 模拟数据客户端提供接口
 */
public class MockClientBlockSupplier{
    int period;

    MockClient [] clients;

    public MockClientBlockSupplier(int period, MockClient[] clients) {
        this.period = period;
        this.clients = clients;
    }

    /**
     * 将客户端按照 period 均匀分割、尽量保证每秒发送的数据是一样的
     * @return
     */
    public MockClientBlock [] get(){
        MockClientBlock[] blocks=new MockClientBlock[period];
        if(clients.length<=period){
            for (int i = 0; i < clients.length; i++) {
                blocks[i]=new MockClientBlock();
                blocks[i].setIndex(i);
                blocks[i].setClients(new MockClient[]{clients[i]});
            }
        }else{
            int n1=clients.length/period;
            int n2=clients.length%period;
            int index=0;
            for (int i = 0; i < period; i++) {
                MockClient[] cur;
                if(i<n2){
                    cur=new MockClient[n1+1];
                }else{
                    cur=new MockClient[n1];
                }
                System.arraycopy(clients, index, cur, 0, cur.length);
                index+=cur.length;
                blocks[i]=new MockClientBlock();
                blocks[i].setIndex(i);
                blocks[i].setClients(cur);
            }
        }
        return blocks;
    }
}
