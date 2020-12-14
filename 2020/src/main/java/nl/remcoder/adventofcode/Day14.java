package nl.remcoder.adventofcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day14 {
    private static final Pattern MEM_REGEX = Pattern.compile("mem\\[(\\d*)\\] = (\\d*)");

    public long handlePart1(Stream<String> input) {
        long[] memory = new long[64 * 1024];

        char[] mask = new char[36];

        input.forEach(string -> {
            if (string.startsWith("mask")) {
                System.arraycopy(string.substring(7).toCharArray(), 0, mask, 0, 36);
            } else {
                Matcher matcher = MEM_REGEX.matcher(string);

                if (matcher.matches()) {
                    int address = Integer.parseInt(matcher.group(1));
                    long value = Long.parseLong(matcher.group(2));

                    for (int position = 0; position < mask.length; position++) {
                        char maskChar = mask[position];

                        long masker = 1L << 35 - position;

                        if (maskChar == 'X') {
                            continue;
                        } else if (maskChar == '0') {
                            value &= ~ masker;
                        } else if (maskChar == '1') {
                            value |= masker;
                        }
                    }

                    memory[address] = value;
                } else {
                    throw new RuntimeException(string);
                }
            }
        });

        return Arrays.stream(memory)
                     .sum();
    }

    public long handlePart2(Stream<String> input) {
        Map<Long, Long> memory = new HashMap<>();

        char[] mask = new char[36];

        input.forEach(string -> {
            if (string.startsWith("mask")) {
                System.arraycopy(string.substring(7).toCharArray(), 0, mask, 0, 36);
            } else {
                Matcher matcher = MEM_REGEX.matcher(string);

                if (matcher.matches()) {
                    long address = Long.parseLong(matcher.group(1));
                    long value = Long.parseLong(matcher.group(2));

                    List<Long> addresses = new ArrayList<>();
                    addresses.add(address);

                    for (int position = 0; position < mask.length; position++) {
                        char maskChar = mask[position];

                        if (maskChar == '0') {
                            continue;
                        } else if (maskChar == '1') {
                            long masker = 1L << 35 - position;

                            for (int position1 = 0; position1 < addresses.size(); position1++) {
                                addresses.set(position1, addresses.get(position1) | masker);
                            }
                        } else if (maskChar == 'X') {
                            long masker = 1L << 35 - position;

                            int size = addresses.size();
                            for (int position1 = 0; position1 < size; position1++) {
                                addresses.set(position1, addresses.get(position1) | masker);
                                addresses.add(addresses.get(position1) & ~masker);
                            }
                        }
                    }

                    for (long memAddress : addresses) {
                        memory.put(memAddress, value);
                    }
                }
            }
        });

        return memory.values()
                     .stream()
                     .mapToLong(Long::longValue)
                     .sum();
    }
}
