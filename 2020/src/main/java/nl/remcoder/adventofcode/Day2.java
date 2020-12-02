package nl.remcoder.adventofcode;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Day2 {

    public long handlePart1(Stream<String> input) {
        return input.map(this::mapStringToPassword)
                    .filter(this::isPasswordValidPart1)
                    .count();
    }

    public long handlePart2(Stream<String> input) {
        return input.map(this::mapStringToPassword)
                    .filter(this::isPasswordValidPart2)
                    .count();
    }

    public Password mapStringToPassword(String password) {
        String[] split = password.split(": ");
        return new Password(new PasswordRule(split[0]), split[1]);
    }

    private boolean isPasswordValidPart1(Password password) {
        Integer amount = password.passwordMap.get(password.passwordRule.c);
        if (amount != null){
            return amount >= password.passwordRule.minAmount && amount <= password.passwordRule.maxAmount;
        } else {
            return false;
        }
    }

    private boolean isPasswordValidPart2(Password password) {
        if (password.password.length() < password.passwordRule.minAmount) {
            return false;
        } else if (password.password.length() < password.passwordRule.maxAmount) {
            return password.password.charAt(password.passwordRule.minAmount - 1) == password.passwordRule.c;
        } else {
            return password.password.charAt(password.passwordRule.minAmount - 1) == password.passwordRule.c ^
                   password.password.charAt(password.passwordRule.maxAmount - 1) == password.passwordRule.c;
        }
    }

    private static class Password {
        final PasswordRule passwordRule;
        final Map<Character, Integer> passwordMap;
        final String password;

        private Password(PasswordRule passwordRule, String password) {
            this.passwordRule = passwordRule;
            this.password = password;
            this.passwordMap = password.chars()
                                          .collect((Supplier<HashMap<Character, Integer>>) HashMap::new,
                                                   this::countCharInMap,
                                                   this::combineMaps);
        }

        private void combineMaps(Map<Character, Integer> mapToKeep,
                                 Map<Character, Integer> obsoleteMap) {
            for (char c : obsoleteMap.keySet()) {
                mapToKeep.merge(c, obsoleteMap.get(c), Integer::sum);
            }
        }

        private void countCharInMap(Map<Character, Integer> map, int character) {
            map.compute((char) character, (key, amount) -> amount != null ? amount + 1 : 1);
        }
    }

    private static class PasswordRule {
        final int minAmount;
        final int maxAmount;
        final char c;

        private PasswordRule(String passwordRule) {
            String[] ruleSplit = passwordRule.split(" ");
            c = ruleSplit[1].charAt(0);
            String[] amountSplit = ruleSplit[0].split("-");
            this.minAmount = Integer.parseInt(amountSplit[0]);
            this.maxAmount = Integer.parseInt(amountSplit[1]);
        }
    }
}
