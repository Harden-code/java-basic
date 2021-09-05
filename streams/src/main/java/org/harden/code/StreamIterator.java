package org.harden.code;

import java.util.Arrays;
import java.util.stream.Stream;

public class StreamIterator {
    public static void main(String[] args) {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4);
        stream.forEach(System.out::println);
        stream.forEach(System.out::println);
    }
}
