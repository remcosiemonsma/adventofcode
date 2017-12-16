package nl.remcoder.adventofcode.day16;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Part1 {
    public static void main(String[] args) throws Exception {
        char[] programs = new char[] {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p'};

        Arrays.stream(Files.readAllLines(Paths.get(ClassLoader.getSystemResource("day16/input").toURI())).get(0).split(",")).forEach(s -> {
            if (s.charAt(0) == 's') {
                int size = Integer.parseInt(s.substring(1));
                spin(programs, size);
            } else if (s.charAt(0) == 'x') {
                String[] positions = s.substring(1).split("/");
                int pos1 = Integer.parseInt(positions[0]);
                int pos2 = Integer.parseInt(positions[1]);
                swapPosition(programs, pos1, pos2);
            } else if (s.charAt(0) == 'p') {
                char[] progs = s.substring(1).toCharArray();
                swapPrograms(programs, progs[0], progs[2]);
            }
        });


        System.out.println(new String(programs));
    }

    private static char[] spin (char[] programs, int size) {
        char[] tmpend = new char[size];
        char[] tmpstart = new char[programs.length - size];

        System.arraycopy(programs, programs.length - size, tmpend, 0, size);
        System.arraycopy(programs, 0, tmpstart, 0, programs.length - size);

        System.arraycopy(tmpend, 0, programs, 0, tmpend.length);
        System.arraycopy(tmpstart, 0, programs, size, tmpstart.length);

        return programs;
    }

    private static char[] swapPosition(char[] programs, int pos1, int pos2) {
        char tmp = programs[pos1];
        programs[pos1] = programs[pos2];
        programs[pos2] = tmp;

        return programs;
    }

    private static char[] swapPrograms(char[] programs, char prog1, char prog2) {
        int pos1 = -1;
        int pos2 = -1;

        for (int i = 0; i < programs.length; i++) {
            if (programs[i] == prog1) {
                pos1 = i;
            }
            if (programs[i] == prog2) {
                pos2 = i;
            }
        }

        swapPosition(programs, pos1, pos2);

        return programs;
    }
}
