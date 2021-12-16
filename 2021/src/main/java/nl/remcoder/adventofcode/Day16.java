package nl.remcoder.adventofcode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Day16 {
    public int handlePart1(Stream<String> input) {
        char[] binaryData = parseInput(input);

        List<Packet> packets = new ArrayList<>();

        readPackets(binaryData, 0, packets);

        return packets.get(0).getTotalVersion();
    }

    public long handlePart2(Stream<String> input) {
        char[] binaryData = parseInput(input);

        List<Packet> packets = new ArrayList<>();

        readPackets(binaryData, 0, packets);

        return packets.get(0).calculateResult();
    }

    private char[] parseInput(Stream<String> input) {
        return input.flatMapToInt(String::chars)
                    .mapToObj(i -> transformHexCharToBinaryString((char) i))
                    .reduce(String::concat)
                    .map(String::toCharArray)
                    .orElse(new char[0]);
    }

    private int readPackets(final char[] packetData, final int position, final List<Packet> packets) {
        int currentPosition = position;

        int version = Integer.parseInt(new String(
                new char[]{packetData[currentPosition++], packetData[currentPosition++],
                           packetData[currentPosition++]}), 2);
        int typeId = Integer.parseInt(new String(
                new char[]{packetData[currentPosition++], packetData[currentPosition++],
                           packetData[currentPosition++]}), 2);

        ArrayList<Packet> subPackets = new ArrayList<>();
        Packet packet = new Packet(version, typeId, subPackets);

        packets.add(packet);

        if (isLiteralPacket(typeId)) {
            currentPosition = readLiteralPacket(packetData, currentPosition, packet);
        } else {
            char lengthId = packetData[currentPosition++];

            if (lengthId == '0') {
                currentPosition = readFixedLengthPackets(packetData, currentPosition, subPackets);
            } else {
                currentPosition = readFixedAmountOfPackets(packetData, currentPosition, subPackets);
            }
        }

        return currentPosition - position;
    }

    private int readFixedAmountOfPackets(char[] packetData, int currentPosition, ArrayList<Packet> subPackets) {
        char[] lengthdata = new char[11];

        System.arraycopy(packetData, currentPosition, lengthdata, 0, 11);
        currentPosition += 11;

        int length = Integer.parseInt(new String(lengthdata), 2);

        while (subPackets.size() < length) {
            currentPosition += readPackets(packetData, currentPosition, subPackets);
        }
        return currentPosition;
    }

    private int readFixedLengthPackets(char[] packetData, int currentPosition, ArrayList<Packet> subPackets) {
        char[] lengthdata = new char[15];

        System.arraycopy(packetData, currentPosition, lengthdata, 0, 15);
        currentPosition += 15;

        int length = Integer.parseInt(new String(lengthdata), 2);

        int end = currentPosition + length;

        while (currentPosition < end) {
            currentPosition += readPackets(packetData, currentPosition, subPackets);
        }
        return currentPosition;
    }

    private int readLiteralPacket(char[] packetData, int currentPosition, Packet packet) {
        boolean moreData = true;

        StringBuilder stringBuilder = new StringBuilder();

        while (moreData) {
            moreData = packetData[currentPosition++] == '1';

            stringBuilder.append(packetData, currentPosition, 4);
            currentPosition += 4;
        }

        packet.number = Long.parseLong(stringBuilder.toString(), 2);
        return currentPosition;
    }

    private String transformHexCharToBinaryString(char hex) {
        return switch (hex) {
            case '0' -> "0000";
            case '1' -> "0001";
            case '2' -> "0010";
            case '3' -> "0011";
            case '4' -> "0100";
            case '5' -> "0101";
            case '6' -> "0110";
            case '7' -> "0111";
            case '8' -> "1000";
            case '9' -> "1001";
            case 'A' -> "1010";
            case 'B' -> "1011";
            case 'C' -> "1100";
            case 'D' -> "1101";
            case 'E' -> "1110";
            case 'F' -> "1111";
            default -> throw new AssertionError("Eek!");
        };
    }

    private boolean isLiteralPacket(int typeId) {
        return typeId == 4;
    }

    private static class Packet {
        private final int version;
        private final int type;
        private long number;
        private final List<Packet> subPackets;

        public Packet(int version, int type, List<Packet> subPackets) {
            this.version = version;
            this.type = type;
            this.subPackets = subPackets;
        }

        public int getTotalVersion() {
            return version + subPackets.stream().mapToInt(Packet::getTotalVersion).sum();
        }

        private long calculateResult() {
            return switch (type) {
                case 0 -> subPackets.stream()
                                    .mapToLong(Packet::calculateResult)
                                    .sum();
                case 1 -> subPackets.stream()
                                    .mapToLong(Packet::calculateResult)
                                    .reduce((l1, l2) -> l1 * l2)
                                    .orElseThrow(() -> new AssertionError("Ook!"));
                case 2 -> subPackets.stream()
                                    .mapToLong(Packet::calculateResult)
                                    .min()
                                    .orElseThrow(() -> new AssertionError("Ook!"));
                case 3 -> subPackets.stream()
                                    .mapToLong(Packet::calculateResult)
                                    .max()
                                    .orElseThrow(() -> new AssertionError("Ook!"));
                case 4 -> number;
                case 5 -> subPackets.get(0).calculateResult() > subPackets.get(1).calculateResult() ? 1 : 0;
                case 6 -> subPackets.get(0).calculateResult() < subPackets.get(1).calculateResult() ? 1 : 0;
                case 7 -> subPackets.get(0).calculateResult() == subPackets.get(1).calculateResult() ? 1 : 0;
                default -> throw new AssertionError("Ook!");
            };
        }

        @Override
        public String toString() {
            return "Packet{" +
                   "version=" + version +
                   ", type=" + type +
                   ", number=" + number +
                   ", subPackets=" + subPackets +
                   '}';
        }

        public void prettyPrint(int depth) {
            for (int i = 0; i < depth; i++) {
                System.out.print(" ");
            }
            switch (type) {
                case 0 -> {
                    System.out.println("+ = " + calculateResult());
                    subPackets.forEach(packet -> packet.prettyPrint(depth + 1));
                }
                case 1 -> {
                    System.out.println("* = " + calculateResult());
                    subPackets.forEach(packet -> packet.prettyPrint(depth + 1));
                }
                case 2 -> {
                    System.out.println("min = " + calculateResult());
                    subPackets.forEach(packet -> packet.prettyPrint(depth + 1));
                }
                case 3 -> {
                    System.out.println("max = " + calculateResult());
                    subPackets.forEach(packet -> packet.prettyPrint(depth + 1));
                }
                case 4 -> System.out.println("value = " + number);
                case 5 -> {
                    System.out.println("> = " + calculateResult());
                    subPackets.forEach(packet -> packet.prettyPrint(depth + 1));
                }
                case 6 -> {
                    System.out.println("< = " + calculateResult());
                    subPackets.forEach(packet -> packet.prettyPrint(depth + 1));
                }
                case 7 -> {
                    System.out.println("== = " + calculateResult());
                    subPackets.forEach(packet -> packet.prettyPrint(depth + 1));
                }
            }
        }
    }
}
