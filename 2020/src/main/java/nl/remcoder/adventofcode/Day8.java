package nl.remcoder.adventofcode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day8 {
    public int handlePart1(Stream<String> input) {
        List<Operation> operations = input.map(this::transformStringToOperation)
                                       .collect(Collectors.toList());

        int accumulator = 0;

        int counter = 0;

        while(true) {
            Operation operation = operations.get(counter);

            if (operation.isVisited()) {
                return accumulator;
            }

            operation.setVisited(true);

            switch (operation.operand) {
                case "acc" -> accumulator += operation.value;
                case "jmp" -> counter += operation.value - 1;
            }

            counter++;
        }
    }

    public int handlePart2(Stream<String> input) {
        List<Operation> operations = input.map(this::transformStringToOperation)
                                          .collect(Collectors.toList());

        for (int i = 0; i < operations.size(); i++) {
            operations.forEach(operation -> operation.setVisited(false));

            Operation operation = operations.get(i);
            if ("acc".equals(operation.operand)) {
                continue;
            }

            List<Operation> modifiedOperations = new ArrayList<>(operations);

            Operation newOperation;
            switch (operation.operand) {
                case "nop" -> newOperation = new Operation("jmp", operation.value);
                case "jmp" -> newOperation = new Operation("nop", operation.value);
                default -> {continue;}
            }

            modifiedOperations.set(i, newOperation);

            AtomicInteger accumulatorValueContainer = new AtomicInteger();
            if (doesProgramTerminate(modifiedOperations, accumulatorValueContainer)) {
                return accumulatorValueContainer.get();
            }
        }

        return -1;
    }

    private Operation transformStringToOperation(String operation) {
        String[] spliteration = operation.split(" ");

        return new Operation(spliteration[0], Integer.parseInt(spliteration[1]));
    }

    private boolean doesProgramTerminate(List<Operation> operations, AtomicInteger accumulatorValueContainer) {
        int accumulator = 0;

        int counter = 0;

        while(counter < operations.size()) {
            Operation operation = operations.get(counter);

            if (operation.isVisited()) {
                return false;
            }

            operation.setVisited(true);

            switch (operation.operand) {
                case "acc" -> accumulator += operation.value;
                case "jmp" -> counter += operation.value - 1;
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
