package nl.remcoder.adventofcode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Day9 {
    public long handlePart1(Stream<String> input,
                            int preambleLength) {
        List<Long> numbers = input.map(Long::parseLong)
                                  .collect(Collectors.toList());

        List<Long> preamble = new ArrayList<>(numbers.subList(0, preambleLength));

        return numbers.stream()
                      .skip(preambleLength)
                      .filter(number -> !isNumberInPreamble(number, preamble))
                      .findFirst()
                      .orElse(-1L);
    }

    public long handlePart2(Stream<String> input,
                            int preambleLength) {
        List<Long> numbers = input.map(Long::parseLong)
                                  .collect(Collectors.toList());

        long invalidNumber = handlePart1(numbers.stream().map(Object::toString), preambleLength);

        long result = -1;

        outer:
        for (int position = 0; position < numbers.size(); position++) {
            for (int windowSize = 2; windowSize < numbers.size(); windowSize++) {
                long possibleResult = numbers.stream()
                                             .skip(position)
                                             .limit(windowSize)
                                             .reduce(Long::sum)
                                             .orElse(-1L);

                if (possibleResult == invalidNumber) {
                    long min = numbers.stream()
                                      .skip(position)
                                      .limit(windowSize)
                                      .min(Long::compareTo)
                                      .orElse(-1L);

                    long max = numbers.stream()
                                      .skip(position)
                                      .limit(windowSize)
                                      .max(Long::compareTo)
                                      .orElse(-1L);

                    result = min + max;
                    break outer;
                }
                if (possibleResult > invalidNumber) {
                    continue outer;
                }
            }
        }

        return result;
    }

    private boolean isNumberInPreamble(long number, List<Long> preamble) {
        boolean result = preamble.stream()
                                 .map(preambleDigit -> preamble.stream()
                                                               .filter(otherPreamble -> !otherPreamble
                                                                       .equals(preambleDigit))
                                                               .mapToLong(otherPreamble -> otherPreamble +
                                                                                           preambleDigit))
                                 .flatMap(LongStream::boxed)
                                 .anyMatch(digit -> digit.equals(number));

        if (result) {
            preamble.remove(0);
            preamble.add(number);
        }

        return result;
    }
}
