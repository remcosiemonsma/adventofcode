package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Day8 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var operations = input.map(this::transformStringToOperation)
                              .toList();

        var accumulator = 0;

        var counter = 0;

        while (true) {
            var operation = operations.get(counter);

            if (operation.isVisited()) {
                return accumulator;
            }

            operation.setVisited(true);

            switch (operation.getOperand()) {
                case "acc" -> accumulator += operation.getValue();
                case "jmp" -> counter += operation.getValue() - 1;
            }

            counter++;
        }
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var operations = input.map(this::transformStringToOperation)
                              .toList();

        for (var i = 0; i < operations.size(); i++) {
            operations.forEach(operation -> operation.setVisited(false));

            var operation = operations.get(i);
            if ("acc".equals(operation.getOperand())) {
                continue;
            }

            var modifiedOperations = new ArrayList<>(operations);

            Operation newOperation;
            switch (operation.getOperand()) {
                case "nop" -> newOperation = new Operation("jmp", operation.getValue());
                case "jmp" -> newOperation = new Operation("nop", operation.getValue());
                default -> {
                    continue;
                }
            }

            modifiedOperations.set(i, newOperation);

            var accumulatorValueContainer = new AtomicInteger();
            if (doesProgramTerminate(modifiedOperations, accumulatorValueContainer)) {
                return accumulatorValueContainer.get();
            }
        }

        return -1;
    }

    private Operation transformStringToOperation(String operation) {
        var spliteration = operation.split(" ");

        return new Operation(spliteration[0], Integer.parseInt(spliteration[1]));
    }

    private boolean doesProgramTerminate(List<Operation> operations, AtomicInteger accumulatorValueContainer) {
        var accumulator = 0;

        var counter = 0;

        while (counter < operations.size()) {
            var operation = operations.get(counter);

            if (operation.isVisited()) {
                return false;
            }

            operation.setVisited(true);

            switch (operation.getOperand()) {
                case "acc" -> accumulator += operation.getValue();
                case "jmp" -> counter += operation.getValue() - 1;
            }

            counter++;
        }

        accumulatorValueContainer.set(accumulator);

        return true;
    }

    private static class Operation {
        private final String operand;
        private final int value;
        private boolean visited;

        private Operation(String operand, int value) {
            this.operand = operand;
            this.value = value;
            this.visited = false;
        }

        public String getOperand() {
            return operand;
        }

        public int getValue() {
            return value;
        }

        public boolean isVisited() {
            return visited;
        }

        public void setVisited(boolean visited) {
            this.visited = visited;
        }
    }
}
