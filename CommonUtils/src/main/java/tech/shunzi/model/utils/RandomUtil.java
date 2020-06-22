package tech.shunzi.model.utils;

import org.apache.commons.collections4.ListUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author Elvis Zhang
 */
public class RandomUtil {

    /**
     * java.util.Random
     * 默认以当前系统给时间的毫秒为 seed
     *     this(seedUniquifier() ^ System.nanoTime());
     */
    static Random defaultGenerator = new Random();

    /**
     * 使用相同 seed 生成的随机数序列一样
     * Seed 无实际含义，本身只作为随机算法中的起始值，后续随机数的生成依赖前面的随机数
     *      do {
     *             oldseed = seed.get();
     *             nextseed = (oldseed * multiplier + addend) & mask;
     *         } while (!seed.compareAndSet(oldseed, nextseed));
     */
    static Random otherSeedGeneratorOne = new Random(10);
    static Random otherSeedGeneratorTwo = new Random(10);


    public static void testSimpleRandom(int randomCount) {
        for(int i = 0; i < randomCount; i++) {
            int number = defaultGenerator.nextInt();
            System.out.println(number);
        }
    }

    public static void testRandomWithRange(int randomCount, int rangeMaxValue) {
        for (int i = 0; i < randomCount; i++) {
            int number = defaultGenerator.nextInt(rangeMaxValue);
            System.out.println(number);
        }
    }

    public static void testOtherSeedRandom(int randomCount) {
        for (int i = 0; i < randomCount; i++) {
            int numberOne = otherSeedGeneratorOne.nextInt();
            int numberTwo = otherSeedGeneratorTwo.nextInt();
            System.out.println(numberOne == numberTwo);
        }
    }

    public static void testDifferentTypeRandom(int randomCount) {
        // Random int with bound
        for (int i = 0; i < randomCount; i++) {
            System.out.print(defaultGenerator.nextInt(10) + " ");
        }
        System.out.println();
        for (int i = 0; i < randomCount; i++) {
            System.out.print(defaultGenerator.nextBoolean() + " ");
        }
        System.out.println();

        // 0.0 -> 1.0 double
        for (int i = 0; i < randomCount; i++) {
            System.out.print(defaultGenerator.nextDouble() + " ");
        }
        System.out.println();

        // 0.0 -> 1.0 float
        for (int i = 0; i < randomCount; i++) {
            System.out.print(defaultGenerator.nextFloat() + " ");
        }
        System.out.println();

        for (int i = 0; i < randomCount; i++) {
            System.out.print(defaultGenerator.nextGaussian() + " ");
        }
        System.out.println();

        for (int i = 0; i < randomCount; i++) {
            System.out.print(defaultGenerator.nextLong() + " ");
        }
        System.out.println();

        byte[] bytes = new byte[100];
        defaultGenerator.nextBytes(bytes);
        System.out.println(new String(bytes));
    }

    public static List<Integer> randomDiffInt(int randomCount) {

        List<Integer> list = new ArrayList<Integer>();
        Random rand = new Random();
        boolean[] bool = new boolean[randomCount];
        int num = 0;
        for (int i = 0; i < randomCount; i++) {
            do {
                // 如果产生的数相同继续循环
                num = rand.nextInt(randomCount);
            } while (bool[num]);
            bool[num] = true;
            list.add(num);
        }
        System.out.println(list);
        System.out.println(Arrays.toString(bool));
        return list;
    }

    public static void main(String[] args) {
        testSimpleRandom(10);
        testRandomWithRange(10, 10);
        testOtherSeedRandom(10);
        testDifferentTypeRandom(10);
        randomDiffInt(100);
    }
}
