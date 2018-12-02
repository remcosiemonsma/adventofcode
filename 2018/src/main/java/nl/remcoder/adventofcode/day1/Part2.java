package nl.remcoder.adventofcode.day1;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Part2 {
    public static void main(String[] args) throws Exception {
        List<Integer> frequencyShifts = Files.lines(Paths.get(ClassLoader.getSystemResource("day1/input").toURI()))
                                     .map(Integer::parseInt)
                                     .collect(Collectors.toList());

        int frequency = 0;

        Set<Integer> frequencies = new HashSet<>();

        int frequencyCounter = 0;

        while (frequencies.add(frequency)) {
            frequency += frequencyShifts.get(frequencyCounter);

            frequencyCounter++;
            if (frequencyCounter >= frequencyShifts.size()) {
                frequencyCounter = 0;
            }
        }

        System.out.println(frequency);
    }
}
