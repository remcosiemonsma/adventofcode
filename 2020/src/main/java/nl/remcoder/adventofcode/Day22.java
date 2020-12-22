package nl.remcoder.adventofcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day22 {
    public int handlePart1(Stream<String> input) {
        List<String> lines = input.skip(1).collect(Collectors.toList());

        List<Integer> player1 = new ArrayList<>();
        List<Integer> player2 = new ArrayList<>();

        List<Integer> currentPlayer = player1;

        for (String line : lines) {
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

        int score = 0;

        for (int position = 0; position < winningPlayer.size(); position++) {
            score += winningPlayer.get(position) * (winningPlayer.size() - position);
        }

        return score;
    }

    public int handlePart2(Stream<String> input) {
        List<String> lines = input.skip(1).collect(Collectors.toList());

        List<Integer> player1 = new ArrayList<>();
        List<Integer> player2 = new ArrayList<>();

        List<Integer> currentPlayer = player1;

        for (String line : lines) {
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
        Set<List<List<Integer>>> memory = new HashSet<>();

        while (!player1.isEmpty() && !player2.isEmpty()) {
            if (memory.contains(List.of(new ArrayList<>(player1), new ArrayList<>(player2)))) {
                player2.clear();
                break;
            } else {
                memory.add(List.of(new ArrayList<>(player1), new ArrayList<>(player2)));
            }

            if (player1.size() > player1.get(0) && player2.size() > player2.get(0)) {
                List<Integer> player1SubList = new ArrayList<>(player1.subList(1, player1.get(0) + 1));
                List<Integer> player2SubList = new ArrayList<>(player2.subList(1, player2.get(0) + 1));
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
