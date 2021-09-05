package org.harden.coder.collection;

import java.util.Arrays;

/**
 * 在使用数组拷贝时,应当注意它是一种浅拷贝
 */
public class ArrayCopyDemo {
    public static void main(String[] args) {
        shallowCopy();
    }
    //因为Integer的基础类为常量类型
    private static void shallowCopySpecification() {
        Integer[] ints=of(1,2,3,4);
        Integer[] copyOf = Arrays.copyOf(ints, ints.length);
        copyOf[0]=99;
        display(ints,copyOf);
    }

    private static void shallowCopy() {
        User[] users=ofUser(1,2,3,4);
        User[] copyOf = Arrays.copyOf(users, users.length);
        copyOf[0].id=12;
        display(users,copyOf);
    }

    private static void display(Integer[] target,Integer[] copyOf ){
        for(int i=0;i<target.length;i++){
            System.out.printf("target is %s copyOf is %s \n",target[i],copyOf[i]);
        }
    }

    private static void display(User[] target,User[] copyOf ){
        for(int i=0;i<target.length;i++){
            System.out.printf("target is %s copyOf is %s \n",target[i],copyOf[i]);
        }
    }

    private static Integer[] of(Integer... integers){
        return integers;
    }

    private static User[] ofUser(Integer... integers){
        User[] users= new User[integers.length];
        for(int i =0;i<integers.length;i++){
            users[i]=new User(i);
        }
        return users;
    }

    static class User{
        private Integer id;

        public User(Integer id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    '}';
        }
    }
}
