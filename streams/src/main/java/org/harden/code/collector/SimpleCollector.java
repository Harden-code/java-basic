package org.harden.code.collector;

import org.harden.code.example.Dish;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static org.harden.code.example.Dish.getAll;

public class SimpleCollector {

    private static List<Dish> dishes = getAll();

    public static long getCount(){

        return dishes.stream().collect(counting());
    }

    public static void summing(){

        //平均值
        dishes.stream().collect(averagingInt(Dish::getCalories));
        //聚集求和 summing
        //平均值 总和 最大值 最小值 个数
        System.out.println(dishes.stream().collect(summarizingInt(Dish::getCalories)).toString());
    }
    //joining


    public static void reduce(){
        dishes.stream().collect(reducing(0,Dish::getCalories,(i,j)->i+j));
        dishes.stream().collect(reducing(0,Dish::getCalories,Integer::sum));
    }

    public static void main(String[] args) {
        summing();
    }
}
