package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.BiAdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.pathfinding.Dijkstra;
import nl.remcoder.adventofcode.library.pathfinding.Node;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Day17 implements BiAdventOfCodeSolution<String, Integer> {
    private static final MessageDigest MD5;

    static {
        try {
            MD5 = MessageDigest.getInstance("MD5");
        } catch (
                NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String handlePart1(Stream<String> input) {
        var passcode = input.findFirst().orElseThrow(() -> new AssertionError("Eek!"));
        
        var start = new Step(new Coordinate(0, 0), passcode, "");
        start.setDistance(0);

        var end = new Coordinate(3, 3);

        return ((Step) Dijkstra.findShortestDistance(start, node -> end.equals(((Step) node).position)).orElseThrow(() -> new AssertionError("Eek!"))).steps;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var passcode = input.findFirst().orElseThrow(() -> new AssertionError("Eek!"));

        var start = new Step(new Coordinate(0, 0), passcode, "");
        var end = new Coordinate(3, 3);

        var longestPath = Integer.MIN_VALUE;
        var currentPath = 0;
        
        var steps = new ArrayList<Step>();
        steps.add(start);
        
        while (!steps.isEmpty()) {
            currentPath++;
            var newSteps = new ArrayList<Step>();
            
            for (var step : steps) {
                for (var node : step.getNeighbors().keySet()) {
                    var newStep = (Step) node;
                    if (newStep.position.equals(end)) {
                        longestPath = currentPath;
                    } else {
                        newSteps.add(newStep);
                    }
                }
            }
            
            steps = newSteps;
        }
        
        return longestPath;
    }
    
    private static class Step extends Node {
        private final Coordinate position;
        private final String passcode;
        private final String steps;

        private Step(Coordinate position, String passcode, String steps) {
            this.position = position;
            this.passcode = passcode;
            this.steps = steps;
        }

        @Override
        public Map<? extends Node, Long> getNeighbors() {
            String hash = getHash(passcode + steps);
            
            var neighbors = new HashMap<Step, Long>();
            
            if (canMoveUp(hash)) {
                var newStep = new Step(new Coordinate(position.x(), position.y() - 1), passcode, steps + "U");
                neighbors.put(newStep, 1L);
            }
            if (canMoveDown(hash)) {
                var newStep = new Step(new Coordinate(position.x(), position.y() + 1), passcode, steps + "D");
                neighbors.put(newStep, 1L);
            }
            if (canMoveLeft(hash)) {
                var newStep = new Step(new Coordinate(position.x() - 1, position.y()), passcode, steps + "L");
                neighbors.put(newStep, 1L);
            }
            if (canMoveRight(hash)) {
                var newStep = new Step(new Coordinate(position.x() + 1, position.y()), passcode, steps + "R");
                neighbors.put(newStep, 1L);
            }
            
            return neighbors;
        }

        @Override
        public void printStateInformation() {
            System.out.println(steps);
        }
        
        private boolean canMoveUp(String hash) {
            if (position.y() == 0) {
                return false;
            }
            return isDoorOpen(hash.charAt(0));
        }
        
        private boolean canMoveDown(String hash) {
            if (position.y() == 3) {
                return false;
            }
            return isDoorOpen(hash.charAt(1));
        }
        
        private boolean canMoveLeft(String hash) {
            if (position.x() == 0) {
                return false;
            }
            return isDoorOpen(hash.charAt(2));
        }
        
        private boolean canMoveRight(String hash) {
            if (position.x() == 3) {
                return false;
            }
            return isDoorOpen(hash.charAt(3));
        }
        
        private boolean isDoorOpen(char c) {
            return c == 'b' || c == 'c' || c == 'd' || c =='e' || c == 'f';
        }
    }

    private static String getHash(String passcode) {
        var hash = MD5.digest(passcode.getBytes());
        
        return byteArrayToHex(hash);
    }
    
    private static String byteArrayToHex(byte[] hash) {
        var sb = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
