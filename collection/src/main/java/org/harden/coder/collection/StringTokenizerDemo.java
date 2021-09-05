package org.harden.coder.collection;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.StringTokenizer;

public class StringTokenizerDemo {
    public static void main(String[] args) {
        String str="1,2,3";

        String[] split = str.split(",");

        StringTokenizer token=new StringTokenizer(str,",");
        token.countTokens();

    }
}
