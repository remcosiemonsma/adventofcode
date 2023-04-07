package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.BiAdventOfCodeSolution;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Day21 implements BiAdventOfCodeSolution<Integer, Long> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var players = input.map(this::createPlayer)
                           .toArray(Player[]::new);

        var scoresPlayer1 = new int[] {6, 4, 2, 10, 8};
        var scoresPlayer2 = new int[] {5, 3, 1, 9, 7};

        var round = 0;
        var dicethrows = 0;

        while (players[0].score < 1000 && players[1].score < 1000) {
            dicethrows += 3;
            players[0].position = (players[0].position + scoresPlayer1[round % 5]) % 10;
            if (players[0].position == 0) {
                players[0].score += 10;
            } else {
                players[0].score += players[0].position;
            }
            if (players[0].score < 1000) {
                dicethrows += 3;
                players[1].position = (players[1].position + scoresPlayer2[round % 5]) % 10;
                if (players[1].position == 0) {
                    players[1].score += 10;
                } else {
                    players[1].score += players[1].position;
                }
            }
            round++;
        }

        return dicethrows * players[1].score;
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var players = input.map(this::createPlayer)
                           .toArray(Player[]::new);

        var memo = new HashMap<GameState, Result>();

        Result result = amountOfWins(players[0].position, players[1].position, 0, 0, memo);

        return Math.max(result.player1Wins, result.player2Wins);
    }

    private Result amountOfWins(int player1position, int player2position, int player1score, int player2score,
                                Map<GameState, Result> memo) {
        var gameState = new GameState(player1position, player2position, player1score, player2score);
        if (memo.containsKey(gameState)) {
            return memo.get(gameState);
        }

        var amountOfWins = new long[2];

        for (var p1throw1 = 1; p1throw1 <= 3; p1throw1++) {
            for (var p1throw2 = 1; p1throw2 <= 3; p1throw2++) {
                for (var p1throw3 = 1; p1throw3 <= 3; p1throw3++) {
                    var copyOfPlayer1position = (player1position + p1throw1 + p1throw2 + p1throw3) % 10;
                    int copyOfPlayer1score;
                    if (copyOfPlayer1position == 0) {
                        copyOfPlayer1score = player1score + 10;
                    } else {
                        copyOfPlayer1score = player1score + copyOfPlayer1position;
                    }
                    if (copyOfPlayer1score >= 21) {
                        amountOfWins[0]++;
                    } else {
                        for (var p2throw1 = 1; p2throw1 <= 3; p2throw1++) {
                            for (var p2throw2 = 1; p2throw2 <= 3; p2throw2++) {
                                for (var p2throw3 = 1; p2throw3 <= 3; p2throw3++) {
                                    var copyOfPlayer2position = (player2position + p2throw1 + p2throw2 + p2throw3) % 10;
                                    int copyOfPlayer2score;
                                    if (copyOfPlayer2position == 0) {
                                        copyOfPlayer2score = player2score + 10;
                                    } else {
                                        copyOfPlayer2score = player2score + copyOfPlayer2position;
                                    }
                                    if (copyOfPlayer2score >= 21) {
                                        amountOfWins[1]++;
                                    } else {
                                        Result result = amountOfWins(copyOfPlayer1position, copyOfPlayer2position,
                                                                     copyOfPlayer1score, copyOfPlayer2score,
                                                                     memo);
                                        amountOfWins[0] += result.player1Wins;
                                        amountOfWins[1] += result.player2Wins;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        var result = new Result(amountOfWins[0], amountOfWins[1]);
        memo.put(gameState, result);

        return result;
    }

    private Player createPlayer(String input) {
        return new Player(Integer.parseInt(input.substring(28)));
    }

    private static class Player {
        private int position;
        private int score;

        public Player(int position) {
            this.position = position;
            this.score = 0;
        }
    }

    private record GameState(int player1position, int player2position, int player1score, int player2score) {
    }

    private record Result(long player1Wins, long player2Wins) {
    }
}
