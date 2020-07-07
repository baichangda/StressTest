package com.bcd.ickey.mock;

import com.bcd.base.exception.BaseRuntimeException;
import com.bcd.mock.MockIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MockIdGeneratorImpl implements MockIdGenerator {

    @Autowired
    MockProp mockProp;

    @Override
    public String[] generate() {
        String pre=mockProp.getIdPre();
        int len=mockProp.getIdLen();
        int num=mockProp.getNum();
        if(pre.length()>=len){
            throw BaseRuntimeException.getException("prop[pre.length():"+pre.length()+"] must less than prop[idLen:"+len+"]");
        }
        double max=Math.pow(10,len-pre.length());
        if(num> max){
            throw BaseRuntimeException.getException("config support max["+max+"],but ["+num+"]");
        }
        List<String> idList=new ArrayList<>();
        for(int i=0;i<num;i++){
            StringBuilder sb=new StringBuilder(pre);
            int n1=(i+"").length();
            int n2=len-pre.length()-n1;
            for (int j = 0; j < n2; j++) {
                sb.append("0");
            }
            sb.append(i);
            idList.add(sb.toString());
        }
        return idList.toArray(new String[0]);
    }
}
