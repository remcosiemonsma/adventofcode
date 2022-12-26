package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.LinkedList;
import java.util.stream.Stream;

public class Day19 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var totalElves = input.mapToInt(Integer::parseInt)
                              .findFirst()
                              .orElseThrow(() -> new AssertionError("Eek!"));

        var elves = new LinkedList<Integer>();

        for (int i = 1; i < totalElves + 1; i++) {
            elves.add(i);
        }

        boolean take = false;
        while (elves.size() > 1) {
            var iterator = elves.iterator();

            while (iterator.hasNext()) {
                iterator.next();
                if (take) {
                    iterator.remove();
                }
                take = !take;
            }
        }

        return elves.getFirst();
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var totalElves = input.mapToInt(Integer::parseInt)
                              .findFirst()
                              .orElseThrow(() -> new AssertionError("Eek!"));


        LinkedList<Integer> firstElves = new LinkedList<>();
        LinkedList<Integer> lastElves = new LinkedList<>();
        for (int i = 1; i <= totalElves; i++) {
            if (i <= totalElves / 2) {
                firstElves.addLast(i);
            } else {
                lastElves.addLast(i);
            }
        }

        while (firstElves.size() + lastElves.size() != 1) {
            int currentElf = firstElves.pollFirst();
            if (firstElves.size() == lastElves.size()) {
                firstElves.pollLast();
            } else {
                lastElves.pollFirst();
            }
            lastElves.addLast(currentElf);
            int elfToMove = lastElves.pollFirst();
            firstElves.addLast(elfToMove);
        }

        return firstElves.getFirst();
    }
}
