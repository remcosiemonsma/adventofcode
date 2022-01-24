package nl.remcoder.adventofcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day13 {
    private static final Pattern HAPPINESS_PATTERN = Pattern.compile("(.*) would (lose|gain) (\\d*) happiness units by sitting next to (.*)\\.");

    public int handlePart1(Stream<String> input) {
        Map<String, Person> personsMap = new HashMap<>();

        input.map(HAPPINESS_PATTERN::matcher)
                .filter(Matcher::matches)
                .forEach(matcher -> processMatch(matcher, personsMap));

        int highestRating = Integer.MIN_VALUE;

        List<Person> persons = new ArrayList<>(personsMap.values());

        Person first = persons.remove(0);
        List<Person> seated = new ArrayList<>();
        seated.add(first);

        List<List<Person>> arrangements = arrangeSeats(seated, persons);

        for (List<Person> arrangement : arrangements) {
            int score = calculateSeatingScore(arrangement);

            if (score > highestRating) {
                highestRating = score;
            }
        }

        return highestRating;
    }

    public int handlePart2(Stream<String> input) {
        Map<String, Person> personsMap = new HashMap<>();

        input.map(HAPPINESS_PATTERN::matcher)
             .filter(Matcher::matches)
             .forEach(matcher -> processMatch(matcher, personsMap));

        int highestRating = Integer.MIN_VALUE;

        List<Person> persons = new ArrayList<>(personsMap.values());

        Person you = new Person();

        List<Person> seated = new ArrayList<>();
        seated.add(you);

        List<List<Person>> arrangements = arrangeSeats(seated, persons);

        for (List<Person> arrangement : arrangements) {
            int score = calculateSeatingScore(arrangement);

            if (score > highestRating) {
                highestRating = score;
            }
        }

        return highestRating;
    }

    private List<List<Person>> arrangeSeats(List<Person> seated, List<Person> toBeSeated) {
        List<List<Person>> arrangements = new ArrayList<>();

        for (Person next : toBeSeated) {
            List<Person> newSeated = new ArrayList<>(seated);
            newSeated.add(next);
            List<Person> newToBeSeated = new ArrayList<>(toBeSeated);
            newToBeSeated.remove(next);
            if (newToBeSeated.isEmpty()) {
                arrangements.add(newSeated);
            } else {
                arrangements.addAll(arrangeSeats(newSeated, newToBeSeated));
            }
        }

        return arrangements;
    }

    private int calculateSeatingScore(List<Person> seating) {
        int score = 0;

        for (int i = 0; i < seating.size(); i++) {
            Person current = seating.get(i);
            Person left;
            if (i == 0) {
                left = seating.get(seating.size() - 1);
            } else {
                left = seating.get(i - 1);
            }
            Person right;
            if (i == seating.size() - 1) {
                right = seating.get(0);
            } else {
                right = seating.get(i + 1);
            }
            score += current.happinessImpact.getOrDefault(left, 0);
            score += current.happinessImpact.getOrDefault(right, 0);
        }

        return score;
    }

    private void processMatch(Matcher matcher, Map<String, Person> persons) {
        Person person = persons.computeIfAbsent(matcher.group(1), s -> new Person());

        Person other = persons.computeIfAbsent(matcher.group(4), s -> new Person());

        int happiness = Integer.parseInt(matcher.group(3)) * (matcher.group(2).equals("lose") ? -1 : 1);

        person.happinessImpact.put(other, happiness);
    }

    private static class Person {
        private final Map<Person, Integer> happinessImpact;

        private Person() {
            happinessImpact = new HashMap<>();
        }
    }
}
