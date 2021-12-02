package nl.remcoder.adventofcode;

import java.util.stream.Stream;

public class Day2 {
    public int handlePart1(Stream<String> input) {
        Position position = new Position();

        input.map(this::mapToStep1)
             .forEach(step -> step.performStep(position));

        return position.depth * position.horizontal;
    }

    public int handlePart2(Stream<String> input) {
        Position2 position = new Position2();

        input.map(this::mapToStep2)
             .forEach(step -> step.performStep(position));

        return position.depth * position.horizontal;
    }

    private Step1 mapToStep1(String s) {
        String[] parts = s.split(" ");

        return switch (parts[0]) {
            case "forward" -> new ForwardStep1(Integer.parseInt(parts[1]));
            case "down" -> new DownStep1(Integer.parseInt(parts[1]));
            case "up" -> new UpStep1(Integer.parseInt(parts[1]));
            default -> throw new AssertionError("Eek!");
        };
    }

    private Step2 mapToStep2(String s) {
        String[] parts = s.split(" ");

        return switch (parts[0]) {
            case "forward" -> new ForwardStep2(Integer.parseInt(parts[1]));
            case "down" -> new DownStep2(Integer.parseInt(parts[1]));
            case "up" -> new UpStep2(Integer.parseInt(parts[1]));
            default -> throw new AssertionError("Eek!");
        };
    }

    private static class Position {
        private int horizontal;
        private int depth;
    }

    private static class Position2 {
        private int horizontal;
        private int aim;
        private int depth;
    }

    private interface Step1 {
        void performStep(Position position);
    }

    private interface Step2 {
        void performStep(Position2 position);
    }

    private record ForwardStep1(int distance) implements Step1 {
        @Override
        public void performStep(Position position) {
            position.horizontal += distance;
        }
    }

    private record ForwardStep2(int distance) implements Step2 {
        @Override
        public void performStep(Position2 position) {
            position.horizontal += distance;
            position.depth += position.aim * distance;
        }
    }

    private record DownStep1(int distance) implements Step1 {
        @Override
        public void performStep(Position position) {
            position.depth += distance;
        }
    }

    private record DownStep2(int distance) implements Step2 {
        @Override
        public void performStep(Position2 position) {
            position.aim += distance;
        }
    }

    private record UpStep1(int distance) implements Step1 {
        @Override
        public void performStep(Position position) {
            position.depth -= distance;
        }
    }

    private record UpStep2(int distance) implements Step2 {
        @Override
        public void performStep(Position2 position) {
            position.aim -= distance;
        }
    }
}
