package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.CoordinateNDimensional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day25 implements AdventOfCodeSolution<Integer> {

    @Override
    public Integer handlePart1(Stream<String> input) {
        var coordinates = new ArrayList<>(input.map(s -> s.split(","))
                                               .map(s -> Arrays.stream(s)
                                                               .mapToInt(Integer::parseInt)
                                                               .toArray())
                                               .map(CoordinateNDimensional::new)
                                               .toList());

        var constellations = 0;
        
        while (!coordinates.isEmpty()) {
            var constellation = findNextConstellation(coordinates);

            constellations++;
        }

        return constellations;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        System.out.println("Merry Christmas!");
        return null;
    }
    
    private List<CoordinateNDimensional> findNextConstellation(List<CoordinateNDimensional> coordinates) {
        var constellation = new ArrayList<CoordinateNDimensional>();
        
        constellation.add(coordinates.remove(0));
        
        var added = true;
        
        while (added) {
            var sizebefore = coordinates.size();
            for (CoordinateNDimensional coordinate : coordinates) {
                if (constellation.stream().anyMatch(target -> target.getDistanceTo(coordinate) <= 3)) {
                    constellation.add(coordinate);
                }
            }
            coordinates.removeAll(constellation);
            var sizeafter = coordinates.size();
            
            added = sizebefore != sizeafter;
        }
        coordinates.removeAll(constellation);
        
        return constellation;
    }
}
