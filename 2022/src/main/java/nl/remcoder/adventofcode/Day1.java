package nl.remcoder.adventofcode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class Day1 {
    public int handlePart1(Stream<String> input) {
        List<String> data = input.toList();

        List<List<Integer>> bags = new ArrayList<>();
        List<Integer> currentBag = new ArrayList<>();
        bags.add(currentBag);

        for (String line : data) {
            if (line.isBlank()) {
                currentBag = new ArrayList<>();
                bags.add(currentBag);
            } else {
                currentBag.add(Integer.parseInt(line));
            }
        }

        return bags.stream()
                   .mapToInt(bag -> bag.stream()
                                       .mapToInt(Integer::intValue)
                                       .sum())
                   .max()
                   .orElseThrow(() -> new AssertionError("Eek!"));
    }

    public int handlePart2(Stream<String> input) {
        List<String> data = input.toList();

        List<List<Integer>> bags = new ArrayList<>();
        List<Integer> currentBag = new ArrayList<>();
        bags.add(currentBag);

        for (String line : data) {
            if (line.isBlank()) {
                currentBag = new ArrayList<>();
                bags.add(currentBag);
            } else {
                currentBag.add(Integer.parseInt(line));
            }
        }

        return bags.stream()
                   .map(bag -> bag.stream()
                                  .mapToInt(Integer::intValue)
                                  .sum())
                   .sorted(Comparator.reverseOrder())
                   .limit(3)
                   .mapToInt(Integer::intValue)
                   .sum();
    }
}