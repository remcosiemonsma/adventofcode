package nl.remcoder.adventofcode;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day16 {
    private final static Pattern TICKET_PATTERN = Pattern.compile("^[0-9,]+$");
    private final static Pattern RULE_PATTERN = Pattern.compile("^([\\w\\s]+): (\\d+)-(\\d+) or (\\d+)-(\\d+)$");

    public int handlePart1(Stream<String> input) {
        List<Rule> rules = new ArrayList<>();
        List<List<Integer>> tickets = new ArrayList<>();

        input.forEach(line -> {
            Matcher ticketMatcher = TICKET_PATTERN.matcher(line);
            Matcher ruleMatcher = RULE_PATTERN.matcher(line);

            if (ticketMatcher.matches()) {
                tickets.add(Arrays.stream(line.split(","))
                                  .map(Integer::parseInt)
                                  .collect(Collectors.toList()));
            } else if (ruleMatcher.matches()) {
                rules.add(new Rule(ruleMatcher.group(1),
                                   Integer.parseInt(ruleMatcher.group(2)),
                                   Integer.parseInt(ruleMatcher.group(3)),
                                   Integer.parseInt(ruleMatcher.group(4)),
                                   Integer.parseInt(ruleMatcher.group(5))));
            }
        });

        return tickets.stream()
                      .flatMap(Collection::stream)
                      .filter(value -> rules.stream().noneMatch(rule -> rule.isIntegerInValidRange(value)))
                      .reduce(Integer::sum)
                      .get();
    }

    public long handlePart2(Stream<String> input) {
        List<Rule> rules = new ArrayList<>();
        List<List<Integer>> tickets = new ArrayList<>();

        List<Integer> myTicket = null;

        List<String> lines = input.collect(Collectors.toList());

        for (String line : lines) {
            Matcher ruleMatcher = RULE_PATTERN.matcher(line);

            if (ruleMatcher.matches()) {
                rules.add(new Rule(ruleMatcher.group(1),
                                   Integer.parseInt(ruleMatcher.group(2)),
                                   Integer.parseInt(ruleMatcher.group(3)),
                                   Integer.parseInt(ruleMatcher.group(4)),
                                   Integer.parseInt(ruleMatcher.group(5))));

                continue;
            }

            Matcher ticketMatcher = TICKET_PATTERN.matcher(line);

            if (ticketMatcher.matches()) {
                if (myTicket == null) {
                    myTicket = Arrays.stream(line.split(","))
                                     .map(Integer::parseInt)
                                     .collect(Collectors.toList());
                } else {
                    List<Integer> ticket = Arrays.stream(line.split(","))
                                                  .map(Integer::parseInt)
                                                  .collect(Collectors.toList());

                    boolean isTicketValid = true;

                    for (int value : ticket) {
                        if (rules.stream().noneMatch(rule -> rule.isIntegerInValidRange(value))) {
                            isTicketValid = false;
                            break;
                        }
                    }

                    if (isTicketValid) {
                        tickets.add(ticket);
                    }
                }
            }
        }

        Rule[] rulePositions = new Rule[rules.size()];

        Map<Integer, List<Rule>> possiblePositionRules = new HashMap<>();

        IntStream.range(0, rules.size()).forEach(position -> possiblePositionRules.put(position, new ArrayList<>(rules)));

        while (!possiblePositionRules.isEmpty()) {
            Set<Integer> set = new HashSet<>(possiblePositionRules.keySet());
            for (int position : set) {
                List<Rule> possibleRules = new ArrayList<>(possiblePositionRules.get(position));

                for (List<Integer> ticket : tickets) {
                    possibleRules.removeIf(rule -> !rule.isIntegerInValidRange(ticket.get(position)));
                }

                if (possibleRules.size() == 1) {
                    rulePositions[position] = possibleRules.get(0);
                    possiblePositionRules.values().forEach(rules1 -> rules1.remove(possibleRules.get(0)));
                    possiblePositionRules.remove(position);
                }
            }
        }

        long result = 1;

        for (int position = 0; position < rulePositions.length; position++) {
            Rule rule = rulePositions[position];

            if (rule.name.startsWith("departure")) {
                result *= (long) myTicket.get(position);
            }
        }

        return result;
    }

    private static class Rule {
        private final String name;
        private final int startRange1;
        private final int endRange1;
        private final int startRange2;
        private final int endRange2;

        private Rule(String name, int startRange1, int endRange1, int startRange2, int endRange2) {
            this.name = name;
            this.startRange1 = startRange1;
            this.endRange1 = endRange1;
            this.startRange2 = startRange2;
            this.endRange2 = endRange2;
        }

        public boolean isIntegerInValidRange(int value) {
            return ((value >= startRange1) && (value <= endRange1)) ||
                   ((value >= startRange2) && (value <= endRange2));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Rule rule = (Rule) o;
            return startRange1 == rule.startRange1 && endRange1 == rule.endRange1 && startRange2 == rule.startRange2 &&
                   endRange2 == rule.endRange2 && Objects.equals(name, rule.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, startRange1, endRange1, startRange2, endRange2);
        }

        @Override
        public String toString() {
            return "Rule{" +
                   "name='" + name + '\'' +
                   ", startRange1=" + startRange1 +
                   ", endRange1=" + endRange1 +
                   ", startRange2=" + startRange2 +
                   ", endRange2=" + endRange2 +
                   '}';
        }
    }
}
