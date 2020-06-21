package tech.shunzi.model.utils;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.util.HashSet;
import java.util.Random;

public class RandomUtil {

    static Random generator = new Random();

    public static void main(String[] args) {
        for(int i = 0; i < 100; i++) {
            int number = generator.nextInt();
            System.out.println(number);
        }
    }
}
