package nl.remcoder.adventofcode.day23;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Part1 {
    public static void main(String[] args) throws Exception {
        String[] operations = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("day23/input").toURI())).toArray(new String[0]);

        Map<String, Long> registers = new HashMap<>();

        registers.put("a", 0L);
        registers.put("b", 0L);
        registers.put("c", 0L);
        registers.put("d", 0L);
        registers.put("e", 0L);
        registers.put("f", 0L);
        registers.put("g", 0L);
        registers.put("h", 0L);

        int pc = 0;

        int timesmul = 0;

        while (0 <= pc && pc < operations.length) {
            String[] opparts = operations[pc].split(" ");

            switch (opparts[0]) {
                case "set":
                    registers.put(opparts[1], getValue(opparts[2], registers));
                    pc++;
                    break;
                case "sub":
                    registers.put(opparts[1], registers.get(opparts[1]) - getValue(opparts[2], registers));
                    pc++;
                    break;
                case "mul":
                    registers.put(opparts[1], registers.get(opparts[1]) * getValue(opparts[2], registers));
                    pc++;
                    timesmul++;
                    break;
                case "jnz":
                    if (getValue(opparts[1], registers) != 0) {
                        pc += getValue(opparts[2], registers);
                    } else {
                        pc++;
                    }
                    break;
            }
        }

        System.out.println(timesmul);
    }

    private static long getValue(String register, Map<String, Long> registers) {
        long value;
        try {
            value = Long.parseLong(register);
        } catch (NumberFormatException e) {
            value = registers.get(register);
        }
        return value;
    }
}
