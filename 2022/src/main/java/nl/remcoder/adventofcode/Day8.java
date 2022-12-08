package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.GridFactory;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.model.Grid;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class Day8 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) {
        long start = System.currentTimeMillis();

        Grid<Integer> trees = GridFactory.createNumberedGridFromInput(input);

        long end = System.currentTimeMillis();

        System.out.printf("Took %d millis to parse grid%n", end - start);

        Set<Coordinate> visibleTrees = determineVisibleTrees(trees);

        return (long) visibleTrees.size();
    }

    private Set<Coordinate> determineVisibleTrees(Grid<Integer> trees) {
        Set<Coordinate> visibleTrees = new HashSet<>();

        getVisibleTreesVertical(trees, visibleTrees);
        determineVisibleTreesHorizontal(trees, visibleTrees);
        
        return visibleTrees;
    }

    private void determineVisibleTreesHorizontal(Grid<Integer> trees, Set<Coordinate> visibleTrees) {
        for (int y = trees.getStarty(); y <= trees.getEndy(); y++) {
            determineVisibleTreesTopBottom(trees, visibleTrees, y);
            determineVisibleTreesBottomTop(trees, visibleTrees, y);
        }
    }

    private void getVisibleTreesVertical(Grid<Integer> trees, Set<Coordinate> visibleTrees) {
        for (int x = trees.getStartx(); x <= trees.getEndx(); x++) {
            determineVisibleTreesLeftRight(trees, visibleTrees, x);
            determineVisibleTreesRightLeft(trees, visibleTrees, x);
        }
    }

    private void determineVisibleTreesBottomTop(Grid<Integer> trees, Set<Coordinate> visibleTrees, int y) {
        int highestTree = -1;
        for (int x = trees.getEndy(); x >= trees.getStarty(); x--) {
            Coordinate coordinate = new Coordinate(x, y);
            int treeHeight = trees.get(coordinate);
            if (treeHeight > highestTree) {
                visibleTrees.add(coordinate);
                highestTree = treeHeight;
            }
            if (treeHeight == 9) {
                break;
            }
        }
    }

    private void determineVisibleTreesTopBottom(Grid<Integer> trees, Set<Coordinate> visibleTrees, int y) {
        int highestTree = -1;
        for (int x = trees.getStartx(); x <= trees.getEndx(); x++) {
            Coordinate coordinate = new Coordinate(x, y);
            int treeHeight = trees.get(coordinate);
            if (treeHeight > highestTree) {
                visibleTrees.add(coordinate);
                highestTree = treeHeight;
            }
            if (treeHeight == 9) {
                break;
            }
        }
    }

    private void determineVisibleTreesRightLeft(Grid<Integer> trees, Set<Coordinate> visibleTrees, int x) {
        int highestTree = -1;
        for (int y = trees.getEndy(); y >= trees.getStarty(); y--) {
            Coordinate coordinate = new Coordinate(x, y);
            int treeHeight = trees.get(coordinate);
            if (treeHeight > highestTree) {
                visibleTrees.add(coordinate);
                highestTree = treeHeight;
            }
            if (treeHeight == 9) {
                break;
            }
        }
    }

    private void determineVisibleTreesLeftRight(Grid<Integer> trees, Set<Coordinate> visibleTrees, int x) {
        int highestTree = -1;
        for (int y = trees.getStarty(); y <= trees.getEndy(); y++) {
            Coordinate coordinate = new Coordinate(x, y);
            int treeHeight = trees.get(coordinate);
            if (treeHeight > highestTree) {
                visibleTrees.add(coordinate);
                highestTree = treeHeight;
            }
            if (treeHeight == 9) {
                break;
            }
        }
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
