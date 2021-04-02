package tech.shunzi.algorithm;

public class BitUtil {
    // bit #1 001
    private final static int IS_LEAF_MASK = 0x01;
    // bit #2 010
    private final static int VARIABLE_SIZE_VALUES = 0x02;
    // bit #3 100
    private final static int HAS_OVERFLOW_PAGES = 0x04;

    public static void main(String[] args) {

        int[] flags = new int[20];

        System.out.println("IS_LEAF_MASK: " + IS_LEAF_MASK);
        System.out.println("VARIABLE_SIZE_VALUES: " + VARIABLE_SIZE_VALUES);
        System.out.println("HAS_OVERFLOW_PAGES: " + HAS_OVERFLOW_PAGES);
        System.out.println("1 << 2 : " + (1 << 2));

        // 000 -> 100
        flags[0] |= HAS_OVERFLOW_PAGES;
        System.out.println(flags[0]);
        flags[1] |= (1 << 2);
        System.out.println(flags[1]);

        // 100 -> 000
        flags[0] &= ~HAS_OVERFLOW_PAGES;
        System.out.println(flags[0]);
        flags[1] &= ~(1 << 2);
        System.out.println(flags[1]);

        boolean isSet = (flags[0] & HAS_OVERFLOW_PAGES) != 0;
        System.out.println("isSet: " + isSet);



    }
}
