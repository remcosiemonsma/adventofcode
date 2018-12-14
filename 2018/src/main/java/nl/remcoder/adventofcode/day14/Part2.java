package nl.remcoder.adventofcode.day14;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Part2 {
    public static void main(String[] args) throws Exception {
        String expectedRecipyList = Files.lines(Paths.get(ClassLoader.getSystemResource("day14/input").toURI()))
                                                .findFirst()
                                                .get();

        StringBuilder recipyScores = new StringBuilder("37");

        int elf1pos = 0;
        int elf2pos = 1;

        boolean doesListEndWithOtherList = doesListEndWithOtherList(recipyScores, expectedRecipyList);
        boolean doesListEndWithOtherList1CharExtra = doesListEndWithOtherList1CharExtra(recipyScores, expectedRecipyList);

        while(!doesListEndWithOtherList && !doesListEndWithOtherList1CharExtra) {
            calculateAndAddNewRecipyScore(recipyScores, elf1pos, elf2pos);

            elf1pos = moveElf(recipyScores, elf1pos);

            elf2pos = moveElf(recipyScores, elf2pos);

            doesListEndWithOtherList = doesListEndWithOtherList(recipyScores, expectedRecipyList);
            doesListEndWithOtherList1CharExtra = doesListEndWithOtherList1CharExtra(recipyScores, expectedRecipyList);
        }

        if (doesListEndWithOtherList) {
            System.out.println(recipyScores.length() - expectedRecipyList.length());
        } else {
            System.out.println(recipyScores.length() - expectedRecipyList.length() - 1);
        }
    }

    private static boolean doesListEndWithOtherList(StringBuilder recipyList, String checkList) {
        if (recipyList.length() >= checkList.length()) {
            for (int i = 0; i < checkList.length(); i++) {
                if (recipyList.charAt(recipyList.length() - (checkList.length() - i)) != checkList.charAt(i)) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    private static boolean doesListEndWithOtherList1CharExtra(StringBuilder recipyList, String checkList) {
        if (recipyList.length() > checkList.length()) {
            for (int i = 0; i < checkList.length(); i++) {
                if (recipyList.charAt(recipyList.length() - (checkList.length() - i) - 1) != checkList.charAt(i)) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    private static int moveElf(StringBuilder recipyScores, int elf1pos) {
        elf1pos += 1 + (recipyScores.charAt(elf1pos) - '0');
        elf1pos %= recipyScores.length();
        return elf1pos;
    }

    private static void calculateAndAddNewRecipyScore(StringBuilder recipyScores, int elf1pos, int elf2pos) {
        int recipy1Score = recipyScores.charAt(elf1pos) - '0';
        int recipy2Score = recipyScores.charAt(elf2pos) - '0';

        int newRecipyScore = recipy1Score + recipy2Score;

        if (newRecipyScore > 9) {
            recipyScores.append((int) Math.floor(newRecipyScore / 10d));
            recipyScores.append(newRecipyScore % 10);
        } else {
            recipyScores.append(newRecipyScore);
        }
    }
}
