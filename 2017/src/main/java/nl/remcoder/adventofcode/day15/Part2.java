package nl.remcoder.adventofcode.day15;

public class Part2 {
    public static void main(String[] args) {
        Generator generatorA = new Generator();
        generatorA.previousvalue = 618;
//        generatorA.previousvalue = 65;
        generatorA.criteria = 4;
        generatorA.factor = 16807;
        Generator generatorB = new Generator();
        generatorB.previousvalue = 814;
//        generatorB.previousvalue = 8921;
        generatorB.factor = 48271;
        generatorB.criteria = 8;

        int matches = 0;

        for (int i = 0; i < 5000000; i++) {
            long valueA = generatorA.generateValue();
            long valueB = generatorB.generateValue();

            String bitsA = Long.toBinaryString(valueA);
            String bitsB = Long.toBinaryString(valueB);

            String significantBitsA = bitsA.length() > 16 ? bitsA.substring(bitsA.length() - 16) : bitsA;
            String significantBitsB = bitsB.length() > 16 ? bitsB.substring(bitsB.length() - 16) : bitsB;

            if(significantBitsA.equals(significantBitsB)) {
                matches++;
            }
        }

        System.out.println(matches);
    }

    private static class Generator {
        long previousvalue;
        long factor;
        long criteria;

        long generateValue() {
            long value;

            while((value = (previousvalue * factor) % 2147483647) % criteria != 0){
                previousvalue = value;
            }

            previousvalue = value;

            return value;
        }
    }
}
