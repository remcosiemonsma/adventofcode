package nl.remcoder.adventofcode;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day4 {
    private static final Pattern BIRTH_YEAR_REGEX = Pattern.compile("byr:(19[2-9]\\d|200[0-2])( |$)");
    private static final Pattern ISSUE_YEAR_REGEX = Pattern.compile("iyr:20(1\\d|20)( |$)");
    private static final Pattern EXPIRATION_YEAR_REGEX = Pattern.compile("eyr:20(2\\d|30)( |$)");
    private static final Pattern HEIGHT_REGEX = Pattern.compile("hgt:(1([5-8]\\d|9[0-3])cm|(59|6\\d|7[0-6])in)( |$)");
    private static final Pattern HAIR_COLOR_REGEX = Pattern.compile("hcl:#[0-9a-f]{6}( |$)");
    private static final Pattern EYE_COLOR_REGEX = Pattern.compile("ecl:(amb|blu|brn|gry|grn|hzl|oth)( |$)");
    private static final Pattern PASSPORT_ID_REGEX = Pattern.compile("pid:\\d{9}( |$)");

    public long handlePart1(Stream<String> input) {
        List<String> passPortLines = input.collect(Collectors.toList());

        long validPasswords = 0;

        Passport passport = new Passport();

        for (String line : passPortLines) {
            if (line.isEmpty()) {
                if (isPassportValid(passport)) {
                    validPasswords++;
                }
                passport = new Passport();
            } else {
                if (line.contains("byr")) {
                    passport.birthYear = true;
                }
                if (line.contains("iyr")) {
                    passport.issueYear = true;
                }
                if (line.contains("eyr")) {
                    passport.expirationYear = true;
                }
                if (line.contains("hgt")) {
                    passport.height = true;
                }
                if (line.contains("hcl")) {
                    passport.hairColor = true;
                }
                if (line.contains("ecl")) {
                    passport.eyeColor = true;
                }
                if (line.contains("pid")) {
                    passport.passportId = true;
                }
            }
        }

        if (isPassportValid(passport)) {
            validPasswords++;
        }

        return validPasswords;
    }

    public long handlePart2(Stream<String> input) {
        List<String> passPortLines = input.collect(Collectors.toList());

        long validPasswords = 0;

        Passport passport = new Passport();

        for (String line : passPortLines) {
            if (line.isEmpty()) {
                if (isPassportValid(passport)) {
                    validPasswords++;
                }
                passport = new Passport();
            } else {
                if (BIRTH_YEAR_REGEX.matcher(line).find()) {
                    passport.birthYear = true;
                }
                if (ISSUE_YEAR_REGEX.matcher(line).find()) {
                    passport.issueYear = true;
                }
                if (EXPIRATION_YEAR_REGEX.matcher(line).find()) {
                    passport.expirationYear = true;
                }
                if (HEIGHT_REGEX.matcher(line).find()) {
                    passport.height = true;
                }
                if (HAIR_COLOR_REGEX.matcher(line).find()) {
                    passport.hairColor = true;
                }
                if (EYE_COLOR_REGEX.matcher(line).find()) {
                    passport.eyeColor = true;
                }
                if (PASSPORT_ID_REGEX.matcher(line).find()) {
                    passport.passportId = true;
                }
            }
        }

        if (isPassportValid(passport)) {
            validPasswords++;
        }

        return validPasswords;
    }

    private boolean isPassportValid(Passport passport) {
        return passport.birthYear &&
               passport.issueYear &&
               passport.expirationYear &&
               passport.height &&
               passport.hairColor &&
               passport.eyeColor &&
               passport.passportId;
    }

    private static class Passport {
        private boolean birthYear;
        private boolean issueYear;
        private boolean expirationYear;
        private boolean height;
        private boolean hairColor;
        private boolean eyeColor;
        private boolean passportId;
    }
}
