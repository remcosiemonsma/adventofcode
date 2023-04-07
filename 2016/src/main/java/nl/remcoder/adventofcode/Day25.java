package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.asembunny.CPU;
import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.stream.Stream;

public class Day25 implements AdventOfCodeSolution<Integer> {

    @Override
    public Integer handlePart1(Stream<String> input) {
        var instructions = input.map(s -> s.split(" ")).toArray(String[][]::new);

        var cycleFound = false;
        
        var expected = "0101010101";
        
        var number = 0;
        while (!cycleFound) {
            CPU cpu = new CPU();
            var output = cpu.getOutput();
            cpu.setRegistera(number);

            StringBuilder result = new StringBuilder();

            Runnable runnable = () -> cpu.performOperations(instructions);
            Thread thread = new Thread(runnable);
            thread.start();
            
            while (result.length() < 10 && thread.isAlive()) {
                try {
                    result.append(output.take());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            
            cpu.stop();

            if (expected.contentEquals(result)) {
                cycleFound = true;
            } else {
                number++;
            }
        }
        
        return number;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        System.out.println("Merry Christmas!");
        return null;
    }
}
