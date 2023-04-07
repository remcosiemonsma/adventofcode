package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class Day16 implements AdventOfCodeSolution<String> {
    private String programs;

    @Override
    public String handlePart1(Stream<String> input) {
        var programs = this.programs.toCharArray();

        input.map(s -> s.split(","))
             .flatMap(Arrays::stream)
             .forEach(s -> {
                 if (s.charAt(0) == 's') {
                     var size = Integer.parseInt(s.substring(1));
                     spin(programs, size);
                 } else if (s.charAt(0) == 'x') {
                     var positions = s.substring(1).split("/");
                     var pos1 = Integer.parseInt(positions[0]);
                     var pos2 = Integer.parseInt(positions[1]);
                     swapPosition(programs, pos1, pos2);
                 } else if (s.charAt(0) == 'p') {
                     var progs = s.substring(1).toCharArray();
                     swapPrograms(programs, progs[0], progs[2]);
                 }
             });

        return new String(programs);
    }

    @Override
    public String handlePart2(Stream<String> input) {
        var danceMoves = input.map(s -> s.split(","))
                              .flatMap(Arrays::stream)
                              .toArray(String[]::new);

        var programs = this.programs.toCharArray();
        
        var foundPrograms = new ArrayList<String>();

        var stop = false;

        for (var i = 0; i < 1000000000; i++) {
            if (!stop) {
                Arrays.stream(danceMoves).forEach(s -> {
                    if (s.charAt(0) == 's') {
                        var size = Integer.parseInt(s.substring(1));
                        spin(programs, size);
                    } else if (s.charAt(0) == 'x') {
                        var positions = s.substring(1).split("/");
                        var pos1 = Integer.parseInt(positions[0]);
                        var pos2 = Integer.parseInt(positions[1]);
                        swapPosition(programs, pos1, pos2);
                    } else if (s.charAt(0) == 'p') {
                        var progs = s.substring(1).toCharArray();
                        swapPrograms(programs, progs[0], progs[2]);
                    }
                });

                if (!foundPrograms.contains(new String(programs))) {
                    foundPrograms.add(new String(programs));
                } else {
                    stop = true;
                }
            }
        }

        return foundPrograms.get((1000000000 % foundPrograms.size()) - 1);
    }

    public void setPrograms(String programs) {
        this.programs = programs;
    }

    private void spin(char[] programs, int size) {
        var tmpend = new char[size];
        var tmpstart = new char[programs.length - size];

        System.arraycopy(programs, programs.length - size, tmpend, 0, size);
        System.arraycopy(programs, 0, tmpstart, 0, programs.length - size);

        System.arraycopy(tmpend, 0, programs, 0, tmpend.length);
        System.arraycopy(tmpstart, 0, programs, size, tmpstart.length);
    }

    private void swapPosition(char[] programs, int pos1, int pos2) {
        var tmp = programs[pos1];
        programs[pos1] = programs[pos2];
        programs[pos2] = tmp;
    }

    private void swapPrograms(char[] programs, char prog1, char prog2) {
        var pos1 = -1;
        var pos2 = -1;

        for (var i = 0; i < programs.length; i++) {
            if (programs[i] == prog1) {
                pos1 = i;
            }
            if (programs[i] == prog2) {
                pos2 = i;
            }
        }

        swapPosition(programs, pos1, pos2);
    }
}
