package nl.remcoder.adventofcode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day12 {
    public int handlePart1(Stream<String> input) {
        var data = input.findFirst()
                        .orElseThrow(() -> new AssertionError("Eek!"));

        var replaced = data.replaceAll("[^\\d-,]", "");

        var numbers = replaced.split(",");

        return Arrays.stream(numbers)
                     .filter(number -> !number.isEmpty())
                     .mapToInt(Integer::parseInt)
                     .sum();
    }

    public int handlePart2(Stream<String> input) {
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
        int total = 0;

        if (object instanceof Integer) {
            total = (int) object;
        } else if (object instanceof JSONArray jsonArray) {
            total = IntStream.range(0, jsonArray.length())
                             .map(position -> getValue(jsonArray.get(position)))
                             .sum();
        } else if (object instanceof JSONObject jsonObject) {
            var names = jsonObject.names();

            for (int position = 0; position < names.length(); position++) {
                String name = (String) names.get(position);
                if ("red".equals(jsonObject.get(name))) {
                    return 0;
                } else {
                    total += getValue(jsonObject.get(name));
                }
            }
        }

        return total;
    }
}
