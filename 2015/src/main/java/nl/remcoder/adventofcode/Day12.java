package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day12 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var data = input.findFirst()
                        .orElseThrow(() -> new AssertionError("Eek!"));

        var replaced = data.replaceAll("[^\\d-,]", "");

        var numbers = replaced.split(",");

        return Arrays.stream(numbers)
                     .filter(Predicate.not(String::isEmpty))
                     .mapToInt(Integer::parseInt)
                     .sum();
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var data = input.findFirst()
                        .orElseThrow(() -> new AssertionError("Eek!"));

        if (data.charAt(0) == '{') {
            var jsonObject = new JSONObject(data);
            return getValue(jsonObject);
        } else if (data.charAt(0) == '[') {
            var jsonArray = new JSONArray(data);
            return getValue(jsonArray);
        }

        return -1;
    }

    private int getValue(Object object) {
        return switch (object) {
            case Integer i -> i;
            case JSONArray jsonArray -> calculateTotalForArray(jsonArray);
            case JSONObject jsonObject -> calculateTotalForObject(jsonObject);
            default -> 0;
        };
    }

    private int calculateTotalForArray(JSONArray jsonArray) {
        return IntStream.range(0, jsonArray.length())
                        .map(position -> getValue(jsonArray.get(position)))
                        .sum();
    }

    private int calculateTotalForObject(JSONObject jsonObject) {
        var total = 0;

        var names = jsonObject.names();

        for (var position = 0; position < names.length(); position++) {
            String name = (String) names.get(position);
            if ("red".equals(jsonObject.get(name))) {
                return 0;
            } else {
                total += getValue(jsonObject.get(name));
            }
        }

        return total;
    }
}
