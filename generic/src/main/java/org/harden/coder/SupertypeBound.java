package org.harden.coder;

import java.util.List;

public class SupertypeBound {
    private List<Emp> list;

    private Emp emp;

    static class Emp{

    }

    static class Manager extends Emp{

    }


    public void addEmp(List<? extends Emp> emps){
        list.addAll(emps);
    }

    public void print(){
    }

    public void copyList(List<? super Emp> copy){
        copy.addAll(list);
    }

}
