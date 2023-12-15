package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.Arrays;
import java.util.stream.Stream;

public class Day15 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        return input.flatMap(line -> Arrays.stream(line.split(",")))
                    .mapToInt(this::calculateHash)
                    .sum();
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var boxes = new Lens[256][];

        Arrays.fill(boxes, new Lens[0]);

        var instructions = input.findFirst()
                                .orElseThrow(() -> new AssertionError("Eek!"))
                                .split(",");

        for (var instruction : instructions) {
            processInstruction(instruction, boxes);
        }

        return calculateFocalPower(boxes);
    }

    private int calculateFocalPower(Lens[][] boxes) {
        var focalPower = 0;

        for (var index = 0; index < boxes.length; index++) {
            var box = boxes[index];
            if (box.length > 0) {
                focalPower += calculateFocalPower(index, box);
            }
        }

        return focalPower;
    }

    private int calculateFocalPower(int index, Lens[] box) {
        var focalPower = 0;

        for (var lensIndex = 0; lensIndex < box.length; lensIndex++) {
            int focalPowerLens = (index + 1) * (lensIndex + 1) * box[lensIndex].strength();
            focalPower += focalPowerLens;
        }

        return focalPower;
    }

    private void processInstruction(String instruction, Lens[][] boxes) {
        var split = instruction.split("[=-]");

        var boxId = calculateHash(split[0]);


        var box = boxes[boxId];
        if (split.length == 1) {
            boxes[boxId] = removeLens(split[0], box);
        } else {
            boxes[boxId] = putLens(split[0], Integer.parseInt(split[1]), box);
        }
    }

    private Lens[] putLens(String label, int strength, Lens[] box) {
        Result result = findLens(label, box);

        if (result.lensFound()) {
            box[result.index()] = new Lens(label, strength);
            return box;
        } else {
            var newBox = new Lens[box.length + 1];
            System.arraycopy(box, 0, newBox, 0, box.length);
            newBox[newBox.length - 1] = new Lens(label, strength);
            return newBox;
        }
    }

    private Lens[] removeLens(String label, Lens[] box) {
        Result result = findLens(label, box);

        if (result.lensFound()) {
            var newBox = new Lens[box.length - 1];

            System.arraycopy(box, 0, newBox, 0, result.index());
            System.arraycopy(box, result.index() + 1, newBox, result.index(), box.length - result.index() - 1);

            return newBox;
        } else {
            return box;
        }
    }

    private Result findLens(String label, Lens[] box) {
        var lensFound = false;
        int index = 0;
        for (; index < box.length; index++) {
            var lens = box[index];
            if (lens.label().equals(label)) {
                lensFound = true;
                break;
            }
        }
        return new Result(lensFound, index);
    }

    private int calculateHash(String line) {
        var hash = 0;

        for (var c : line.toCharArray()) {
            hash += c;
            hash *= 17;
            hash %= 256;
        }

        return hash;
    }

    private record Result(boolean lensFound, int index) {}

    private record Lens(String label, int strength) {}
}
