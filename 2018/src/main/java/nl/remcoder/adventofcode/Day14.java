package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.BiAdventOfCodeSolution;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Day14 implements BiAdventOfCodeSolution<String, Integer> {
    @Override
    public String handlePart1(Stream<String> input) {
        var numberOfRecipes = input.findFirst()
                                   .map(Integer::parseInt)
                                   .orElseThrow(() -> new AssertionError("Eek!"));

        List<Integer> recipyScores = new ArrayList<>(numberOfRecipes + 10);

        recipyScores.add(3);
        recipyScores.add(7);

        int elf1pos = 0;
        int elf2pos = 1;

        for (int i = 0; i < numberOfRecipes + 10; i++) {
            int recipy1Score = recipyScores.get(elf1pos);
            int recipy2Score = recipyScores.get(elf2pos);

            int newRecipyScore = recipy1Score + recipy2Score;

            if (newRecipyScore > 9) {
                recipyScores.add((int) Math.floor(newRecipyScore / 10d));
                recipyScores.add(newRecipyScore % 10);
            } else {
                recipyScores.add(newRecipyScore);
            }

            elf1pos += 1 + recipyScores.get(elf1pos);
            elf2pos += 1 + recipyScores.get(elf2pos);

            while (elf1pos > recipyScores.size() - 1) {
                elf1pos -= recipyScores.size();
            }

            while (elf2pos > recipyScores.size() - 1) {
                elf2pos -= recipyScores.size();
            }
        }

        return recipyScores.stream()
                           .skip(numberOfRecipes)
                           .limit(10)
                           .map(Object::toString)
                           .reduce((s, s2) -> s + s2)
                           .orElseThrow(() -> new AssertionError("Ook!"));
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var expectedRecipyList = input.findFirst()
                                      .orElseThrow(() -> new AssertionError("Eek!"));

        var recipyScores = new StringBuilder("37");

        var elf1pos = 0;
        var elf2pos = 1;

        var doesListEndWithOtherList = doesListEndWithOtherList(recipyScores, expectedRecipyList);
        var doesListEndWithOtherList1CharExtra =
                doesListEndWithOtherList1CharExtra(recipyScores, expectedRecipyList);

        while (!doesListEndWithOtherList && !doesListEndWithOtherList1CharExtra) {
            calculateAndAddNewRecipyScore(recipyScores, elf1pos, elf2pos);

            elf1pos = moveElf(recipyScores, elf1pos);

            elf2pos = moveElf(recipyScores, elf2pos);

            doesListEndWithOtherList = doesListEndWithOtherList(recipyScores, expectedRecipyList);
            doesListEndWithOtherList1CharExtra = doesListEndWithOtherList1CharExtra(recipyScores, expectedRecipyList);
        }

        if (doesListEndWithOtherList) {
            return recipyScores.length() - expectedRecipyList.length();
        } else {
            return recipyScores.length() - expectedRecipyList.length() - 1;
        }
    }

    private boolean doesListEndWithOtherList(StringBuilder recipyList, String checkList) {
        if (recipyList.length() >= checkList.length()) {
            for (var i = 0; i < checkList.length(); i++) {
                if (recipyList.charAt(recipyList.length() - (checkList.length() - i)) != checkList.charAt(i)) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    private boolean doesListEndWithOtherList1CharExtra(StringBuilder recipyList, String checkList) {
        if (recipyList.length() > checkList.length()) {
            for (var i = 0; i < checkList.length(); i++) {
                if (recipyList.charAt(recipyList.length() - (checkList.length() - i) - 1) != checkList.charAt(i)) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    private int moveElf(StringBuilder recipyScores, int elf1pos) {
        elf1pos += 1 + (recipyScores.charAt(elf1pos) - '0');
        elf1pos %= recipyScores.length();
        return elf1pos;
    }

    private void calculateAndAddNewRecipyScore(StringBuilder recipyScores, int elf1pos, int elf2pos) {
        var recipy1Score = recipyScores.charAt(elf1pos) - '0';
        var recipy2Score = recipyScores.charAt(elf2pos) - '0';

        var newRecipyScore = recipy1Score + recipy2Score;

        if (newRecipyScore > 9) {
            recipyScores.append((int) Math.floor(newRecipyScore / 10d));
            recipyScores.append(newRecipyScore % 10);
        } else {
            recipyScores.append(newRecipyScore);
        }
    }
}
