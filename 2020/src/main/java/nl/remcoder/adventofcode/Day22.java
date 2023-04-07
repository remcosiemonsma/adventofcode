package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

public class Day22 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var lines = input.skip(1).toList();

        var player1 = new ArrayList<Integer>();
        var player2 = new ArrayList<Integer>();

        var currentPlayer = player1;

        for (var line : lines) {
            if (line.isBlank()) {
                continue;
            }
            if (line.startsWith("Player")) {
                currentPlayer = player2;
            } else {
                currentPlayer.add(Integer.parseInt(line));
            }
        }

        while (!player1.isEmpty() && !player2.isEmpty()) {
            if (player1.get(0) < player2.get(0)) {
                player2.add(player2.remove(0));
                player2.add(player1.remove(0));
            } else {
                player1.add(player1.remove(0));
                player1.add(player2.remove(0));
            }
        }

        List<Integer> winningPlayer;

        if (player1.isEmpty()) {
            winningPlayer = player2;
        } else {
            winningPlayer = player1;
        }

        var score = 0;

        for (var position = 0; position < winningPlayer.size(); position++) {
            score += winningPlayer.get(position) * (winningPlayer.size() - position);
        }

        return score;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var lines = input.skip(1).toList();

        var player1 = new ArrayList<Integer>();
        var player2 = new ArrayList<Integer>();

        var currentPlayer = player1;

        for (var line : lines) {
            if (line.isBlank()) {
                continue;
            }
            if (line.startsWith("Player")) {
                currentPlayer = player2;
            } else {
                currentPlayer.add(Integer.parseInt(line));
            }
        }

        playRecursiveCombat(player1, player2);

        List<Integer> winningPlayer;

        if (player1.isEmpty()) {
            winningPlayer = player2;
        } else {
            winningPlayer = player1;
        }

        int score = 0;

        for (int position = 0; position < winningPlayer.size(); position++) {
            score += winningPlayer.get(position) * (winningPlayer.size() - position);
        }

        return score;
    }

    private void playRecursiveCombat(List<Integer> player1, List<Integer> player2) {
        var memory = new HashSet<List<List<Integer>>>();

        while (!player1.isEmpty() && !player2.isEmpty()) {
            if (memory.contains(List.of(new ArrayList<>(player1), new ArrayList<>(player2)))) {
                player2.clear();
                break;
            } else {
                memory.add(List.of(new ArrayList<>(player1), new ArrayList<>(player2)));
            }

            if (player1.size() > player1.get(0) && player2.size() > player2.get(0)) {
                var player1SubList = new ArrayList<>(player1.subList(1, player1.get(0) + 1));
                var player2SubList = new ArrayList<>(player2.subList(1, player2.get(0) + 1));
                playRecursiveCombat(player1SubList, player2SubList);
                if (player1SubList.isEmpty()) {
                    player2.add(player2.remove(0));
                    player2.add(player1.remove(0));
                } else {
                    player1.add(player1.remove(0));
                    player1.add(player2.remove(0));
                }
            } else {
                if (player1.get(0) < player2.get(0)) {
                    player2.add(player2.remove(0));
                    player2.add(player1.remove(0));
                } else {
                    player1.add(player1.remove(0));
                    player1.add(player2.remove(0));
                }
            }
        }
    }
}
