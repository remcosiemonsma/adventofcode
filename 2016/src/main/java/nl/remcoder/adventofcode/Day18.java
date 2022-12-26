package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Grid;

import java.util.ArrayList;
import java.util.stream.Stream;

public class Day18 implements AdventOfCodeSolution<Integer> {
    private int rows;
    
    @Override
    public Integer handlePart1(Stream<String> input) {
        var traps = input.findFirst()
                         .map(String::toCharArray)
                         .map(this::toTrapArray)
                         .orElseThrow(() -> new AssertionError("Eek!"));
        
        var grid = new Grid<>(new Boolean[][] {traps});
        
        for (var y = 1; y < rows; y++) {
            for (var x = 0; x < traps.length; x++) {
                if ((Boolean.TRUE.equals(grid.get(x - 1, y - 1)) && !Boolean.TRUE.equals(grid.get(x + 1, y - 1))) ||
                    (!Boolean.TRUE.equals(grid.get(x - 1, y - 1)) && Boolean.TRUE.equals(grid.get(x + 1, y - 1)))) {
                    grid.set(x, y, Boolean.TRUE);
                } else {
                    grid.set(x, y, Boolean.FALSE);
                }
            }
        }

        return (int) grid.countElements(Boolean.FALSE);
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var traps = input.findFirst()
                         .map(String::toCharArray)
                         .map(this::toTrapArray)
                         .orElseThrow(() -> new AssertionError("Eek!"));

        var grid = new Grid<>(new Boolean[][] {traps});
        
        for (var y = 1; y < 400000; y++) {
            if (y % 1000 == 0) {
                System.out.println(y);
            }
            for (var x = 0; x < traps.length; x++) {
                if ((Boolean.TRUE.equals(grid.get(x - 1, y - 1)) && !Boolean.TRUE.equals(grid.get(x + 1, y - 1))) ||
                    (!Boolean.TRUE.equals(grid.get(x - 1, y - 1)) && Boolean.TRUE.equals(grid.get(x + 1, y - 1)))) {
                    grid.set(x, y, Boolean.TRUE);
                } else {
                    grid.set(x, y, Boolean.FALSE);
                }
            }
        }

        return (int) grid.countElements(Boolean.FALSE);    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    private Boolean[] toTrapArray(char[] chars) {
        var traps = new Boolean[chars.length];
        
        for (var i = 0; i < chars.length; i++) {
            traps[i] = chars[i] == '^';
        }
        
        return traps;
    }
}
