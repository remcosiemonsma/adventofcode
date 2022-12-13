package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Day13 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        List<String> lines = input.toList();

        int sum = 0;

        for (int i = 0; i < lines.size(); i += 3) {
            Packet left = parsePacket(lines.get(i));
            Packet right = parsePacket(lines.get(i + 1));

            if (left.compareTo(right) < 1) {
                sum += (i / 3) + 1;
            }
        }

        return sum;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        List<Packet> packets = new ArrayList<>(input.filter(Predicate.not(String::isBlank))
                                                    .map(this::parsePacket)
                                                    .toList());
        
        Packet firstDivider = parsePacket("[[2]]");
        Packet secondDivider = parsePacket("[[6]]");
        
        packets.add(firstDivider);
        packets.add(secondDivider);
        
        packets.sort(Comparator.comparing(packet -> packet));

        int firstIndex = packets.indexOf(firstDivider) + 1;
        int secondIndex = packets.indexOf(secondDivider) + 1;

        return firstIndex * secondIndex;
    }

    private Packet parsePacket(String line) {
        if (line.contains(",") || line.contains("[")) {
            List<String> parts = new ArrayList<>();

            int depthCounter = 0;

            StringBuilder partBuilder = new StringBuilder();

            char[] charArray = line.toCharArray();
            for (int i = 1; i < charArray.length - 1; i++) {
                char c = charArray[i];
                if (c == ',') {
                    if (depthCounter == 0) {
                        parts.add(partBuilder.toString());
                        partBuilder = new StringBuilder();
                    } else {
                        partBuilder.append(c);
                    }
                } else if (c == '[') {
                    depthCounter++;
                    partBuilder.append(c);
                } else if (c == ']') {
                    depthCounter--;
                    partBuilder.append(c);
                } else {
                    partBuilder.append(c);
                }
            }
            parts.add(partBuilder.toString());
            return new Packet(parts.stream()
                                   .filter(Predicate.not(String::isBlank))
                                   .map(this::parsePacket)
                                   .toList(), null);
        } else if (line.isBlank()) {
            return new Packet(null, null);
        } else {
            return new Packet(null, Integer.parseInt(line));
        }
    }

    private static class Packet implements Comparable<Packet> {
        private final List<Packet> items;
        private final Integer value;

        private Packet(List<Packet> items, Integer value) {
            this.items = items;
            this.value = value;
        }

        @Override
        public int compareTo(Packet o) {
            if (value != null && o.value != null) {
                return value.compareTo(o.value);
            }
            if (items != null && o.items != null) {
                if (this.items.isEmpty() && o.items.isEmpty()) {
                    return 0;
                }
                for (int i = 0; i < items.size(); i++) {
                    if (i >= o.items.size()) {
                        return 1;
                    }
                    Packet itemLeft = items.get(i);
                    Packet itemRight = o.items.get(i);
                    int result = itemLeft.compareTo(itemRight);
                    if (result == 0) {
                        continue;
                    }
                    return result;
                }
                return -1;
            }
            if (items == null) {
                Packet newPacket = new Packet(List.of(new Packet(null, value)), null);
                return newPacket.compareTo(o);
            }
            Packet newPacket = new Packet(List.of(new Packet(null, o.value)), null);
            return this.compareTo(newPacket);
        }

        @Override
        public String toString() {
            if (value != null) {
                return value.toString();
            } else {
                return items.toString().replaceAll(" ", "");
            }
        }
    }
}
