package nl.remcoder.adventofcode.library.math;

import java.math.BigInteger;

public class Math {
    public static long lcm(long number1, long number2) {
        BigInteger bigInteger1 = BigInteger.valueOf(number1);
        BigInteger bigInteger2 = BigInteger.valueOf(number2);

        BigInteger gcd = bigInteger1.gcd(bigInteger2);
        BigInteger absProduct = bigInteger1.multiply(bigInteger2).abs();
        return absProduct.divide(gcd).longValue();
    }
}
