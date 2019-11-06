package nl.remcoder.adventofcode.day19;

public class Part2 {
       public static void main(String[] args) {
        int number = 10551296;

        int result = 0;

        for (int i = 1; i <= number; i++) {
            if (number % i == 0) {
                result += i;
            }
        }

        System.out.println(result);
    }
}
