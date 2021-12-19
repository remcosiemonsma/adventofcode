package nl.remcoder.adventofcode;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day18 {
    public long handlePart1(Stream<String> input) {
        return input.map((String number) -> createSnailNumber(number, null, 0))
                    .peek(this::reduceSnailNumber)
                    .reduce(this::addSnailNumbers)
                    .map(SnailNumber::calculateMagnitude)
                    .get();
    }

    public long handlePart2(Stream<String> input) {
        List<SnailNumber> snailNumbers = input.map((String number) -> createSnailNumber(number, null, 0))
                                         .peek(this::reduceSnailNumber)
                                         .collect(Collectors.toList());

        long greatestMagnitude = 0;

        for (SnailNumber firstSnailNumber : snailNumbers) {
            for (SnailNumber secondSnailNumber : snailNumbers) {
                if (firstSnailNumber == secondSnailNumber) {
                    continue;
                }
                SnailNumber snailNumber = firstSnailNumber.copy(null);
                SnailNumber otherSnailNumber = secondSnailNumber.copy(null);
                SnailNumber combined = addSnailNumbers(snailNumber, otherSnailNumber);
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
        SnailNumber snailNumber = new SnailNumber();
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
                if (canNumberBeExploded(snailNumber.right)) {
                    return true;
                }
            }
        }
        return false;
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
                if (canNumberBeSplit(snailNumber.right)) {
                    return true;
                }
            }
        }
        return false;
    }

    private SnailNumber createSnailNumber(String number, SnailNumber parent, int nesting) {
        int position = 1;

        SnailNumber snailNumber = new SnailNumber();
        snailNumber.parent = parent;
        snailNumber.nesting = nesting;

        while (position < number.length()) {
            if (Character.isDigit(number.charAt(position))) {
                int leftValue = Character.digit(number.charAt(position), 10);
                SnailNumber left = new SnailNumber();
                left.parent = snailNumber;
                left.value = leftValue;
                left.nesting = nesting + 1;
                snailNumber.left = left;
                position += 2;
                if (Character.isDigit(number.charAt(position))) {
                    int rightValue = Character.digit(number.charAt(position), 10);
                    SnailNumber right = new SnailNumber();
                    right.parent = snailNumber;
                    right.value = rightValue;
                    right.nesting = nesting + 1;
                    snailNumber.right = right;
                    position += 2;
                } else {
                    String subNumber = createSubNumber(number, position);
                    snailNumber.right = createSnailNumber(subNumber, snailNumber, nesting + 1);
                    position += subNumber.length() + 1;
                }
            } else {
                String subNumber = createSubNumber(number, position);
                snailNumber.left = createSnailNumber(subNumber, snailNumber, nesting + 1);
                position += subNumber.length() + 1;
                if (Character.isDigit(number.charAt(position))) {
                    int rightValue = Character.digit(number.charAt(position), 10);
                    SnailNumber right = new SnailNumber();
                    right.parent = snailNumber;
                    right.value = rightValue;
                    right.nesting = nesting + 1;
                    snailNumber.right = right;
                    position += 2;
                } else {
                    String subRight = createSubNumber(number, position);
                    snailNumber.right = createSnailNumber(subRight, snailNumber, nesting + 1);
                    position += subRight.length() + 1;
                }
            }
        }

        return snailNumber;
    }

    private String createSubNumber(String number, int position) {
        int count = 1;

        int end = position + 1;

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

                SnailNumber newNumber = new SnailNumber();
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
                    boolean exploded = false;

                    SnailNumber previous = parent.left;

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
                SnailNumber previousParent = parent;

                boolean rightParentFound = false;

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
                    boolean exploded = false;

                    SnailNumber previous = previousParent.left;

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
                    boolean exploded = false;

                    SnailNumber previous = parent.right;

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
                SnailNumber previousParent = parent;

                boolean leftParentFound = false;

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
                    boolean exploded = false;

                    SnailNumber previous = previousParent.right;

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
                int leftValue = (int) Math.floor(value / 2d);
                int rightValue = (int) Math.ceil(value / 2d);

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
            SnailNumber copy = new SnailNumber();
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
