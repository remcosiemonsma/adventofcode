package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Day7 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) {
        return input.map(this::mapToEquation)
                    .filter(this::isValidEquation)
                    .mapToLong(Equation::result)
                    .sum();
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        return input.map(this::mapToEquation)
                    .filter(this::isValidEquationPart2)
                    .mapToLong(Equation::result)
                    .sum();
    }

    private Equation mapToEquation(String line) {
        var split = line.split(":? ");

        var result = Long.parseLong(split[0]);

        var operands = new ArrayList<Integer>();

        for (int i = 1; i < split.length; i++) {
            operands.add(Integer.parseInt(split[i]));
        }

        return new Equation(result, operands);
    }

    private boolean isValidEquation(Equation equation) {
        List<List<Character>> possibleFormulas = determinePossibleFormulas(equation.result(), equation.operands());

        return !possibleFormulas.isEmpty();
    }

    private boolean isValidEquationPart2(Equation equation) {
        List<List<Character>> possibleFormulas = determinePossibleFormulasPart2(equation.result(), equation.operands());

        return !possibleFormulas.isEmpty();
    }

    private List<List<Character>> determinePossibleFormulasPart2(long expectedResult, List<Integer> operands) {
        var possibleFormulas = new ArrayList<List<Character>>();

        var operand = operands.getLast();

        long possibleDivision;
        if (expectedResult % operand == 0) {
            possibleDivision = expectedResult / operand;
        } else {
            possibleDivision = 0;
        }
        var possibleSubtraction = expectedResult - operand;

        if (operands.size() == 2) {
            if (possibleDivision == operands.getFirst()) {
                var possibleOperators = new ArrayList<Character>();
                possibleOperators.add('*');
                possibleFormulas.add(possibleOperators);
            }
            if (possibleSubtraction == operands.getFirst()) {
                var possibleOperators = new ArrayList<Character>();
                possibleOperators.add('+');
                possibleFormulas.add(possibleOperators);
            }
            var possibleMerge = Long.parseLong(Long.toString(operands.get(operands.size() - 2)) + Long.toString(operand));
            if (expectedResult == possibleMerge) {
                var possibleOperators = new ArrayList<Character>();
                possibleOperators.add('|');
                possibleFormulas.add(possibleOperators);
            }
        } else {
            var newOperands = new ArrayList<>(operands);
            newOperands.removeLast();
            if (possibleDivision != 0) {
                var possibleDivisionFormulas = determinePossibleFormulasPart2(possibleDivision, newOperands);
                possibleDivisionFormulas.forEach(formula -> formula.add('*'));
                possibleFormulas.addAll(possibleDivisionFormulas);
            }
            var possibleSubtractionFormulas = determinePossibleFormulasPart2(possibleSubtraction, newOperands);
            possibleSubtractionFormulas.forEach(formula -> formula.add('+'));
            possibleFormulas.addAll(possibleSubtractionFormulas);

            String operandString = Integer.toString(operand);
            if (Long.toString(expectedResult).endsWith(operandString)) {
                var possibleSplit = (expectedResult - operand) / (long)Math.pow(10, operandString.length());
                var possibleSplitFormulas = determinePossibleFormulasPart2(possibleSplit, newOperands);
                possibleSplitFormulas.forEach(formula -> formula.add('|'));
                possibleFormulas.addAll(possibleSplitFormulas);
            }
        }
        return possibleFormulas;
    }

    private List<List<Character>> determinePossibleFormulas(final long expectedResult, List<Integer> operands) {
        if (operands.size() < 2) {
            return List.of();
        }

        var possibleFormulas = new ArrayList<List<Character>>();

        var operand = operands.getLast();

        long possibleDivision;
        if (expectedResult % operand == 0) {
            possibleDivision = expectedResult / operand;
        } else {
            possibleDivision = 0;
        }
        var possibleSubtraction = expectedResult - operand;

        if (operands.size() == 2) {
            if (possibleDivision == operands.getFirst()) {
                var possibleOperators = new ArrayList<Character>();
                possibleOperators.add('*');
                possibleFormulas.add(possibleOperators);
            }
            if (possibleSubtraction == operands.getFirst()) {
                var possibleOperators = new ArrayList<Character>();
                possibleOperators.add('+');
                possibleFormulas.add(possibleOperators);
            }
        } else {
            var newOperands = new ArrayList<>(operands);
            newOperands.removeLast();
            if (possibleDivision != 0) {
                var possibleDivisionFormulas = determinePossibleFormulas(possibleDivision, newOperands);
                possibleDivisionFormulas.forEach(formula -> formula.add('*'));
                possibleFormulas.addAll(possibleDivisionFormulas);
            }
            var possibleSubtractionFormulas = determinePossibleFormulas(possibleSubtraction, newOperands);
            possibleSubtractionFormulas.forEach(formula -> formula.add('+'));
            possibleFormulas.addAll(possibleSubtractionFormulas);
        }
        return possibleFormulas;
    }

    private record Equation(long result, List<Integer> operands) {
    }
}
