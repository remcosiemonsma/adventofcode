package nl.remcoder.adventofcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day7 {
    private static final Pattern BAG_REGEX = Pattern.compile("(\\d+) (.*?) bag");

    public int handlePart1(Stream<String> input) {
        List<Bag> bags = input.map(this::transformStringToBag)
                              .collect(Collectors.toList());

        Set<String> colours = new HashSet<>();
        colours.add("shiny gold");

        Set<String> outerbags = new HashSet<>();

        while (!colours.isEmpty()) {
            Set<String> newColours = new HashSet<>();

            colours.stream()
                   .map(colour -> bags.stream()
                                      .filter(bag -> bag.getContents().containsKey(colour))
                                      .map(Bag::getColour)
                                      .collect(Collectors.toSet()))
                   .forEach(newColours::addAll);

            newColours.removeAll(outerbags);
            outerbags.addAll(newColours);

            colours.clear();
            colours.addAll(newColours);
        }

        return outerbags.size();
    }

    public long handlePart2(Stream<String> input) {
        Map<String, Bag> bags = input.map(this::transformStringToBag)
                                     .collect(Collectors.toMap(Bag::getColour, bag -> bag));

        return countBagsInBag("shiny gold", bags);
    }

    private long countBagsInBag(String bagColour, Map<String, Bag> bags) {
        Bag bag = bags.get(bagColour);

        long totalAmount = 0;

        for (String colour : bag.getContents().keySet()) {
            long bagsInBag = countBagsInBag(colour, bags);
            totalAmount += (bagsInBag * bag.getContents().get(colour)) + bag.getContents().get(colour);
        }

        return totalAmount;
    }

    private Bag transformStringToBag(String data) {
        String[] split = data.split(" bags contain ");
        String[] contentSplit = split[1].split(", ");

        Map<String, Long> contents = new HashMap<>();

        for (String content : contentSplit) {
            Matcher matcher = BAG_REGEX.matcher(content);
            if (matcher.find()) {
                contents.put(matcher.group(2), Long.valueOf(matcher.group(1)));
            }
        }

        return new Bag(split[0], Map.copyOf(contents));
    }

    private static class Bag {
        private final String colour;
        private final Map<String, Long> contents;

        private Bag(String colour, Map<String, Long> contents) {
            this.colour = colour;
            this.contents = contents;
        }

        public String getColour() {
            return colour;
        }

        public Map<String, Long> getContents() {
            return contents;
        }

        @Override
        public String toString() {
            return "Bag{" +
                   "color='" + colour + '\'' +
                   ", contents=" + contents +
                   '}';
        }
    }
}
