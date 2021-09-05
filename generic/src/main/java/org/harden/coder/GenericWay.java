package org.harden.coder;

import java.util.function.Supplier;

public class GenericWay {
    static class Pair<T,R>{
        private T t;
        private R r;

        public Pair(T t, R r){
            this.t=t;
            this.r=r;
        }
    }

    public static Pair<String,String> createPair(Supplier<String> factory){
        return new Pair<>(factory.get(),factory.get());
    }

}
