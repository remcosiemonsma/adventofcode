package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day6 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var blocks = input.map(s -> s.split("\\t"))
                          .flatMap(Arrays::stream)
                          .map(Integer::parseInt)
                          .toArray(Integer[]::new);

        var states = new ArrayList<List<Integer>>();

        var iterations = 0;

        while (!states.contains(Arrays.asList(Arrays.copyOf(blocks, blocks.length)))) {
            states.add(Arrays.asList(Arrays.copyOf(blocks, blocks.length)));

            var highestBlock = -1;
            var highestBlockPosition = -1;

            for (var i = 0; i < blocks.length; i++) {
                if (blocks[i] > highestBlock) {
                    highestBlock = blocks[i];
                    highestBlockPosition = i;
                }
            }

            blocks[highestBlockPosition] = 0;

            while (highestBlock > 0) {
                highestBlockPosition++;
                if (highestBlockPosition == blocks.length) {
                    highestBlockPosition = 0;
                }
                blocks[highestBlockPosition]++;
                highestBlock--;
            }

            iterations++;
        }

        return iterations;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var blocks = input.map(s -> s.split("\\t"))
                          .flatMap(Arrays::stream)
                          .map(Integer::parseInt)
                          .toArray(Integer[]::new);
        
        var states = new ArrayList<List<Integer>>();

        while (!states.contains(Arrays.asList(Arrays.copyOf(blocks, blocks.length)))) {
            states.add(Arrays.asList(Arrays.copyOf(blocks, blocks.length)));

            var highestBlock = -1;
            var highestBlockPosition = -1;

            for (var i = 0; i < blocks.length; i++) {
                if (blocks[i] > highestBlock) {
                    highestBlock = blocks[i];
                    highestBlockPosition = i;
                }
            }

            blocks[highestBlockPosition] = 0;

            while (highestBlock > 0) {
                highestBlockPosition++;
                if (highestBlockPosition == blocks.length) {
                    highestBlockPosition = 0;
                }
                blocks[highestBlockPosition]++;
                highestBlock--;
            }
        }

        int position = states.indexOf(Arrays.asList(Arrays.copyOf(blocks, blocks.length)));

        return states.size() - position;
    }
}
