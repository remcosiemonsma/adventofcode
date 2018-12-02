package nl.remcoder.adventofcode.day2;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Part2 {
    public static void main(String[] args) throws Exception {
        List<String> strings = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("day2/input").toURI()));

        String first = null;
        String second = null;

        one: for (int i = 0; i < strings.size(); i++) {
            String one = strings.get(i);

            two: for (int j = i + 1; j < strings.size(); j++) {
                String two = strings.get(j);

                if (one.length() != two.length()) {
                    continue;
                }

                int differences = 0;

                for (int k = 0; k < one.length(); k++) {
                    if (one.charAt(k) != two.charAt(k)) {
                        differences++;
                    }

                    if (differences > 1) {
                        continue two;
                    }
                }

                if (differences == 1) {
                    first = one;
                    second = two;
                    break one;
                }
            }
        }

        StringBuilder sb = new StringBuilder();

        for (int k = 0; k < first.length(); k++) {
            if (first.charAt(k) == second.charAt(k)) {
                sb.append(first.charAt(k));
            }
        }

        System.out.println(sb);
    }
}
