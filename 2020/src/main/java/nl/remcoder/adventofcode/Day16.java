package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.BiAdventOfCodeSolution;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day16 implements BiAdventOfCodeSolution<Integer, Long> {
    private final static Pattern TICKET_PATTERN = Pattern.compile("^[0-9,]+$");
    private final static Pattern RULE_PATTERN = Pattern.compile("^([\\w\\s]+): (\\d+)-(\\d+) or (\\d+)-(\\d+)$");

    @Override
    public Integer handlePart1(Stream<String> input) {
        var rules = new ArrayList<Rule>();
        var tickets = new ArrayList<List<Integer>>();

        input.forEach(line -> {
            var ticketMatcher = TICKET_PATTERN.matcher(line);
            var ruleMatcher = RULE_PATTERN.matcher(line);

            if (ticketMatcher.matches()) {
                tickets.add(Arrays.stream(line.split(","))
                                  .map(Integer::parseInt)
                                  .toList());
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
                      .orElseThrow(() -> new AssertionError("Eek!"));
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var rules = new ArrayList<Rule>();
        var tickets = new ArrayList<List<Integer>>();

        List<Integer> myTicket = null;

        var lines = input.toList();

        for (var line : lines) {
            var ruleMatcher = RULE_PATTERN.matcher(line);

            if (ruleMatcher.matches()) {
                rules.add(new Rule(ruleMatcher.group(1),
                                   Integer.parseInt(ruleMatcher.group(2)),
                                   Integer.parseInt(ruleMatcher.group(3)),
                                   Integer.parseInt(ruleMatcher.group(4)),
                                   Integer.parseInt(ruleMatcher.group(5))));

                continue;
            }

            var ticketMatcher = TICKET_PATTERN.matcher(line);

            if (ticketMatcher.matches()) {
                if (myTicket == null) {
                    myTicket = Arrays.stream(line.split(","))
                                     .map(Integer::parseInt)
                                     .toList();
                } else {
                    var ticket = Arrays.stream(line.split(","))
                                       .map(Integer::parseInt)
                                       .toList();

                    var isTicketValid = true;

                    for (var value : ticket) {
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

        var rulePositions = new Rule[rules.size()];

        var possiblePositionRules = new HashMap<Integer, List<Rule>>();

        IntStream.range(0, rules.size())
                 .forEach(position -> possiblePositionRules.put(position, new ArrayList<>(rules)));

        while (!possiblePositionRules.isEmpty()) {
            var set = new HashSet<>(possiblePositionRules.keySet());
            for (var position : set) {
                var possibleRules = new ArrayList<>(possiblePositionRules.get(position));

                for (var ticket : tickets) {
                    possibleRules.removeIf(rule -> !rule.isIntegerInValidRange(ticket.get(position)));
                }

                if (possibleRules.size() == 1) {
                    rulePositions[position] = possibleRules.get(0);
                    possiblePositionRules.values().forEach(rules1 -> rules1.remove(possibleRules.get(0)));
                    possiblePositionRules.remove(position);
                }
            }
        }

        var result = 1L;

        for (int position = 0; position < rulePositions.length; position++) {
            Rule rule = rulePositions[position];

            if (rule.name.startsWith("departure")) {
                assert myTicket != null;
                result *= (long) myTicket.get(position);
            }
        }

        return result;
    }

    private record Rule(String name, int startRange1, int endRange1, int startRange2, int endRange2) {

        public boolean isIntegerInValidRange(int value) {
            return ((value >= startRange1) && (value <= endRange1)) ||
                   ((value >= startRange2) && (value <= endRange2));
        }
    }
}
