package org.harden.coder.collection;

public class IdentityHashDemo {
    public static void main(String[] args) {
        int one =199;
        Integer two=199;
//        hashVsIdentityHash(one,two);

        Foo fooOne=new Foo(2);
        Foo fooTwo=new Foo(2);
        hashVsIdentityHash(fooOne,fooTwo);
    }

    private static void hashVsIdentityHash(Object one,Object two){
        System.out.printf("one hash is %s one identityHash is %s \n",one.hashCode(),System.identityHashCode(one));
        System.out.printf("two hash is %s two identityHash is %s \n",two.hashCode(),System.identityHashCode(two));
//        System.out.println(one.equals(two));
    }

    static class Foo{
        private Integer integer;

        public Foo(Integer integer) {
            this.integer = integer;
        }
    }
}
