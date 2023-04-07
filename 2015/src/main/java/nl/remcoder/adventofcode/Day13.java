package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day13 implements AdventOfCodeSolution<Integer> {
    private static final Pattern HAPPINESS_PATTERN =
            Pattern.compile("(.*) would (lose|gain) (\\d*) happiness units by sitting next to (.*)\\.");

    @Override 
    public Integer handlePart1(Stream<String> input) {
        var personsMap = new HashMap<String, Person>();

        input.map(HAPPINESS_PATTERN::matcher)
             .filter(Matcher::matches)
             .forEach(matcher -> processMatch(matcher, personsMap));

        var highestRating = Integer.MIN_VALUE;

        var persons = new ArrayList<>(personsMap.values());

        var first = persons.remove(0);
        var seated = new ArrayList<Person>();
        seated.add(first);

        var arrangements = arrangeSeats(seated, persons);

        for (var arrangement : arrangements) {
            var score = calculateSeatingScore(arrangement);

            if (score > highestRating) {
                highestRating = score;
            }
        }

        return highestRating;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var personsMap = new HashMap<String, Person>();

        input.map(HAPPINESS_PATTERN::matcher)
             .filter(Matcher::matches)
             .forEach(matcher -> processMatch(matcher, personsMap));

        var highestRating = Integer.MIN_VALUE;

        var persons = new ArrayList<>(personsMap.values());

        var you = new Person();

        var seated = new ArrayList<Person>();
        seated.add(you);

        var arrangements = arrangeSeats(seated, persons);

        for (var arrangement : arrangements) {
            var score = calculateSeatingScore(arrangement);

            if (score > highestRating) {
                highestRating = score;
            }
        }

        return highestRating;
    }

    private List<List<Person>> arrangeSeats(List<Person> seated, List<Person> toBeSeated) {
        var arrangements = new ArrayList<List<Person>>();

        for (var next : toBeSeated) {
            var newSeated = new ArrayList<>(seated);
            newSeated.add(next);
            var newToBeSeated = new ArrayList<>(toBeSeated);
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
        var score = 0;

        for (var i = 0; i < seating.size(); i++) {
            var current = seating.get(i);
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
        var person = persons.computeIfAbsent(matcher.group(1), s -> new Person());

        var other = persons.computeIfAbsent(matcher.group(4), s -> new Person());

        var happiness = Integer.parseInt(matcher.group(3)) * (matcher.group(2).equals("lose") ? -1 : 1);

        person.happinessImpact.put(other, happiness);
    }

    private static class Person {
        private final Map<Person, Integer> happinessImpact;

        private Person() {
            happinessImpact = new HashMap<>();
        }
    }
}
