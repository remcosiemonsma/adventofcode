package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.stream.CombiningCollector;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day13 implements AdventOfCodeSolution<Long> {
    private static final Pattern BUTTON_PATTERN = Pattern.compile("Button .: X\\+(\\d+), Y\\+(\\d+)");
    private static final Pattern PRIZE_PATTERN = Pattern.compile("Prize: X=(\\d+), Y=(\\d+)");

    @Override
    public Long handlePart1(Stream<String> input) {
        var clawMachines = input.collect(new CombiningCollector<>(Function.identity(), String::isBlank, ClawMachineBag::new))
                                .map(ClawMachineBag.class::cast)
                                .map(ClawMachineBag::toClawMachine)
                                .toList();

        return clawMachines.stream()
                           .mapToLong(this::playMachine)
                           .sum();
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var clawMachines = input.collect(new CombiningCollector<>(Function.identity(), String::isBlank, ClawMachineBag::new))
                                .map(ClawMachineBag.class::cast)
                                .map(ClawMachineBag::toClawMachine)
                                .map(this::mapToPart2)
                                .toList();

        return clawMachines.stream()
                           .mapToLong(this::playMachine)
                           .sum();
    }

    private ClawMachine mapToPart2(ClawMachine clawMachine) {
        return new ClawMachine(clawMachine.a(), clawMachine.b(),
                               new PrizeLocation(clawMachine.prizeLocation().x() + 10000000000000L,
                                                 clawMachine.prizeLocation().y() + 10000000000000L));
    }

    private static class ClawMachineBag implements CombiningCollector.Bag<String> {
        private final List<String> list = new ArrayList<>();

        @Override
        public void add(String line) {
            list.add(line);
        }

        public ClawMachine toClawMachine() {
            var a = mapButton(list.getFirst());
            var b = mapButton(list.get(1));
            return mapClawMachine(a, b, list.get(2));
        }

        private Button mapButton(String buttonLine) {
            var matcher = BUTTON_PATTERN.matcher(buttonLine);
            if (matcher.matches()) {
                return new Button(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
            } else {
                throw new AssertionError("Eek!");
            }
        }

        private ClawMachine mapClawMachine(Button a, Button b, String prizeLine) {
            var matcher = PRIZE_PATTERN.matcher(prizeLine);
            if (matcher.matches()) {
                return new ClawMachine(a, b, new PrizeLocation(Integer.parseInt(matcher.group(1)),
                                                               Integer.parseInt(matcher.group(2))));
            } else {
                throw new AssertionError("Eek!");
            }
        }
    }

    private long playMachine(ClawMachine clawMachine) {
        var ax = clawMachine.a().x();
        var ay = clawMachine.a().y();
        var bx = clawMachine.b().x();
        var by = clawMachine.b().y();
        var px = clawMachine.prizeLocation().x();
        var py = clawMachine.prizeLocation().y();

        // Blijkbaar is Cramer's rule hier van toepassing

        var d = ax * by - ay * bx;
        var dx = px * by - py * bx;
        var dy = ax * py - ay * px;

        if (dx % d != 0 || dy % d != 0) {
            // Geen oplossing mogelijk
            return 0;
        }

        var x = dx / d;
        var y = dy / d;

        return x * 3 + y;
    }

    private record ClawMachine(Button a, Button b, PrizeLocation prizeLocation) {
    }

    private record PrizeLocation(long x, long y) {
    }

    private record Button(int x, int y) {
    }
}
