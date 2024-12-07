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
        var operand = equation.operands().getLast();

        long possibleDivision;
        if (operand != 0 && equation.result() % operand == 0) {
            possibleDivision = equation.result() / operand;
        } else {
            possibleDivision = 0;
        }
        var possibleSubtraction = equation.result() - operand;

        if (possibleSubtraction < 0 && possibleDivision == 0) {
            return false;
        }

        if (equation.operands().size() == 2) {
            if (possibleDivision == equation.operands().getFirst()) {
                return true;
            }
            if (possibleSubtraction == equation.operands().getFirst()) {
                return true;
            }
            return false;
        } else {
            var newOperands = new ArrayList<>(equation.operands());
            newOperands.removeLast();
            var validEquation = false;
            if (possibleDivision != 0) {
                var newEquation = new Equation(possibleDivision, newOperands);
                validEquation = isValidEquation(newEquation);
            }
            if (validEquation) {
                return true;
            } else if (possibleSubtraction > 0){
                var newEquation = new Equation(possibleSubtraction, newOperands);
                return isValidEquation(newEquation);
            } else {
                return false;
            }
        }
    }

    private boolean isValidEquationPart2(Equation equation) {
        var operand = equation.operands().getLast();

        long possibleDivision;
        if (operand != 0 && equation.result() % operand == 0) {
            possibleDivision = equation.result() / operand;
        } else {
            possibleDivision = 0;
        }
        var possibleSubtraction = equation.result() - operand;

        if (possibleSubtraction < 0 && possibleDivision == 0) {
            return false;
        }

        if (equation.operands().size() == 2) {
            if (possibleDivision == equation.operands().getFirst()) {
                return true;
            }
            if (possibleSubtraction == equation.operands().getFirst()) {
                return true;
            }
            var possibleMerge = Long.parseLong(
                    Long.toString(equation.operands().get(equation.operands().size() - 2)) + Long.toString(operand));
            if (equation.result() == possibleMerge) {
                return true;
            }
            return false;
        } else {
            var newOperands = new ArrayList<>(equation.operands());
            newOperands.removeLast();
            var validEquation = false;
            if (possibleDivision != 0) {
                var newEquation = new Equation(possibleDivision, newOperands);
                validEquation = isValidEquationPart2(newEquation);
            }
            if (validEquation) {
                return true;
            } else if (possibleSubtraction > 0){
                var newEquation = new Equation(possibleSubtraction, newOperands);
                validEquation = isValidEquationPart2(newEquation);
            }
            if (validEquation) {
                return true;
            } else {
                String operandString = Integer.toString(operand);
                if (Long.toString(equation.result()).endsWith(operandString)) {
                    var possibleSplit = (equation.result() - operand) / (long) Math.pow(10, operandString.length());
                    var newEquation = new Equation(possibleSplit, newOperands);
                    return isValidEquationPart2(newEquation);
                } else {
                    return false;
                }
            }
        }
    }

    private record Equation(long result, List<Integer> operands) {
    }
}
