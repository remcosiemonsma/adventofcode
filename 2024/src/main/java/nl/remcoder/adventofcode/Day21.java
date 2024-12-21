package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Day21 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) {
        return input.mapToLong(this::codeComplexity)
                    .sum();
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        return input.mapToLong(this::codeComplexityPart2)
                    .sum();
    }

    private long codeComplexity(String code) {
        String depressurizedRobotPattern = createNumericMovementString(code);

        var result = 0L;

        var memo = new HashMap<MemoKey, Long>();

        var pc = 'A';
        for (var c : depressurizedRobotPattern.toCharArray()) {
            result += explodeDirectionalMovement(pc, c, 2, memo);
            pc = c;
        }

        var numericCode = Integer.parseInt(code.substring(0, 3));
        return numericCode * result;
    }

    private long codeComplexityPart2(String code) {
        String numericRobotPattern = createNumericMovementString(code);

        var result = 0L;

        var memo = new HashMap<MemoKey, Long>();

        var pc = 'A';
        for (var c : numericRobotPattern.toCharArray()) {
            result += explodeDirectionalMovement(pc, c, 25, memo);
            pc = c;
        }

        var numericCode = Integer.parseInt(code.substring(0, 3));
        return numericCode * result;
    }

    private long explodeDirectionalMovement(char previousStep, char step, int stepsWanted, Map<MemoKey, Long> memo) {
        var memoKey = new MemoKey(previousStep, step, stepsWanted);
        if (memo.containsKey(memoKey)) {
            return memo.get(memoKey);
        }

        String movement = DirectionalKeypad.moveToButton(previousStep, step);
        if (stepsWanted == 1) {
            memo.put(memoKey, (long) movement.length());
            return movement.length();
        } else {
            var result = 0L;
            var pc = 'A';
            for (var c : movement.toCharArray()) {
                result += explodeDirectionalMovement(pc, c, stepsWanted - 1, memo);
                pc = c;
            }
            memo.put(memoKey, result);
            return result;
        }
    }

    private String createDirectionalMovementString(String movementString) {
        var previousButton = 'A';

        var sb = new StringBuilder();

        for (int i = 0; i < movementString.length(); i++) {
            var wantedButton = movementString.charAt(i);

            sb.append(DirectionalKeypad.moveToButton(previousButton, wantedButton));

            previousButton = wantedButton;
        }

        return sb.toString();
    }

    private String createNumericMovementString(String code) {
        var previousButton = 'A';

        var sb = new StringBuilder();

        for (int i = 0; i < code.length(); i++) {
            var wantedButton = code.charAt(i);

            sb.append(NumericKeypad.moveToButton(previousButton, wantedButton));

            previousButton = wantedButton;
        }

        return sb.toString();
    }

    private static class DirectionalKeypad {
        private static final Map<Character, Map<Character, String>> paths = new HashMap<>();

        static {
            buildPathA();
            buildPathLeft();
            buildPathRight();
            buildPathDown();
            buildPathUp();
        }

        public static String moveToButton(char start, char end) {
            return paths.get(start).get(end);
        }

        private static void buildPathA() {
            Map<Character, String> pathA = new HashMap<>();
            pathA.put('A', "A");
            pathA.put('<', "v<<A");
            pathA.put('^', "<A");
            pathA.put('>', "vA");
            pathA.put('v', "<vA");
            paths.put('A', pathA);
        }

        private static void buildPathLeft() {
            Map<Character, String> pathLeft = new HashMap<>();
            pathLeft.put('A', ">>^A");
            pathLeft.put('<', "A");
            pathLeft.put('^', ">^A");
            pathLeft.put('>', ">>A");
            pathLeft.put('v', ">A");
            paths.put('<', pathLeft);
        }

        private static void buildPathUp() {
            Map<Character, String> pathUp = new HashMap<>();
            pathUp.put('A', ">A");
            pathUp.put('<', "v<A");
            pathUp.put('^', "A");
            pathUp.put('>', "v>A");
            pathUp.put('v', "vA");
            paths.put('^', pathUp);
        }

        private static void buildPathRight() {
            Map<Character, String> pathRight = new HashMap<>();
            pathRight.put('A', "^A");
            pathRight.put('<', "<<A");
            pathRight.put('^', "<^A");
            pathRight.put('>', "A");
            pathRight.put('v', "<A");
            paths.put('>', pathRight);
        }

        private static void buildPathDown() {
            Map<Character, String> pathRight = new HashMap<>();
            pathRight.put('A', "^>A");
            pathRight.put('<', "<A");
            pathRight.put('^', "^A");
            pathRight.put('>', ">A");
            pathRight.put('v', "A");
            paths.put('v', pathRight);
        }
    }

    private static class NumericKeypad {
        private static final Map<Character, Map<Character, String>> paths = new HashMap<>();

        static {
            buildPathA();
            buildPath0();
            buildPath1();
            buildPath2();
            buildPath3();
            buildPath4();
            buildPath5();
            buildPath6();
            buildPath7();
            buildPath8();
            buildPath9();
        }

        public static String moveToButton(char start, char end) {
            return paths.get(start).get(end);
        }

        private static void buildPathA() {
            Map<Character, String> pathA = new HashMap<>();
            pathA.put('A', "A");
            pathA.put('0', "<A");
            pathA.put('1', "^<<A");
            pathA.put('2', "<^A");
            pathA.put('3', "^A");
            pathA.put('4', "^^<<A");
            pathA.put('5', "^^<A");
            pathA.put('6', "^^A");
            pathA.put('7', "^^^<<A");
            pathA.put('8', "^^^<A");
            pathA.put('9', "^^^A");
            paths.put('A', pathA);
        }

        private static void buildPath0() {
            Map<Character, String> path0 = new HashMap<>();
            path0.put('A', ">A");
            path0.put('0', "A");
            path0.put('1', "^<A");
            path0.put('2', "^A");
            path0.put('3', "^>A");
            path0.put('4', "^^<A");
            path0.put('5', "^^A");
            path0.put('6', "^^>A");
            path0.put('7', "^^^<A");
            path0.put('8', "^^^A");
            path0.put('9', "^^^>A");
            paths.put('0', path0);
        }

        private static void buildPath1() {
            Map<Character, String> path1 = new HashMap<>();
            path1.put('A', ">>vA");
            path1.put('0', ">vA");
            path1.put('1', "A");
            path1.put('2', ">A");
            path1.put('3', ">>A");
            path1.put('4', "^A");
            path1.put('5', "^>A");
            path1.put('6', ">>^A");
            path1.put('7', "^^A");
            path1.put('8', "^^>A");
            path1.put('9', "^^>>A");
            paths.put('1', path1);
        }

        private static void buildPath2() {
            Map<Character, String> path2 = new HashMap<>();
            path2.put('A', "v>A");
            path2.put('0', "vA");
            path2.put('1', "<A");
            path2.put('2', "A");
            path2.put('3', ">A");
            path2.put('4', "<^A");
            path2.put('5', "^A");
            path2.put('6', "^>A");
            path2.put('7', "<^^A");
            path2.put('8', "^^A");
            path2.put('9', "^^>A");
            paths.put('2', path2);
        }

        private static void buildPath3() {
            Map<Character, String> path3 = new HashMap<>();
            path3.put('A', "vA");
            path3.put('0', "<vA");
            path3.put('1', "<<A");
            path3.put('2', "<A");
            path3.put('3', "A");
            path3.put('4', "<<^A");
            path3.put('5', "<^A");
            path3.put('6', "^A");
            path3.put('7', "<<^^A");
            path3.put('8', "<^^A");
            path3.put('9', "^^A");
            paths.put('3', path3);
        }

        private static void buildPath4() {
            Map<Character, String> path4 = new HashMap<>();
            path4.put('A', ">>vvA");
            path4.put('0', ">vvA");
            path4.put('1', "vA");
            path4.put('2', "v>A");
            path4.put('3', "v>>A");
            path4.put('4', "A");
            path4.put('5', ">A");
            path4.put('6', ">>A");
            path4.put('7', "^A");
            path4.put('8', "^>A");
            path4.put('9', "^>>A");
            paths.put('4', path4);
        }

        private static void buildPath5() {
            Map<Character, String> path5 = new HashMap<>();
            path5.put('A', "vv>A");
            path5.put('0', "vvA");
            path5.put('1', "<vA");
            path5.put('2', "vA");
            path5.put('3', "v>A");
            path5.put('4', "<A");
            path5.put('5', "A");
            path5.put('6', ">A");
            path5.put('7', "<^A");
            path5.put('8', "^A");
            path5.put('9', "^>A");
            paths.put('5', path5);
        }

        private static void buildPath6() {
            Map<Character, String> path6 = new HashMap<>();
            path6.put('A', "vvA");
            path6.put('0', "<vvA");
            path6.put('1', "<<vA");
            path6.put('2', "<vA");
            path6.put('3', "vA");
            path6.put('4', "<<A");
            path6.put('5', "<A");
            path6.put('6', "A");
            path6.put('7', "<<^A");
            path6.put('8', "<^A");
            path6.put('9', "^A");
            paths.put('6', path6);
        }

        private static void buildPath7() {
            Map<Character, String> path7 = new HashMap<>();
            path7.put('A', ">>vvvA");
            path7.put('0', ">vvvA");
            path7.put('1', "vvA");
            path7.put('2', "vv>A");
            path7.put('3', "vv>>A");
            path7.put('4', "vA");
            path7.put('5', ">vA");
            path7.put('6', "v>>A");
            path7.put('7', "A");
            path7.put('8', ">A");
            path7.put('9', ">>A");
            paths.put('7', path7);
        }

        private static void buildPath8() {
            Map<Character, String> path8 = new HashMap<>();
            path8.put('A', "vvv>A");
            path8.put('0', "vvvA");
            path8.put('1', "<vvA");
            path8.put('2', "vvA");
            path8.put('3', "vv>A");
            path8.put('4', "<vA");
            path8.put('5', "vA");
            path8.put('6', "v>A");
            path8.put('7', "<A");
            path8.put('8', "A");
            path8.put('9', ">A");
            paths.put('8', path8);
        }

        private static void buildPath9() {
            Map<Character, String> path9 = new HashMap<>();
            path9.put('A', "vvvA");
            path9.put('0', "vvv<A");
            path9.put('1', "<<vvA");
            path9.put('2', "<vvA");
            path9.put('3', "vvA");
            path9.put('4', "<<vA");
            path9.put('5', "<vA");
            path9.put('6', "vA");
            path9.put('7', "<<A");
            path9.put('8', "<A");
            path9.put('9', "A");
            paths.put('9', path9);
        }
    }

    private record MemoKey(char previousStep, char step, int stepsWanted) {}
}
