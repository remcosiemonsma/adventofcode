package nl.remcoder.adventofcode;

public class Day4 {
    public int handlePart1(int start, int end) {
        int amountMatching = 0;

        for (int password = start; password <= end; password++) {
            String passwordString = Integer.toString(password);

            if (passwordString.length() == 6 && doesPasswordContainSameAdjacentDigits(passwordString) &&
                doesPasswordContainOnlyIncreasingDigits(passwordString)) {
                amountMatching++;
            }
        }

        return amountMatching;
    }

    public int handlePart2(int start, int end) {
        int amountMatching = 0;

        for (int password = start; password <= end; password++) {
            String passwordString = Integer.toString(password);

            if (passwordString.length() == 6 && doesPasswordContainSameAdjacentDigitsWithNoTriplets(passwordString) &&
                doesPasswordContainOnlyIncreasingDigits(passwordString)) {
                amountMatching++;
            }
        }

        return amountMatching;
    }

    private boolean doesPasswordContainSameAdjacentDigitsWithNoTriplets(String password) {
        char[] charArray = password.toCharArray();

        char c0 = charArray[0];
        char c1 = charArray[1];
        char c2 = charArray[2];
        char c3 = charArray[3];
        char c4 = charArray[4];
        char c5 = charArray[5];

        if (c0 == c1 && c1 != c2) {
            return true;
        }
        if (c1 == c2 && c2 != c3 && c1 != c0) {
            return true;
        }
        if (c2 == c3 && c3 != c4 && c2 != c1) {
            return true;
        }
        if (c3 == c4 && c4 != c5 && c3 != c2) {
            return true;
        }
        if (c4 == c5 && c4 != c3) {
            return true;
        }

        return false;
    }

    private boolean doesPasswordContainSameAdjacentDigits(String password) {
        char[] charArray = password.toCharArray();

        for (int i = 0; i < charArray.length - 1; i++) {
            char c1 = charArray[i];
            char c2 = charArray[i + 1];

            if (c1 == c2) {
                return true;
            }
        }

        return false;
    }

    private boolean doesPasswordContainOnlyIncreasingDigits(String password) {
        char[] charArray = password.toCharArray();
        for (int i = 0; i < charArray.length - 1; i++) {
            char c1 = charArray[i];
            char c2 = charArray[i + 1];

            if (c2 < c1) {
                return false;
            }
        }

        return true;
    }
}
