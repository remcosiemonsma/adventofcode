package nl.remcoder.adventofcode.day23;

public class Part2 {
    public static void main(String[] args) throws Exception {
        int h = 0;

        for (int i = 109300; i <= 126300; i += 17) {
            if (isComposite(i)) {
                h++;
            }
        }
        System.out.println(h);
    }

    private static boolean isComposite(int i) {
        for (int j = 2; j < i; j++) {
            if (i % j == 0) {
                return true;
            }
        }
        return false;
    }
}
