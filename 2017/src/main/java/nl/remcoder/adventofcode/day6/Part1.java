package nl.remcoder.adventofcode.day6;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Part1 {
    public static void main(String[] args) throws Exception {
        String line = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("day6/input").toURI())).get(0);

        String[] initialBlocks = line.split("\\t");

        Integer[] blocks = new Integer[initialBlocks.length];

        for (int i = 0; i < initialBlocks.length; i++) {
            blocks[i] = Integer.parseInt(initialBlocks[i]);
        }

        System.out.println(Arrays.toString(Arrays.copyOf(blocks, blocks.length)));

        List<List<Integer>> states = new ArrayList<>();

        int iterations = 0;

        while (!states.contains(Arrays.asList(Arrays.copyOf(blocks, blocks.length)))) {
            states.add(Arrays.asList(Arrays.copyOf(blocks, blocks.length)));

            int highestBlock = -1;
            int highestBlockPosition = -1;

            for (int i = 0; i < blocks.length; i++) {
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

            System.out.println(Arrays.toString(blocks));

            iterations++;
        }

        System.out.println(iterations);
    }
}
