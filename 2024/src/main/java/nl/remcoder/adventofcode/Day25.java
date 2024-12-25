package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.BiAdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Pair;

import java.util.ArrayList;
import java.util.stream.Stream;

public class Day25 implements BiAdventOfCodeSolution<Integer, String> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var lines = input.toList();

        var keys = new ArrayList<Key>();
        var locks = new ArrayList<Lock>();

        for (var i = 0; i < lines.size(); i += 8) {
            if (lines.get(i).startsWith("#")) {
                var pinHeights = new int[5];
                for (var j = 0; j < 5; j++) {
                    var height = 0;
                    while (lines.get(i + 1 + height).charAt(j) == '#') {
                        height++;
                    }
                    pinHeights[j] = height;
                }
                locks.add(new Lock(pinHeights[0], pinHeights[1], pinHeights[2], pinHeights[3], pinHeights[4]));
            } else {
                var pinHeights = new int[5];
                for (var j = 0; j < 5; j++) {
                    var height = 0;
                    while (lines.get(i + 5 - height).charAt(j) == '#') {
                        height++;
                    }
                    pinHeights[j] = height;
                }
                keys.add(new Key(pinHeights[0], pinHeights[1], pinHeights[2], pinHeights[3], pinHeights[4]));
            }
        }

        return (int) locks.stream()
                          .flatMap(lock -> keys.stream()
                                               .filter(key -> fits(lock, key))
                                               .map(key -> new Pair<>(key, lock)))
                          .count();
    }

    @Override
    public String handlePart2(Stream<String> input) {
        return "Merry Christmas!";
    }

    private boolean fits(Lock lock, Key key) {
        return lock.pin1 + key.pin1 <= 5 &&
               lock.pin2 + key.pin2 <= 5 &&
               lock.pin3 + key.pin3 <= 5 &&
               lock.pin4 + key.pin4 <= 5 &&
               lock.pin5 + key.pin5 <= 5;
    }

    private record Key(int pin1, int pin2, int pin3, int pin4, int pin5) {
        @Override
        public String toString() {
            return pin1 +
                   "," + pin2 +
                   "," + pin3 +
                   "," + pin4 +
                   "," + pin5;
        }
    }

    private record Lock(int pin1, int pin2, int pin3, int pin4, int pin5) {
        @Override
        public String toString() {
            return pin1 +
                   "," + pin2 +
                   "," + pin3 +
                   "," + pin4 +
                   "," + pin5;
        }
    }
}
