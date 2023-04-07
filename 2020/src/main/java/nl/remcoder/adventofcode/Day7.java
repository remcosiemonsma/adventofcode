package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.BiAdventOfCodeSolution;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day7 implements BiAdventOfCodeSolution<Integer, Long> {
    private static final Pattern BAG_REGEX = Pattern.compile("(\\d+) (.*?) bag");

    @Override
    public Integer handlePart1(Stream<String> input) {
        var bags = input.map(this::transformStringToBag)
                        .toList();

        var colours = new HashSet<String>();
        colours.add("shiny gold");

        var outerbags = new HashSet<String>();

        while (!colours.isEmpty()) {
            var newColours = new HashSet<String>();

            colours.stream()
                   .map(colour -> bags.stream()
                                      .filter(bag -> bag.contents().containsKey(colour))
                                      .map(Bag::colour)
                                      .collect(Collectors.toSet()))
                   .forEach(newColours::addAll);

            newColours.removeAll(outerbags);
            outerbags.addAll(newColours);

            colours.clear();
            colours.addAll(newColours);
        }

        return outerbags.size();
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var bags = input.map(this::transformStringToBag)
                        .collect(Collectors.toMap(Bag::colour, bag -> bag));

        return countBagsInBag("shiny gold", bags);
    }

    private long countBagsInBag(String bagColour, Map<String, Bag> bags) {
        var bag = bags.get(bagColour);

        var totalAmount = 0;

        for (var colour : bag.contents().keySet()) {
            var bagsInBag = countBagsInBag(colour, bags);
            totalAmount += (bagsInBag * bag.contents().get(colour)) + bag.contents().get(colour);
        }

        return totalAmount;
    }

    private Bag transformStringToBag(String data) {
        var split = data.split(" bags contain ");
        var contentSplit = split[1].split(", ");

        var contents = new HashMap<String, Long>();

        for (var content : contentSplit) {
            var matcher = BAG_REGEX.matcher(content);
            if (matcher.find()) {
                contents.put(matcher.group(2), Long.valueOf(matcher.group(1)));
            }
        }

        return new Bag(split[0], Map.copyOf(contents));
    }

    private record Bag(String colour, Map<String, Long> contents) {
    }
}
