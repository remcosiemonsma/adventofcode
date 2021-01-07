package nl.remcoder.adventofcode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day12 {
    public int handlePart1(Stream<String> input) {
        String data = input.findFirst().get();

        String replaced = data.replaceAll("[^\\d-,]", "");

        String[] numbers = replaced.split(",");

        return Arrays.stream(numbers)
                     .filter(number -> !number.isEmpty())
                     .mapToInt(Integer::parseInt)
                     .sum();
    }

    public int handlePart2(Stream<String> input) {
        String data = input.findFirst().get();

        if (data.charAt(0) == '{') {
            JSONObject jsonObject = new JSONObject(data);
            return getValue(jsonObject);
        } else if (data.charAt(0) == '[') {
            JSONArray jsonArray = new JSONArray(data);
            return getValue(jsonArray);
        }

        return -1;
    }

    private int getValue(Object object) {
        int total = 0;

        if (object instanceof Integer) {
            total = (int) object;
        } else if (object instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) object;
            total = IntStream.range(0, jsonArray.length())
                             .map(position -> getValue(jsonArray.get(position)))
                             .sum();
        } else if (object instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) object;
            JSONArray names = jsonObject.names();

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
