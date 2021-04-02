package tech.shunzi.algorithm;

import sun.security.util.BitArray;

import java.util.BitSet;

public class BitMapUtil {

    public static void testBitArray() {
        BitArray bitArray = new BitArray(1024);
        for (int i = 0; i < 1024; i++) {
            if ((i & 1) == 0) {
                bitArray.set(i, true);
            }
            System.out.println(bitArray.get(i));
        }
        System.out.println(bitArray.get(12));
    }

    public static void testBitSet() {
        BitSet bits1 = new BitSet(16);
        BitSet bits2 = new BitSet(16);

        // set some bits
        for (int i = 0; i < 16; i++) {
            if ((i % 2) == 0) bits1.set(i);
            if ((i % 5) != 0) bits2.set(i);
        }
        System.out.println("Initial pattern in bits1: ");
        System.out.println(bits1);
        System.out.println("\nInitial pattern in bits2: ");
        System.out.println(bits2);

        // AND bits
        bits2.and(bits1);
        System.out.println("\nbits2 AND bits1: ");
        System.out.println(bits2);

        // OR bits
        bits2.or(bits1);
        System.out.println("\nbits2 OR bits1: ");
        System.out.println(bits2);

        // XOR bits
        bits2.xor(bits1);
        System.out.println("\nbits2 XOR bits1: ");
        System.out.println(bits2);
    }

    public static void main(String[] args) {
        testBitSet();
    }
}
