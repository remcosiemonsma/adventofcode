package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.*;
import java.util.stream.Stream;

public class Day18 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var operations = input.toList();

        var registers = new HashMap<String, Long>();

        registers.put("a", 0L);
        registers.put("b", 0L);
        registers.put("f", 0L);
        registers.put("i", 0L);
        registers.put("p", 0L);

        var counter = 0;

        var sndFrequency = 0L;

        var stop = false;

        while (!stop) {
            String operation = operations.get(counter);

            String[] operationParts = operation.split(" ");

            switch (operationParts[0]) {
                case "snd" -> {
                    sndFrequency = registers.get(operationParts[1]);
                    counter++;
                }
                case "set" -> {
                    long value;
                    try {
                        value = Long.parseLong(operationParts[2]);
                    } catch (NumberFormatException e) {
                        value = registers.get(operationParts[2]);
                    }
                    registers.put(operationParts[1], value);
                    counter++;
                }
                case "add" -> {
                    long add;
                    try {
                        add = Long.parseLong(operationParts[2]);
                    } catch (NumberFormatException e) {
                        add = registers.get(operationParts[2]);
                    }
                    registers.put(operationParts[1], registers.get(operationParts[1]) +
                                                     add);
                    counter++;
                }
                case "mul" -> {
                    long value;
                    try {
                        value = Long.parseLong(operationParts[2]);
                    } catch (NumberFormatException e) {
                        value = registers.get(operationParts[2]);
                    }
                    registers.put(operationParts[1],
                                  registers.get(operationParts[1]) * value);
                    counter++;
                }
                case "mod" -> {
                    long modulo;
                    try {
                        modulo = Long.parseLong(operationParts[2]);
                    } catch (NumberFormatException e) {
                        modulo = registers.get(operationParts[2]);
                    }
                    registers.put(operationParts[1], registers.get(operationParts[1]) %
                                                     modulo);
                    counter++;
                }
                case "rcv" -> {
                    if (registers.get(operationParts[1]) != 0) {
                        stop = true;
                    } else {
                        counter++;
                    }
                }
                case "jgz" -> {
                    if (registers.get(operationParts[1]) != 0) {
                        counter += Long.parseLong(operationParts[2]);
                    } else {
                        counter++;
                    }
                }
            }
        }

        return (int) sndFrequency;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var operations = input.toList();
        
        var queue0 = new ArrayDeque<Long>();
        var queue1 = new ArrayDeque<Long>();

        var program0 = new Program(operations, 0, queue1, queue0);
        var program1 = new Program(operations, 1, queue0, queue1);

        while (!program0.isWaiting() || !program1.isWaiting()) {
            while (!program0.isWaiting()) {
                program0.performOperations();
            }
            if (!program0.sendQueue.isEmpty()) {
                program1.waiting = false;
            }

            while (!program1.isWaiting()) {
                program1.performOperations();
            }
            if (!program1.sendQueue.isEmpty()) {
                program0.waiting = false;
            }
        }
        
        return program1.getSendCounter();
    }

    private static class Program {
        boolean waiting = false;
        List<String> operations;
        long initialPvalue;
        Queue<Long> sendQueue;
        Queue<Long> receiveQueue;
        int sendCounter = 0;
        Map<String, Long> registers = new HashMap<>();
        int counter = 0;

        public Program(List<String> operations, long initialPvalue, Queue<Long> sendQueue, Queue<Long> receiveQueue) {
            this.operations = operations;
            this.initialPvalue = initialPvalue;
            this.sendQueue = sendQueue;
            this.receiveQueue = receiveQueue;

            registers.put("a", 0L);
            registers.put("b", 0L);
            registers.put("f", 0L);
            registers.put("i", 0L);
            registers.put("p", initialPvalue);
        }

        public int getSendCounter() {
            return sendCounter;
        }

        public boolean isWaiting() {
            return waiting;
        }

        public void performOperations() {
            String operation = operations.get(counter);

            String[] operationParts = operation.split(" ");

            switch (operationParts[0]) {
                case "snd" -> {
                    sendQueue.add(registers.get(operationParts[1]));
                    counter++;
                    sendCounter++;
                }
                case "set" -> {
                    long value;
                    value = getValue(operationParts[2]);
                    registers.put(operationParts[1], value);
                    counter++;
                }
                case "add" -> {
                    long add;
                    add = getValue(operationParts[2]);
                    registers.put(operationParts[1], registers.get(operationParts[1]) + add);
                    counter++;
                }
                case "mul" -> {
                    long value;
                    try {
                        value = Long.parseLong(operationParts[2]);
                    } catch (NumberFormatException e) {
                        value = registers.get(operationParts[2]);
                    }
                    registers.put(operationParts[1],
                                  registers.get(operationParts[1]) * value);
                    counter++;
                }
                case "mod" -> {
                    long modulo;
                    modulo = getValue(operationParts[2]);
                    registers.put(operationParts[1], registers.get(operationParts[1]) % modulo);
                    counter++;
                }
                case "rcv" -> {
                    if (receiveQueue.isEmpty()) {
                        waiting = true;
                    } else {
                        Long received = receiveQueue.remove();

                        waiting = false;

                        registers.put(operationParts[1], received);

                        counter++;
                    }
                }
                case "jgz" -> {
                    long register1 = getValue(operationParts[1]);
                    if (register1 > 0) {
                        long register2;
                        register2 = getValue(operationParts[2]);
                        counter += register2;
                    } else {
                        counter++;
                    }
                }
            }
        }

        private long getValue(String register) {
            long value;
            try {
                value = Long.parseLong(register);
            } catch (NumberFormatException e) {
                value = registers.get(register);
            }
            return value;
        }

        @Override
        public String toString() {
            return "Program{" + "waiting=" + waiting + ", operations=" + operations + ", initialPvalue=" +
                   initialPvalue + ", sendQueue=" + sendQueue + ", receiveQueue=" + receiveQueue + ", sendCounter=" +
                   sendCounter + '}';
        }
    }
}
