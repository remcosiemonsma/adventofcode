package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Day11 implements AdventOfCodeSolution<Long> {
    private final List<Long> testNumbers = new ArrayList<>();
    private static long superModulo;
    
    @Override
    public Long handlePart1(Stream<String> input) {
        List<String> lines = input.toList();

        Map<String, Monkey> monkeyMap = new HashMap<>();
        List<Monkey> monkeys = new ArrayList<>();

        for (int i = 0; i < lines.size(); i += 7) {
            List<String> subList = lines.subList(i, i + 6);

            Monkey monkey = createMonkey(subList, false);

            monkeyMap.put(monkey.getId(), monkey);
            monkeys.add(monkey);
        }

        monkeyMap.values()
                 .forEach(monkey -> monkey.setTargets(monkeyMap));

        superModulo = testNumbers.stream()
                                 .reduce((number1, number2) -> number1 * number2)
                                 .orElseThrow(() -> new AssertionError("Aak!"));
        
        for (int i = 0; i < 20; i++) {
            monkeys.forEach(Monkey::doTurn);
        }

        return monkeys.stream()
                      .map(Monkey::getItemsInspected)
                      .sorted(Comparator.reverseOrder())
                      .limit(2)
                      .reduce((i1, i2) -> i1 * i2)
                      .orElseThrow(() -> new AssertionError("Ook!"));
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        List<String> lines = input.toList();

        Map<String, Monkey> monkeyMap = new HashMap<>();
        List<Monkey> monkeys = new ArrayList<>();

        for (int i = 0; i < lines.size(); i += 7) {
            List<String> subList = lines.subList(i, i + 6);

            Monkey monkey = createMonkey(subList, true);

            monkeyMap.put(monkey.getId(), monkey);
            monkeys.add(monkey);
        }

        monkeyMap.values()
                 .forEach(monkey -> monkey.setTargets(monkeyMap));

        superModulo = testNumbers.stream()
                                 .reduce((number1, number2) -> number1 * number2)
                                 .orElseThrow(() -> new AssertionError("Aak!"));
        
        for (int i = 0; i < 10000; i++) {
            monkeys.forEach(Monkey::doTurn);
        }

        return monkeys.stream()
                      .map(Monkey::getItemsInspected)
                      .sorted(Comparator.reverseOrder())
                      .limit(2)
                      .reduce((i1, i2) -> i1 * i2)
                      .orElseThrow(() -> new AssertionError("Ook!"));
    }

    private Monkey createMonkey(List<String> input, boolean worried) {
        String monkeyId = input.get(0).substring(7).replace(":", "");
        Function<Long, Long> function = parseFunction(input.get(2));
        Predicate<Long> test = parseTest(input.get(3));
        String targetTrue = input.get(4).substring(29);
        String targetFalse = input.get(5).substring(30);

        Monkey monkey = new Monkey(monkeyId, function, test, targetTrue, targetFalse, worried);

        String itemString = input.get(1).substring(18);
        Arrays.stream(itemString.split(", "))
              .map(Long::parseLong)
              .forEach(monkey::catchItem);

        return monkey;
    }

    private Function<Long, Long> parseFunction(String line) {
        String part = line.substring(23);

        if ("* old".equals(part)) {
            return number -> number * number;
        } else {
            final long value = Long.parseLong(part.split(" ")[1]);
            return switch (part.charAt(0)) {
                case '*' -> number -> number * value;
                case '+' -> number -> number + value;
                default -> throw new AssertionError("Eek!");
            };
        }
    }

    private Predicate<Long> parseTest(String line) {
        Long testNumber = Long.parseLong(line.substring(21));
        testNumbers.add(testNumber);
        return number -> number % testNumber == 0;
    }

    private static class Monkey {
        private final String id;
        private final Queue<Long> items = new ArrayDeque<>();
        private final Function<Long, Long> operation;
        private final Predicate<Long> test;
        private final String targetTrueId;
        private final String targetFalseId;
        private final boolean worried;
        private Monkey targetTrue;
        private Monkey targetFalse;
        private long itemsInspected;

        private Monkey(String id, Function<Long, Long> operation, Predicate<Long> test, String targetTrueId,
                       String targetFalseId, boolean worried) {
            this.id = id;
            this.operation = operation;
            this.test = test;
            this.targetTrueId = targetTrueId;
            this.targetFalseId = targetFalseId;
            this.worried = worried;
        }

        public void setTargets(Map<String, Monkey> monkeys) {
            targetTrue = monkeys.get(targetTrueId);
            targetFalse = monkeys.get(targetFalseId);
        }

        public void doTurn() {
            while (!items.isEmpty()) {
                Long item = items.remove();
                
                item %= superModulo;

                item = operation.apply(item);
                if (!worried) {
                    item /= 3;
                }

                if (test.test(item)) {
                    targetTrue.catchItem(item);
                } else {
                    targetFalse.catchItem(item);
                }
                itemsInspected++;
            }
        }

        public void catchItem(Long item) {
            items.add(item);
        }

        public String getId() {
            return id;
        }

        public long getItemsInspected() {
            return itemsInspected;
        }

        @Override
        public String toString() {
            return "Monkey{" +
                   "id='" + id + '\'' +
                   ", items=" + items +
                   ", targetTrueId='" + targetTrueId + '\'' +
                   ", targetFalseId='" + targetFalseId + '\'' +
                   ", itemsInspected=" + itemsInspected +
                   '}';
        }
    }
}
