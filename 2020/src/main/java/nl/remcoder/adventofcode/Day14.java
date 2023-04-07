package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day14 implements AdventOfCodeSolution<Long> {
    private static final Pattern MEM_REGEX = Pattern.compile("mem\\[(\\d*)] = (\\d*)");

    @Override
    public Long handlePart1(Stream<String> input) {
        var memory = new long[64 * 1024];

        var mask = new char[36];

        input.forEach(string -> {
            if (string.startsWith("mask")) {
                System.arraycopy(string.substring(7).toCharArray(), 0, mask, 0, 36);
            } else {
                var matcher = MEM_REGEX.matcher(string);

                if (matcher.matches()) {
                    var address = Integer.parseInt(matcher.group(1));
                    var value = Long.parseLong(matcher.group(2));

                    for (var position = 0; position < mask.length; position++) {
                        var maskChar = mask[position];

                        var masker = 1L << 35 - position;

                        if (maskChar != 'X') {
                            if (maskChar == '0') {
                                value &= ~ masker;
                            } else if (maskChar == '1') {
                                value |= masker;
                            }
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

    @Override
    public Long handlePart2(Stream<String> input) {
        var memory = new HashMap<Long, Long>();

        var mask = new char[36];

        input.forEach(string -> {
            if (string.startsWith("mask")) {
                System.arraycopy(string.substring(7).toCharArray(), 0, mask, 0, 36);
            } else {
                var matcher = MEM_REGEX.matcher(string);

                if (matcher.matches()) {
                    var address = Long.parseLong(matcher.group(1));
                    var value = Long.parseLong(matcher.group(2));

                    var addresses = new ArrayList<Long>();
                    addresses.add(address);

                    for (var position = 0; position < mask.length; position++) {
                        char maskChar = mask[position];

                        if (maskChar != '0') {
                            if (maskChar == '1') {
                                long masker = 1L << 35 - position;

                                addresses.replaceAll(position1 -> position1 | masker);
                            } else if (maskChar == 'X') {
                                var masker = 1L << 35 - position;
    
                                var size = addresses.size();
                                for (var position1 = 0; position1 < size; position1++) {
                                    addresses.set(position1, addresses.get(position1) | masker);
                                    addresses.add(addresses.get(position1) & ~masker);
                                }
                            }
                        }
                    }

                    for (var memAddress : addresses) {
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
