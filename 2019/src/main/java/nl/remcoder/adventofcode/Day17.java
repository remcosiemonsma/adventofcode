package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.intcodecomputer.IntCodeComputer;
import nl.remcoder.adventofcode.intcodecomputer.ProducingQueue;
import nl.remcoder.adventofcode.library.BiAdventOfCodeSolution;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

public class Day17 implements BiAdventOfCodeSolution<Integer, Long> {
    @Override
    public Integer handlePart1(Stream<String> input) throws InterruptedException {
        var line = input.findFirst().orElseThrow(AssertionError::new);

        var opcodes = Arrays.stream(line.split(","))
                            .mapToLong(Long::parseLong)
                            .toArray();

        var outputState = new LinkedBlockingQueue<Long>();

        var intCodeComputer = new IntCodeComputer(opcodes, null, outputState);

        intCodeComputer.runProgram();

        var stringBuilder = new StringBuilder();

        while (!outputState.isEmpty()) {
            var c = (char) outputState.take().intValue();

            stringBuilder.append(c);
        }

        var grid = stringBuilder.toString();

        var intersections = findIntersections(grid);

        return intersections.stream()
                            .mapToInt(point -> point.x * point.y)
                            .sum();
    }

    @Override
    public Long handlePart2(Stream<String> input) throws Exception {
        var line = input.findFirst().orElseThrow(AssertionError::new);

        var opcodes = Arrays.stream(line.split(","))
                            .mapToLong(Long::parseLong)
                            .toArray();
        
        //Path = L,12,L,12,L,6,L,6,R,8,R,4,L,12,L,12,L,12,L,6,L,6,L,12,L6,R,12,R,8,R,8,R,4,L,12,L,12,L,12,L,6,L,6,L,12,L,6,R,12,R,8,R,8,R,4,L,12,L,12,L,12,L,6,L,6,L,12,L,6,R,12,R,8
        
        var main = "A,B,A,C,B,A,C,B,A,C\n";
        var A = "L,12,L,12,L,6,L,6\n";
        var B = "R,8,R,4,L,12\n";
        var C = "L,12,L,6,R,12,R,8\n";
        var videoFeed = "n\n";
        
        var program = main + A + B + C + videoFeed;

        var chars = new ArrayDeque<Long>(); 
        
        for (var c : program.toCharArray()) {
            chars.add((long) c);
        }
        
        opcodes[0] = 2;

        var outputState = new LinkedBlockingQueue<Long>();
        var inputState = new ProducingQueue(chars::remove);

        var intCodeComputer = new IntCodeComputer(opcodes, inputState, outputState);

        var thread = new Thread(intCodeComputer);

        thread.start();
        
        thread.join();
        
        var dust = 0L;

        while (!outputState.isEmpty()) {
            dust = outputState.take();
        }

        return dust;
    }

    private List<Point> findIntersections(String gridString) {
        var intersections = new ArrayList<Point>();

        var grid = gridString.lines()
                             .map(String::toCharArray)
                             .toArray(char[][]::new);

        for (var y = 1; y < grid.length - 1; y++) {
            for (var x = 1; x < grid[0].length - 1; x++) {
                if (grid[y][x] == '#' && grid[y][x - 1] == '#' && grid[y][x + 1] == '#' && grid[y - 1][x] == '#' &&
                    grid[y + 1][x] == '#') {
                    var point = new Point(x, y);
                    intersections.add(point);
                }
            }
        }

        return intersections;
    }

    private record Point(int x, int y) {
    }
}
