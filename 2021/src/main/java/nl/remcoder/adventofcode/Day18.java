package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.stream.Stream;

public class Day18 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) {
        return input.map((String number) -> createSnailNumber(number, null, 0))
                    .peek(this::reduceSnailNumber)
                    .reduce(this::addSnailNumbers)
                    .map(SnailNumber::calculateMagnitude)
                    .orElseThrow(() -> new AssertionError("Eek!"));
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var snailNumbers = input.map((String number) -> createSnailNumber(number, null, 0))
                                .peek(this::reduceSnailNumber)
                                .toList();

        var greatestMagnitude = 0L;

        for (var firstSnailNumber : snailNumbers) {
            for (var secondSnailNumber : snailNumbers) {
                if (firstSnailNumber == secondSnailNumber) {
                    continue;
                }
                var snailNumber = firstSnailNumber.copy(null);
                var otherSnailNumber = secondSnailNumber.copy(null);
                var combined = addSnailNumbers(snailNumber, otherSnailNumber);
                reduceSnailNumber(combined);
                long magnitude = combined.calculateMagnitude();
                if (magnitude > greatestMagnitude) {
                    greatestMagnitude = magnitude;
                }
            }
        }

        return greatestMagnitude;
    }

    private SnailNumber addSnailNumbers(SnailNumber left, SnailNumber right) {
        var snailNumber = new SnailNumber();
        snailNumber.nesting = 0;
        snailNumber.left = left;
        left.parent = snailNumber;
        left.incrementNesting();
        snailNumber.right = right;
        right.parent = snailNumber;
        right.incrementNesting();
        reduceSnailNumber(snailNumber);
        return snailNumber;
    }

    private void reduceSnailNumber(SnailNumber snailNumber) {
        boolean canSnailNumberBeReduced = true;

        while (canSnailNumberBeReduced) {
            canSnailNumberBeReduced = canNumberBeExploded(snailNumber) || canNumberBeSplit(snailNumber);
        }
    }

    private boolean canNumberBeExploded(SnailNumber snailNumber) {
        if (snailNumber.mustBeExploded()) {
            snailNumber.explode();
            return true;
        } else {
            if (snailNumber.isRegularNumber()) {
                return false;
            }
            if (canNumberBeExploded(snailNumber.left)) {
                return true;
            } else {
                return canNumberBeExploded(snailNumber.right);
            }
        }
    }

    private boolean canNumberBeSplit(SnailNumber snailNumber) {
        if (snailNumber.mustBeSplit()) {
            snailNumber.split();
            return true;
        } else {
            if (snailNumber.isRegularNumber()) {
                return false;
            }
            if (canNumberBeSplit(snailNumber.left)) {
                return true;
            } else {
                return canNumberBeSplit(snailNumber.right);
            }
        }
    }

    private SnailNumber createSnailNumber(String number, SnailNumber parent, int nesting) {
        var position = 1;

        var snailNumber = new SnailNumber();
        snailNumber.parent = parent;
        snailNumber.nesting = nesting;

        while (position < number.length()) {
            if (Character.isDigit(number.charAt(position))) {
                var leftValue = Character.digit(number.charAt(position), 10);
                var left = new SnailNumber();
                left.parent = snailNumber;
                left.value = leftValue;
                left.nesting = nesting + 1;
                snailNumber.left = left;
                position += 2;
                if (Character.isDigit(number.charAt(position))) {
                    var rightValue = Character.digit(number.charAt(position), 10);
                    var right = new SnailNumber();
                    right.parent = snailNumber;
                    right.value = rightValue;
                    right.nesting = nesting + 1;
                    snailNumber.right = right;
                    position += 2;
                } else {
                    var subNumber = createSubNumber(number, position);
                    snailNumber.right = createSnailNumber(subNumber, snailNumber, nesting + 1);
                    position += subNumber.length() + 1;
                }
            } else {
                var subNumber = createSubNumber(number, position);
                snailNumber.left = createSnailNumber(subNumber, snailNumber, nesting + 1);
                position += subNumber.length() + 1;
                if (Character.isDigit(number.charAt(position))) {
                    var rightValue = Character.digit(number.charAt(position), 10);
                    var right = new SnailNumber();
                    right.parent = snailNumber;
                    right.value = rightValue;
                    right.nesting = nesting + 1;
                    snailNumber.right = right;
                    position += 2;
                } else {
                    var subRight = createSubNumber(number, position);
                    snailNumber.right = createSnailNumber(subRight, snailNumber, nesting + 1);
                    position += subRight.length() + 1;
                }
            }
        }

        return snailNumber;
    }

    private String createSubNumber(String number, int position) {
        var count = 1;

        var end = position + 1;

        while (count > 0) {
            switch (number.charAt(end++)) {
                case '[' -> count++;
                case ']' -> count--;
            }
        }

        return number.substring(position, end);
    }

    private static final class SnailNumber {
        private SnailNumber left;
        private SnailNumber right;
        private SnailNumber parent;
        private int nesting;
        private Integer value;

        public boolean isRegularNumber() {
            return value != null;
        }

        public boolean mustBeExploded() {
            return !isRegularNumber() && left.isRegularNumber() && right.isRegularNumber() && nesting >= 4;
        }

        public boolean mustBeSplit() {
            return isRegularNumber() && value > 9;
        }

        public void explode() {
            if (mustBeExploded()) {
                explodeLeftSide();
                explodeRightSide();

                var newNumber = new SnailNumber();
                newNumber.value = 0;
                newNumber.parent = this.parent;
                newNumber.nesting = nesting;

                if (parent.left == this) {
                    parent.left = newNumber;
                } else {
                    parent.right = newNumber;
                }
            } else {
                throw new AssertionError("Boom!");
            }
        }

        private void explodeLeftSide() {
            if (parent.right == this) {
                if (parent.left.isRegularNumber()) {
                    parent.left.value += left.value;
                } else {
                    var exploded = false;

                    var previous = parent.left;

                    while (!exploded) {
                        if (previous.right.isRegularNumber()) {
                            previous.right.value += left.value;
                            exploded = true;
                        } else {
                            previous = previous.right;
                        }
                    }
                }
            } else {
                var previousParent = parent;

                var rightParentFound = false;

                while (!rightParentFound) {
                    if (previousParent.parent == null) {
                        break;
                    } else {
                        if (previousParent.parent.left != previousParent) {
                            rightParentFound = true;
                        }
                        previousParent = previousParent.parent;
                    }
                }

                if (rightParentFound) {
                    var exploded = false;

                    var previous = previousParent.left;

                    if (previous.isRegularNumber()) {
                        previous.value += left.value;
                    } else {
                        while (!exploded) {
                            if (previous.right.isRegularNumber()) {
                                previous.right.value += left.value;
                                exploded = true;
                            } else {
                                previous = previous.right;
                            }
                        }
                    }
                }
            }
        }

        private void explodeRightSide() {
            if (parent.left == this) {
                if (parent.right.isRegularNumber()) {
                    parent.right.value += right.value;
                } else {
                    var exploded = false;

                    var previous = parent.right;

                    while (!exploded) {
                        if (previous.left.isRegularNumber()) {
                            previous.left.value += right.value;
                            exploded = true;
                        } else {
                            previous = previous.left;
                        }
                    }
                }
            } else {
                var previousParent = parent;

                var leftParentFound = false;

                while (!leftParentFound) {
                    if (previousParent.parent == null) {
                        break;
                    } else {
                        if (previousParent.parent.right != previousParent) {
                            leftParentFound = true;
                        }
                        previousParent = previousParent.parent;
                    }
                }

                if (leftParentFound) {
                    var exploded = false;

                    var previous = previousParent.right;

                    if (previous.isRegularNumber()) {
                        previous.value += right.value;
                    } else {
                        while (!exploded) {
                            if (previous.left.isRegularNumber()) {
                                previous.left.value += right.value;
                                exploded = true;
                            } else {
                                previous = previous.left;
                            }
                        }
                    }
                }
            }
        }

        public void split() {
            if (mustBeSplit()) {
                var leftValue = (int) Math.floor(value / 2d);
                var rightValue = (int) Math.ceil(value / 2d);

                value = null;

                left = new SnailNumber();
                left.parent = this;
                left.value = leftValue;
                left.nesting = nesting + 1;

                right = new SnailNumber();
                right.parent = this;
                right.value = rightValue;
                right.nesting = nesting + 1;
            } else {
                throw new AssertionError("Crack");
            }
        }

        public void incrementNesting() {
            this.nesting++;
            if (!isRegularNumber()) {
                left.incrementNesting();
                right.incrementNesting();
            }
        }

        public long calculateMagnitude() {
            if (isRegularNumber()) {
                return value;
            } else {
                return (left.calculateMagnitude() * 3) + (right.calculateMagnitude() * 2);
            }
        }

        public SnailNumber copy(SnailNumber parent) {
            var copy = new SnailNumber();
            copy.parent = parent;
            copy.value = this.value;
            copy.nesting = this.nesting;
            if (left != null) {
                copy.left = left.copy(copy);
            }
            if (right != null) {
                copy.right = right.copy(copy);
            }
            return copy;
        }

        @Override
        public String toString() {
            if (value != null) {
                return value.toString();
            } else {
                return "[" + left + "," + right + "]";
            }
        }
    }
}
