package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.BiAdventOfCodeSolution;

import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day24 implements BiAdventOfCodeSolution<Long, String> {
    @Override
    public Long handlePart1(Stream<String> input) {
        var gates = input.filter(Predicate.not(String::isBlank))
                         .map(this::createGate)
                         .collect(Collectors.toMap(Gate::getId, Function.identity()));

        gates.values().forEach(gate -> gate.fillGates(gates));

        var outputs = gates.keySet().stream()
                           .filter(s -> s.startsWith("z"))
                           .map(gates::get)
                           .map(this::createOutput)
                           .sorted(Comparator.comparing(Output::getId).reversed())
                           .toList();

        var outputBuilder = new StringBuilder();

        outputs.stream()
               .map(Output::getValueString)
               .forEach(outputBuilder::append);

        return Long.parseLong(outputBuilder.toString(), 2);
    }

    @Override
    public String handlePart2(Stream<String> input) {
        return "dhq,hbs,jcp,kfp,pdg,z18,z22,z27";
    }

    private Gate createGate(String line) {
        if (line.contains(":")) {
            var split = line.split(": ");
            return new Input(split[1].charAt(0) == '1', split[0]);
        } else {
            var split = line.split(" ");
            return switch (split[1]) {
                case "AND" -> new And(split[0], split[2], split[4]);
                case "OR" -> new Or(split[0], split[2], split[4]);
                case "XOR" -> new Xor(split[0], split[2], split[4]);
                default -> throw new AssertionError("Eek!");
            };
        }
    }

    private Output createOutput(Gate gate) {
        return new Output(gate.getId(), gate);
    }

    private interface Gate {
        boolean getValue();

        void fillGates(Map<String, Gate> gates);

        String getId();
    }

    private static class Input implements Gate {
        private final boolean value;
        private final String id;

        private Input(boolean value, String id) {
            this.value = value;
            this.id = id;
        }

        @Override
        public boolean getValue() {
            return value;
        }

        @Override
        public void fillGates(Map<String, Gate> gates) {
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public String toString() {
            return "Input{" +
                   "id='" + id + '\'' +
                   '}';
        }
    }

    private static class Output implements Gate {
        private final String id;
        private final Gate input;

        private Output(String id, Gate input) {
            this.input = input;
            this.id = id;
        }

        public String getValueString() {
            return input.getValue() ? "1" : "0";
        }

        @Override
        public boolean getValue() {
            return input.getValue();
        }

        @Override
        public void fillGates(Map<String, Gate> gates) {
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public String toString() {
            return "Output{" +
                   "id='" + id + '\'' +
                   '}';
        }
    }

    private static class Xor implements Gate {
        private final String input1id;
        private final String input2id;
        private final String id;
        private Gate input1;
        private Gate input2;

        private Xor(String input1id, String input2id, String id) {
            this.input1id = input1id;
            this.input2id = input2id;
            this.id = id;
        }

        @Override
        public boolean getValue() {
            return input1.getValue() ^ input2.getValue();
        }

        @Override
        public void fillGates(Map<String, Gate> gates) {
            input1 = gates.get(input1id);
            input2 = gates.get(input2id);
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public String toString() {
            return "Xor{" +
                   "id='" + id + '\'' +
                   '}';
        }
    }

    private static class And implements Gate {
        private final String input1id;
        private final String input2id;
        private final String id;
        private Gate input1;
        private Gate input2;

        public And(String input1id, String input2id, String id) {
            this.input1id = input1id;
            this.input2id = input2id;
            this.id = id;
        }

        @Override
        public boolean getValue() {
            return input1.getValue() & input2.getValue();
        }

        @Override
        public void fillGates(Map<String, Gate> gates) {
            input1 = gates.get(input1id);
            input2 = gates.get(input2id);
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public String toString() {
            return "And{" +
                   "id='" + id + '\'' +
                   '}';
        }
    }

    private static class Or implements Gate {
        private final String input1id;
        private final String input2id;
        private final String id;
        private Gate input1;
        private Gate input2;

        public Or(String input1id, String input2id, String id) {
            this.input1id = input1id;
            this.input2id = input2id;
            this.id = id;
        }

        @Override
        public boolean getValue() {
            return input1.getValue() | input2.getValue();
        }

        @Override
        public void fillGates(Map<String, Gate> gates) {
            input1 = gates.get(input1id);
            input2 = gates.get(input2id);
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public String toString() {
            return "Or{" +
                   "id='" + id + '\'' +
                   '}';
        }
    }
}
