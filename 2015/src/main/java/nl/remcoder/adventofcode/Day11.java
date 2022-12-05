package nl.remcoder.adventofcode;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class Day11 {
    public String handlePart1(Stream<String> input) {
        var password = input.findFirst()
                            .orElseThrow(() -> new AssertionError("Eek!"));

        if (password.contains("i")) {
            var forbiddenChar = 'i';
            password = generatePasswordWithoutForbiddenChar(password, forbiddenChar);
        } else if (password.contains("o")) {
            var forbiddenChar = 'o';
            password = generatePasswordWithoutForbiddenChar(password, forbiddenChar);
        } else if (password.contains("l")) {
            var forbiddenChar = 'l';
            password = generatePasswordWithoutForbiddenChar(password, forbiddenChar);
        }

        while (!isPasswordValid(password)) {
            password = generateNextPassword(password);
        }

        return password;
    }

    public String handlePart2(Stream<String> input) {
        var previousPassword = handlePart1(input);

        var password = generateNextPassword(previousPassword);

        while (!isPasswordValid(password)) {
            password = generateNextPassword(password);
        }

        return password;
    }

    private String generateNextPassword(String password) {
        var passwordBuilder = new StringBuilder();
        var lastChar = password.charAt(password.length() - 1);
        if (lastChar < 'z') {
            for (var position = 0; position < password.length() - 1; position++) {
                passwordBuilder.append(password.charAt(position));
            }
            var nextChar = (char) (lastChar + 1);

            while (nextChar == 'i' || nextChar == 'o' || nextChar == 'l' ||
                   (passwordBuilder.charAt(passwordBuilder.length() - 1) == nextChar &&
                    passwordBuilder.charAt(passwordBuilder.length() - 2) == nextChar)) {
                nextChar++;
            }

            passwordBuilder.append(nextChar);
        } else {
            passwordBuilder.append(generateNextPassword(password.substring(0, password.length() - 1)));
            passwordBuilder.append('a');
        }

        return passwordBuilder.toString();
    }

    private String generatePasswordWithoutForbiddenChar(String password, char badchar) {
        var passwordBuilder = new StringBuilder();

        var index = password.indexOf(badchar);

        for (var position = 0; position < index; position++) {
            passwordBuilder.append(password.charAt(position));
        }
        passwordBuilder.append((char) (badchar + 1));

        var nextchar = 'a';
        var amountTimesCharUsed = 0;

        while (passwordBuilder.length() < password.length()) {
            passwordBuilder.append(nextchar);
            amountTimesCharUsed++;
            if (amountTimesCharUsed == 2) {
                nextchar++;
                amountTimesCharUsed = 0;
            }
        }

        return passwordBuilder.toString();
    }

    private boolean isPasswordValid(String password) {
        var sequenceFound = false;

        for (var c = 'a'; c <= 'x'; c++) {
            var searchBuilder = new StringBuilder();
            searchBuilder.append(c);
            searchBuilder.append((char) (c + 1));
            searchBuilder.append((char) (c + 2));
            if (password.contains(searchBuilder.toString())) {
                sequenceFound = true;
                break;
            }
        }

        if (!sequenceFound) {
            return false;
        }

        char previouschar = 0;

        var duplicates = new HashSet<Character>();

        for (int position = 0; position < password.length(); position++) {
            var c = password.charAt(position);
            if (previouschar == c) {
                if (duplicates.contains(c)) {
                    return false;
                }
                duplicates.add(c);
                if (duplicates.size() == 2) {
                    return true;
                }
            }
            previouschar = c;
        }

        return false;
    }
}
