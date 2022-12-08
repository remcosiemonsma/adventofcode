package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.GridFactory;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.model.Grid;

import java.util.stream.Stream;

public class Day8 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) {
        Grid<Integer> trees = GridFactory.createNumberedGridFromInput(input);
        
        Grid<Boolean> visibilityGrid = new Grid<>(trees.getStartx(),
                                                  trees.getStarty(),
                                                  trees.getEndx(),
                                                  trees.getEndy());

        for (int x = trees.getStartx() + 1; x < trees.getEndx(); x++) {
            for (int y = trees.getStarty() + 1; y < trees.getEndy(); y++) {
                Coordinate coordinate = new Coordinate(x, y);
                if (isTreeVisible(coordinate, trees)) {
                    visibilityGrid.set(coordinate, true);
                }
            }
        }

        for (int x = visibilityGrid.getStartx(); x <= visibilityGrid.getEndx(); x++) {
            visibilityGrid.set(new Coordinate(x, visibilityGrid.getStarty()), true);
            visibilityGrid.set(new Coordinate(x, visibilityGrid.getEndy()), true);
        }

        for (int y = visibilityGrid.getStarty(); y <= visibilityGrid.getEndy(); y++) {
            visibilityGrid.set(new Coordinate(visibilityGrid.getStartx(), y), true);
            visibilityGrid.set(new Coordinate(visibilityGrid.getEndx(), y), true);
        }

        return visibilityGrid.countState(true);
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        Grid<Integer> trees = GridFactory.createNumberedGridFromInput(input);
        
        long bestScenicScore = 0;

        for (int x = trees.getStartx() + 1; x < trees.getEndx(); x++) {
            for (int y = trees.getStarty() + 1; y < trees.getEndy(); y++) {
                Coordinate coordinate = new Coordinate(x, y);
                long scenicScore = getScenicScore(coordinate, trees);
                
                if (scenicScore > bestScenicScore) {
                    bestScenicScore = scenicScore;
                }
            }
        }
        
        return bestScenicScore;
    }

    private boolean isTreeVisible(Coordinate position, Grid<Integer> trees) {
        return isVisibleNorth(position, trees) ||
               isVisibleWest(position, trees) ||
               isVisibleSouth(position, trees) ||
               isVisibleEast(position, trees);
    }

    private boolean isVisibleNorth(Coordinate position, Grid<Integer> trees) {
        int height = trees.get(position);

        boolean visible = true;
        Coordinate north = position.above();
        Integer treeNorth = trees.get(north);

        while (treeNorth != null) {
            if (treeNorth >= height) {
                visible = false;
                break;
            }
            north = north.above();
            treeNorth = trees.get(north);
        }
        return visible;
    }

    private boolean isVisibleWest(Coordinate position, Grid<Integer> trees) {
        int height = trees.get(position);

        boolean visible = true;
        Coordinate west = position.left();
        Integer treeWest = trees.get(west);

        while (treeWest != null) {
            if (treeWest >= height) {
                visible = false;
                break;
            }
            west = west.left();
            treeWest = trees.get(west);
        }
        return visible;
    }

    private boolean isVisibleSouth(Coordinate position, Grid<Integer> trees) {
        int height = trees.get(position);

        boolean visible = true;
        Coordinate south = position.below();
        Integer treeSouth = trees.get(south);

        while (treeSouth != null) {
            if (treeSouth >= height) {
                visible = false;
                break;
            }
            south = south.below();
            treeSouth = trees.get(south);
        }
        return visible;
    }

    private boolean isVisibleEast(Coordinate position, Grid<Integer> trees) {
        int height = trees.get(position);

        boolean visible = true;
        Coordinate east = position.right();
        Integer treeEast = trees.get(east);

        while (treeEast != null) {
            if (treeEast >= height) {
                visible = false;
                break;
            }
            east = east.right();
            treeEast = trees.get(east);
        }
        return visible;
    }

    private long getScenicScore(Coordinate position, Grid<Integer> trees) {
        
        return getScenicScoreNorth(position, trees) *
               getScenicScoreWest(position, trees) *
               getScenicScoreSouth(position, trees) *
               getScenicScoreEast(position, trees);
    }

    private long getScenicScoreNorth(Coordinate position, Grid<Integer> trees) {
        int height = trees.get(position);
        long scenicScore = 0;

        Coordinate north = position.above();
        Integer treeNorth = trees.get(north);

        while (treeNorth != null) {
            scenicScore++;
            if (treeNorth >= height) {
                break;
            }
            north = north.above();
            treeNorth = trees.get(north);
        }
        return scenicScore;
    }

    private long getScenicScoreWest(Coordinate position, Grid<Integer> trees) {
        int height = trees.get(position);
        long scenicScore = 0;

        Coordinate west = position.left();
        Integer treeWest = trees.get(west);

        while (treeWest != null) {
            scenicScore++;
            if (treeWest >= height) {
                break;
            }
            west = west.left();
            treeWest = trees.get(west);
        }
        return scenicScore;
    }
    
    private long getScenicScoreSouth(Coordinate position, Grid<Integer> trees) {
        int height = trees.get(position);
        long scenicScore = 0;

        Coordinate south = position.below();
        Integer treeSouth = trees.get(south);

        while (treeSouth != null) {
            scenicScore++;
            if (treeSouth >= height) {
                break;
            }
            south = south.below();
            treeSouth = trees.get(south);
        }
        return scenicScore;
    }
    
    private long getScenicScoreEast(Coordinate position, Grid<Integer> trees) {
        int height = trees.get(position);
        long scenicScore = 0;

        Coordinate east = position.right();
        Integer treeEast = trees.get(east);

        while (treeEast != null) {
            scenicScore++;
            if (treeEast >= height) {
                break;
            }
            east = east.right();
            treeEast = trees.get(east);
        }
        return scenicScore;
    }
}
