package nl.remcoder.adventofcode.day12;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Part2 {
    public static void main(String[] args) throws Exception {
        String line = Files.lines(Paths.get(ClassLoader.getSystemResource("day12/input").toURI())).findFirst().get();

        String data = line.split(" ")[2];

        int arraySize = 8192;
        boolean[] plants = new boolean[arraySize];

        int centre = arraySize / 2;
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

        List<Integer> plantCount = new ArrayList<>();

        while (plantCount.size() < 1000) {
            boolean[] newplants = Arrays.copyOf(plants, plants.length);

            for (int plant = 2; plant < arraySize - 2; plant++) {
                List<Boolean> transform = Arrays.asList(plants[plant - 2], plants[plant - 1], plants[plant], plants[plant + 1], plants[plant + 2]);

                boolean result = transforms.getOrDefault(transform, false);

                newplants[plant] = result;
            }

            int totalPlants = sumPlantNumbers(plants, centre);

            plantCount.add(totalPlants);

            plants = newplants;
        }

        int growthRateAfter1000Iterations = plantCount.get(plantCount.size() - 1) - plantCount.get(plantCount.size() - 2);

        long growthTillEnd = growthRateAfter1000Iterations * (50000000000L - 999);

        System.out.println(growthTillEnd);

        long result = plantCount.get(plantCount.size() - 1) + growthTillEnd;

        System.out.println(result);
    }

    private static int sumPlantNumbers(boolean[] plants, int centre) {
        int totalPlants = 0;

        for (int i = 0; i < plants.length; i++) {
            if (plants[i]) {
                totalPlants += i - centre;
            }
        }
        return totalPlants;
    }
}
