package nl.remcoder.adventofcode;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day6 {
    public int handlePart1(Stream<String> input) {
        Map<String, List<String>> collect = input.map(s -> s.split(","))
                                                 .flatMap(Arrays::stream)
                                                 .collect(Collectors.groupingBy(s -> s, Collectors.toList()));

        int amountOfFishDay0 = collect.getOrDefault("0", List.of()).size();
        int amountOfFishDay1 = collect.getOrDefault("1", List.of()).size();
        int amountOfFishDay2 = collect.getOrDefault("2", List.of()).size();
        int amountOfFishDay3 = collect.getOrDefault("3", List.of()).size();
        int amountOfFishDay4 = collect.getOrDefault("4", List.of()).size();
        int amountOfFishDay5 = collect.getOrDefault("5", List.of()).size();
        int amountOfFishDay6 = collect.getOrDefault("6", List.of()).size();
        int amountOfFishDay7 = collect.getOrDefault("7", List.of()).size();
        int amountOfFishDay8 = collect.getOrDefault("8", List.of()).size();

        for (int day = 0; day < 80; day++) {
            int temp0 = amountOfFishDay0;
            int temp1 = amountOfFishDay1;
            int temp2 = amountOfFishDay2;
            int temp3 = amountOfFishDay3;
            int temp4 = amountOfFishDay4;
            int temp5 = amountOfFishDay5;
            int temp6 = amountOfFishDay6;
            int temp7 = amountOfFishDay7;

            amountOfFishDay7 = amountOfFishDay8;
            amountOfFishDay6 = temp7;
            amountOfFishDay5 = temp6;
            amountOfFishDay4 = temp5;
            amountOfFishDay3 = temp4;
            amountOfFishDay2 = temp3;
            amountOfFishDay1 = temp2;
            amountOfFishDay0 = temp1;
            amountOfFishDay6 += temp0;
            amountOfFishDay8 = temp0;
        }

        return amountOfFishDay0 + amountOfFishDay1 + amountOfFishDay2 + amountOfFishDay3 + amountOfFishDay4 +
               amountOfFishDay5 + amountOfFishDay6 + amountOfFishDay7 + amountOfFishDay8;
    }

    public long handlePart2(Stream<String> input) {
        Map<String, List<String>> collect = input.map(s -> s.split(","))
                                                 .flatMap(Arrays::stream)
                                                 .collect(Collectors.groupingBy(s -> s, Collectors.toList()));

        long amountOfFishDay0 = collect.getOrDefault("0", List.of()).size();
        long amountOfFishDay1 = collect.getOrDefault("1", List.of()).size();
        long amountOfFishDay2 = collect.getOrDefault("2", List.of()).size();
        long amountOfFishDay3 = collect.getOrDefault("3", List.of()).size();
        long amountOfFishDay4 = collect.getOrDefault("4", List.of()).size();
        long amountOfFishDay5 = collect.getOrDefault("5", List.of()).size();
        long amountOfFishDay6 = collect.getOrDefault("6", List.of()).size();
        long amountOfFishDay7 = collect.getOrDefault("7", List.of()).size();
        long amountOfFishDay8 = collect.getOrDefault("8", List.of()).size();

        for (int day = 0; day < 256; day++) {
            long temp0 = amountOfFishDay0;
            long temp1 = amountOfFishDay1;
            long temp2 = amountOfFishDay2;
            long temp3 = amountOfFishDay3;
            long temp4 = amountOfFishDay4;
            long temp5 = amountOfFishDay5;
            long temp6 = amountOfFishDay6;
            long temp7 = amountOfFishDay7;

            amountOfFishDay7 = amountOfFishDay8;
            amountOfFishDay6 = temp7;
            amountOfFishDay5 = temp6;
            amountOfFishDay4 = temp5;
            amountOfFishDay3 = temp4;
            amountOfFishDay2 = temp3;
            amountOfFishDay1 = temp2;
            amountOfFishDay0 = temp1;
            amountOfFishDay6 += temp0;
            amountOfFishDay8 = temp0;
        }

        return amountOfFishDay0 + amountOfFishDay1 + amountOfFishDay2 + amountOfFishDay3 + amountOfFishDay4 +
               amountOfFishDay5 + amountOfFishDay6 + amountOfFishDay7 + amountOfFishDay8;    }
}
