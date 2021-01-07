package nl.remcoder.adventofcode;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class Day11 {
    public String handlePart1(Stream<String> input) {
        String password = input.findFirst().get();

        if (password.contains("i")) {
            char forbiddenChar = 'i';
            password = generatePasswordWithoutForbiddenChar(password, forbiddenChar);
        } else if (password.contains("o")) {
            char forbiddenChar = 'o';
            password = generatePasswordWithoutForbiddenChar(password, forbiddenChar);
        } else if (password.contains("l")) {
            char forbiddenChar = 'l';
            password = generatePasswordWithoutForbiddenChar(password, forbiddenChar);
        }

        while (!isPasswordValid(password)) {
            password = generateNextPassword(password);
        }

        return password;
    }

    private String generateNextPassword(String password) {
        StringBuilder passwordBuilder = new StringBuilder();
        char lastChar = password.charAt(password.length() - 1);
        if (lastChar < 'z') {
            for (int position = 0; position < password.length() - 1; position++) {
                passwordBuilder.append(password.charAt(position));
            }
            char nextChar = (char) (lastChar + 1);

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
        StringBuilder passwordBuilder = new StringBuilder();

        int index = password.indexOf(badchar);

        for (int position = 0; position < index; position++) {
            passwordBuilder.append(password.charAt(position));
        }
        passwordBuilder.append((char) (badchar + 1));

        char nextchar = 'a';
        int amountTimesCharUsed = 0;

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
        boolean sequenceFound = false;

        for (char c = 'a'; c <= 'x'; c++) {
            StringBuilder searchBuilder = new StringBuilder();
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

        Set<Character> duplicates = new HashSet<>();

        for (int position = 0; position < password.length(); position++) {
            char c = password.charAt(position);
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

    public String handlePart2(Stream<String> input) {
        String previousPassword = handlePart1(input);

        String password = generateNextPassword(previousPassword);

        while (!isPasswordValid(password)) {
            password = generateNextPassword(password);
        }

        return password;
    }
}
