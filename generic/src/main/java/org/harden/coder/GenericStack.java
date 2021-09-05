package org.harden.coder;

public class GenericStack<T> {
//    private T[] =new T[];  error
    //  1
//    private final T[] elements;
    //2

    private final Object[] elements;

    private int index;

    private int deleteIndex;
    public GenericStack(int size) {
        //  1
//        Object[] objects = new Object[size];
//        this.elements = (T[])objects;

        //2
        elements=new Object[size];
    }

    public void push(T t){
        //1
//        elements[index++]=t;

        //2
        elements[index++]=t;
    }

    public T pop(){
//        return elements[deleteIndex++];
        return (T) elements[index++];
    }

    public static void main(String[] args) {
        GenericStack<Integer> stack=new GenericStack<>(10);
        stack.push(1);
        System.out.println(stack.pop());
    }
}

