package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Day20 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) {
        var numbers = input.map(Integer::parseInt)
                           .map(Number::new)
                           .toList();

        var decrypted = new ArrayList<>(numbers);
        
        mixNumbers(numbers, decrypted);

        return getGroveNumber(decrypted);
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var numbers = input.map(Long::parseLong)
                           .map(number -> number * 811589153)
                           .map(Number::new)
                           .toList();

        var decrypted = new ArrayList<>(numbers);
        
        for (int i = 0; i < 10; i++) {
            mixNumbers(numbers, decrypted);
        }

        return getGroveNumber(decrypted);
    }

    private long getGroveNumber(List<Number> decrypted) {
        var zeroNumber = decrypted.stream()
                                  .filter(number -> number.number() == 0)
                                  .findFirst()
                                  .orElseThrow(() -> new AssertionError("Eek!"));

        var zeroIndex = decrypted.indexOf(zeroNumber);

        var firstGroveIndex = (zeroIndex + 1000) % decrypted.size();
        var secondGroveIndex = (zeroIndex + 2000) % decrypted.size();
        var thirdGroveIndex = (zeroIndex + 3000) % decrypted.size();

        return decrypted.get(firstGroveIndex).number() +
               decrypted.get(secondGroveIndex).number() +
               decrypted.get(thirdGroveIndex).number();
    }

    private void mixNumbers(List<Number> numbers, List<Number> numbersToMix) {
        for (var number : numbers) {
            int index = numbersToMix.indexOf(number);
            numbersToMix.remove(index);
            var newindex = Math.floorMod(index + number.number(), numbersToMix.size());
            numbersToMix.add(newindex, number);
        }
    }

    private static class Number {
        private final long number;

        private Number(long number) {
            this.number = number;
        }

        public long number() {
            return number;
        }

        @Override
        public String toString() {
            return "" + number;
        }
    }
}
