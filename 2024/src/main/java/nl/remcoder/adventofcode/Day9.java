package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

public class Day9 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) {
        var blocks = readBlocks(input);

        compressDisk(blocks);

        return calculateChecksum(blocks);
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var blocks = readBlocks(input);

        compressDiskPart2(blocks);

        return calculateChecksum(blocks);
    }

    private ArrayList<Integer> readBlocks(Stream<String> input) {
        var line = input.findAny().orElseThrow(() -> new AssertionError("Eek!"));

        var blocks = new ArrayList<Integer>();

        for (var i = 0; i < line.length(); i++) {
            var c = line.charAt(i);

            var size = c - '0';

            for (int j = 0; j < size; j++) {
                if (i % 2 == 0) {
                    blocks.add(i / 2);
                } else {
                    blocks.add(null);
                }
            }
        }
        return blocks;
    }

    private void compressDisk(List<Integer> blocks) {
        for (var i = blocks.size() - 1; i >= 0; i--) {
            var block = blocks.get(i);
            if (block != null) {
                var nextAvailableBlock = blocks.indexOf(null);
                if (nextAvailableBlock > i) {
                    break;
                }
                blocks.set(nextAvailableBlock, block);
                blocks.set(i, null);
            }
        }
    }

    private void compressDiskPart2(List<Integer> blocks) {
        var processedBlocks = new HashSet<Integer>();
        for (var i = blocks.size() - 1; i >= 0; i--) {
            var block = blocks.get(i);
            if (processedBlocks.contains(block)) {
                continue;
            }
            processedBlocks.add(block);
            if (block != null) {
                var start = blocks.indexOf(block);
                var size = (i - start) + 1;
                i = start;
                int newPosition = findAvailableSize(blocks, size, start);
                if (newPosition == i) {
                    continue;
                }
                for (int j = newPosition; j < newPosition + size; j++) {
                    blocks.set(j, block);
                }
                for (int j = i; j < i + size; j++) {
                    blocks.set(j, null);
                }
            }
        }
    }

    private int findAvailableSize(List<Integer> blocks, int size, int end) {
        var foundSize = 0;
        var firstIndex = -1;
        for (int i = 0; i < end; i++) {
            if (blocks.get(i) == null) {
                foundSize++;
                if (firstIndex == -1) {
                    firstIndex = i;
                }
            } else {
                foundSize = 0;
                firstIndex = -1;
            }
            if (foundSize == size) {
                return firstIndex;
            }
        }
        return end;
    }

    private long calculateChecksum(List<Integer> blocks) {
        var checksum = 0L;
        for (var i = 0; i < blocks.size(); i++) {
            if (blocks.get(i) != null) {
                checksum += (long) blocks.get(i) * i;
            }
        }
        return checksum;
    }

    private String blocksToString(List<Integer> blocks) {
        var sb = new StringBuilder();
        for (var block : blocks) {
            if (block == null) {
                sb.append('.');
            } else {
                sb.append(block);
            }
        }
        return sb.toString();
    }
}
