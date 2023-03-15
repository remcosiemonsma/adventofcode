package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.intcodecomputer.*;
import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Stream;

public class Day23 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) throws Exception {
        var opcodes = input.flatMap(s -> Arrays.stream(s.split(",")))
                           .mapToLong(Long::parseLong)
                           .toArray();

        var computerMap = new HashMap<Integer, Computer>();

        for (var i = 0; i < 50; i++) {
            var computer = new Computer(opcodes, computerMap, i);
            computerMap.put(i, computer);
        }
        var target255 = new ArrayBlockingQueue<Long>(1);

        computerMap.put(255, new Computer255(opcodes, computerMap, target255));

        computerMap.values().forEach(Computer::start);

        var result = target255.take();

        computerMap.values().forEach(Computer::stop);

        return result;
    }

    @Override
    public Long handlePart2(Stream<String> input) throws Exception {
        var opcodes = input.flatMap(s -> Arrays.stream(s.split(",")))
                           .mapToLong(Long::parseLong)
                           .toArray();

        var computerMap = new HashMap<Integer, Computer>();

        for (var i = 0; i < 50; i++) {
            var computer = new Computer(opcodes, computerMap, i);
            computerMap.put(i, computer);
        }
        var targetNAT = new ArrayBlockingQueue<Long>(1);

        computerMap.put(255, new NAT(opcodes, computerMap, targetNAT));

        computerMap.values().forEach(Computer::start);

        var result = targetNAT.take();

        computerMap.values().forEach(Computer::stop);

        return result;
    }

    private static class Computer {
        private final Queue<Packet> input = new ArrayDeque<>();
        private final IntCodeComputer intCodeComputer;
        private final Map<Integer, Computer> computerMap;
        private Long nextTarget;
        private Long nextX;
        private Long nextY;
        private boolean readingX = false;
        private boolean tryingToReceive = false;

        private Computer(long[] opcodes, Map<Integer, Computer> computerMap, int i) {
            input.add(new Packet(0, i));
            this.computerMap = computerMap;
            intCodeComputer = new IntCodeComputer(opcodes, new ProducingQueue(
                    new MyInputProducer()), new ConsumingQueue(new MyOutputConsumer()));
        }

        public void acceptValue(Packet value) {
            input.add(value);
        }

        private void sendValues() {
//            System.out.printf("Sending values x: %d, y: %d, to computer: %d%n", nextX, nextY, nextTarget);
            computerMap.get(nextTarget.intValue()).acceptValue(new Packet(nextX, nextY));
            nextTarget = null;
            nextX = null;
            nextY = null;
        }

        public void start() {
            (new Thread(intCodeComputer)).start();
        }

        public void stop() {
            intCodeComputer.stop();
        }

        private class MyInputProducer implements InputProducer {
            @Override
            public long produceLongValue() {
                if (input.isEmpty()) {
                    tryingToReceive = true;
                    return -1;
                } else {
                    tryingToReceive = false;
                    if (readingX) {
                        readingX = false;
                        return input.peek().x;
                    } else {
                        readingX = true;
                        return input.remove().y;
                    }
                }
            }
        }

        private class MyOutputConsumer implements OutputConsumer {
            @Override
            public void consumeLongValue(Long outputValue) {
                if (nextTarget == null) {
                    nextTarget = outputValue;
                } else if (nextX == null) {
                    nextX = outputValue;
                } else if (nextY == null) {
                    nextY = outputValue;
                    sendValues();
                }
            }
        }

        public boolean isIdle() {
            return tryingToReceive && input.isEmpty();
        }
    }

    private static class Computer255 extends Computer {
        private final BlockingQueue<Long> output;

        private Computer255(long[] opcodes, Map<Integer, Computer> computerMap, BlockingQueue<Long> output) {
            super(opcodes, computerMap, 255);
            this.output = output;
        }

        @Override
        public void acceptValue(Packet value) {
            try {
                output.put(value.y);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void start() {
        }

        @Override
        public void stop() {
        }
    }

    private static class NAT extends Computer {
        private final BlockingQueue<Long> output;
        private final Map<Integer, Computer> computerMap;
        private Packet lastPacket;
        private Packet lastSentPacket;
        private boolean isRunning = true;

        private NAT(long[] opcodes, Map<Integer, Computer> computerMap, BlockingQueue<Long> output) {
            super(opcodes, computerMap, 255);
            this.computerMap = computerMap;
            this.output = output;
            var thread = new Thread(new IdleChecker());
            thread.start();
        }

        @Override
        public void acceptValue(Packet value) {
            lastPacket = value;
        }

        @Override
        public void start() {}

        @Override
        public void stop() {
            isRunning = false;
        }

        @Override
        public boolean isIdle() {
            return true;
        }

        private class IdleChecker implements Runnable {
            @Override
            public void run() {
                while (isRunning) {
                    if (lastPacket != null && computerMap.values().stream().allMatch(Computer::isIdle)) {
                        if (lastSentPacket != null && lastSentPacket.y == lastPacket.y) {
                            try {
                                output.put(lastPacket.y);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            lastSentPacket = lastPacket;
                            computerMap.get(0).acceptValue(lastPacket);
                        }
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    private record Packet(long x, long y) {
    }
}
