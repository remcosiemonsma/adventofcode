package nl.remcoder.adventofcode.day18;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Part2 {
    public static void main(String[] args) throws Exception {
        List<String> operations = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("day18/input").toURI()));

        Queue<Long> queue0 = new ArrayDeque<>();
        Queue<Long> queue1 = new ArrayDeque<>();

        Program program0 = new Program(operations, 0, queue1, queue0);
        Program program1 = new Program(operations, 1, queue0, queue1);

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

        System.out.println(program1.getSendCounter());
    }

    static class Program {
        boolean waiting = false;
        List<String> operations;
        long initialPvalue;
        Queue<Long> sendQueue;
        Queue<Long> receiveQueue;
        int sendCounter = 0;
        Map<String, Long> registers = new HashMap<>();
        int counter = 0;

        Program(List<String> operations, long initialPvalue, Queue<Long> sendQueue, Queue<Long> receiveQueue) {
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

        int getSendCounter() {
            return sendCounter;
        }

        boolean isWaiting() {
            return waiting;
        }

        private void performOperations() {
            String operation = operations.get(counter);

            String[] operationParts = operation.split(" ");

            switch (operationParts[0]) {
                case "snd":
                    sendQueue.add(registers.get(operationParts[1]));
                    counter++;
                    sendCounter++;
                    break;
                case "set":
                    long value;
                    value = getValue(operationParts[2]);
                    registers.put(operationParts[1], value);
                    counter++;
                    break;
                case "add":
                    long add;
                    add = getValue(operationParts[2]);
                    registers.put(operationParts[1], registers.get(operationParts[1]) + add);
                    counter++;
                    break;
                case "mul":
                    registers.put(operationParts[1],
                            registers.get(operationParts[1]) * Long.parseLong(operationParts[2]));
                    counter++;
                    break;
                case "mod":
                    long modulo;
                    modulo = getValue(operationParts[2]);
                    registers.put(operationParts[1], registers.get(operationParts[1]) % modulo);
                    counter++;
                    break;
                case "rcv":
                    if (receiveQueue.isEmpty()) {
                        waiting = true;
                    } else {
                        Long received = receiveQueue.remove();

                        waiting = false;

                        registers.put(operationParts[1], received);

                        counter++;
                    }
                    break;
                case "jgz":
                    long register1 = getValue(operationParts[1]);
                    if (register1 > 0) {
                        long register2;
                        register2 = getValue(operationParts[2]);
                        counter += register2;
                    } else {
                        counter++;
                    }
                    break;
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
