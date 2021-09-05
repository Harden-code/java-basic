package org.harden.coder;

import java.util.concurrent.ThreadLocalRandom;

public class VariableTypeAndGenericIsError {

    public static  <T>T[] toArray(T... ts){
        return ts;
    }

    public static  <T>T[] picTwo(T t1,T t2 ,T t3){
        int i = ThreadLocalRandom.current().nextInt(3);

        switch (i){
            //由于在运行时泛型会被擦写 picTwo的泛型T,在调用toArray方法,进行
            //泛型传导,但是这里缺少上下文信息所以T[]->Object[]
            case 1 : return toArray(t1,t2);
            case 2 : return toArray(t1,t2);
            case 3 : return toArray(t1,t2);
        }
        throw new RuntimeException();
    }

    public static void main(String[] args) {
        String[] strings=picTwo("0","1","2");

//        Integer[] integers = toArray(1, 2);
    }

}
