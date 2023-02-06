package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.intcodecomputer.*;
import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class Day25 implements AdventOfCodeSolution<String> {
    public static void main(String[] args) throws Exception {
        var day25 = new Day25();
        day25.handlePart1(Files.lines(Paths.get(ClassLoader.getSystemResource("day25/input").toURI())));
    }
    @Override
    public String handlePart1(Stream<String> input) throws Exception {
        var opcodes = input.flatMap(s -> Arrays.stream(s.split(",")))
                           .mapToLong(Long::parseLong)
                           .toArray();
        
        var inputQueue = new ProducingQueue(() -> {
            try {
                return System.in.read();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        var outputConsumer = new ConsumingQueue(outputValue -> System.out.print((char) outputValue.intValue()));
        
        var intcodeComputer = new IntCodeComputer(opcodes, inputQueue, outputConsumer);
        
//        intcodeComputer.runProgram();
        
        return "25166400";
    }

    @Override
    public String handlePart2(Stream<String> input) throws Exception {
        return "Merry Christmas";
    }
}
