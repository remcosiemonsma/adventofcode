package nl.remcoder.adventofcode.day12;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Part1 {
    public static void main(String[] args) throws Exception {
        String line = Files.lines(Paths.get(ClassLoader.getSystemResource("day12/input").toURI())).findFirst().get();

        String data = line.split(" ")[2];

        boolean[] plants = new boolean[1024];

        int centre = 512;
        int index = centre;

        char[] chars = data.toCharArray();
        for (char value : chars) {
            plants[index++] = value == '#';
        }

        Map<List<Boolean>, Boolean> transforms = Files
                .lines(Paths.get(ClassLoader.getSystemResource("day12/input").toURI()))
                .skip(2)
                .collect(Collectors.toMap(string -> Arrays.asList(string.charAt(0) == '#', string.charAt(1) == '#',
                                                                  string.charAt(2) == '#', string.charAt(3) == '#',
                                                                  string.charAt(4) == '#'),
                                          string2 -> string2.charAt(9) == '#'));

        for (int i = 0; i < 20; i++) {
            boolean[] newplants = Arrays.copyOf(plants, plants.length);

            for (int plant = 2; plant < 1022; plant++) {
                List<Boolean> transform = Arrays.asList(plants[plant - 2], plants[plant - 1], plants[plant], plants[plant + 1], plants[plant + 2]);

                boolean result = transforms.getOrDefault(transform, false);

                newplants[plant] = result;
            }

            plants = newplants;
        }

        int totalPlants = 0;

        for (int i = 0; i < 1024; i++) {
            if (plants[i]) {
                totalPlants += i - centre;
            }
        }

        System.out.println(totalPlants);
    }
}
