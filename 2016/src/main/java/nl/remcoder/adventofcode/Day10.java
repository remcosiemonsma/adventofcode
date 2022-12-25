package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day10 implements AdventOfCodeSolution<Integer> {
    private static final Pattern VALUE_PATTERN = Pattern.compile("value (\\d+) goes to bot (\\d+)");
    private static final Pattern INSTRUCTION_PATTERN = Pattern.compile("bot (\\d+) gives low to (bot|output) (\\d+) " +
                                                                       "and high to (bot|output) (\\d+)");

    @Override
    public Integer handlePart1(Stream<String> input) {
        var bots = new HashMap<Integer, Bot>();
        var outputs = new HashMap<Integer, Output>();

        processInstructions(input, bots, outputs);

        return bots.values()
                   .stream()
                   .filter(bot -> (bot.value1 == 61 && bot.value2 == 17) || (bot.value1 == 17 && bot.value2 == 61))
                   .findFirst()
                   .map(Bot::getId)
                   .orElse(0);
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var bots = new HashMap<Integer, Bot>();
        var outputs = new HashMap<Integer, Output>();

        processInstructions(input, bots, outputs);

        return outputs.values()
                      .stream()
                      .filter(output -> output.id == 0 || output.id == 1 || output.id == 2)
                      .mapToInt(Output::getValue)
                      .reduce((i1, i2) -> i1 * i2)
                      .orElse(0);
    }

    private void processInstructions(Stream<String> input, Map<Integer, Bot> bots, Map<Integer, Output> outputs) {
        input.forEach(s -> {
            if (s.startsWith("value")) {
                handleValuePattern(bots, s);
            } else {
                handleInstructionPattern(bots, outputs, s);
            }
        });
    }

    private void handleInstructionPattern(Map<Integer, Bot> bots, Map<Integer, Output> outputs, String s) {
        var matcher = INSTRUCTION_PATTERN.matcher(s);

        if (matcher.matches()) {
            var botid = Integer.parseInt(matcher.group(1));
            var isLowOutput = "output".equals(matcher.group(2));
            var lowId = Integer.parseInt(matcher.group(3));
            var isHighOutput = "output".equals(matcher.group(4));
            var highId = Integer.parseInt(matcher.group(5));

            Bot bot;
            if (bots.containsKey(botid)) {
                bot = bots.get(botid);
            } else {
                bot = new Bot(botid);
                bots.put(botid, bot);
            }

            var lowValueHandler = getValueHandler(bots, outputs, isLowOutput, lowId);

            var highValueHandler = getValueHandler(bots, outputs, isHighOutput, highId);

            bot.setLowValueHandler(lowValueHandler);
            bot.setHighValueHandler(highValueHandler);
        } else {
            throw new AssertionError("Aak!");
        }
    }

    private void handleValuePattern(Map<Integer, Bot> bots, String s) {
        var matcher = VALUE_PATTERN.matcher(s);

        if (matcher.matches()) {
            var value = Integer.parseInt(matcher.group(1));
            var botid = Integer.parseInt(matcher.group(2));

            Bot bot;
            if (bots.containsKey(botid)) {
                bot = bots.get(botid);
            } else {
                bot = new Bot(botid);
                bots.put(botid, bot);
            }

            bot.handleValue(value);
        } else {
            throw new AssertionError("Ook!");
        }
    }

    private ValueHandler getValueHandler(Map<Integer, Bot> bots, Map<Integer, Output> outputs,
                                         boolean isOutput, int id) {
        ValueHandler highValueHandler;
        if (isOutput) {
            if (outputs.containsKey(id)) {
                highValueHandler = outputs.get(id);
            } else {
                highValueHandler = new Output(id);
                outputs.put(id, (Output) highValueHandler);
            }
        } else {
            if (bots.containsKey(id)) {
                highValueHandler = bots.get(id);
            } else {
                highValueHandler = new Bot(id);
                bots.put(id, (Bot) highValueHandler);
            }
        }
        return highValueHandler;
    }

    private interface ValueHandler {
        void handleValue(int value);
    }

    private static class Output implements ValueHandler {
        private final int id;
        private int value;

        public Output(int id) {
            this.id = id;
        }

        public int getValue() {
            return value;
        }

        @Override
        public void handleValue(int value) {
            this.value = value;
        }
    }

    private static class Bot implements ValueHandler {
        private final int id;
        private Integer value1;
        private Integer value2;
        private ValueHandler lowValueHandler;
        private ValueHandler highValueHandler;

        public Bot(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        @Override
        public void handleValue(int value) {
            if (value1 == null) {
                value1 = value;
            } else if (value2 == null) {
                value2 = value;
            } else {
                throw new AssertionError("Eek!");
            }

            if (readyToSend()) {
                sendValues();
            }
        }

        public void setLowValueHandler(ValueHandler lowValueHandler) {
            this.lowValueHandler = lowValueHandler;
            if (readyToSend()) {
                sendValues();
            }
        }

        public void setHighValueHandler(ValueHandler highValueHandler) {
            this.highValueHandler = highValueHandler;
            if (readyToSend()) {
                sendValues();
            }
        }

        private boolean readyToSend() {
            return value1 != null && value2 != null && lowValueHandler != null && highValueHandler != null;
        }

        private void sendValues() {
            if (value1 < value2) {
                lowValueHandler.handleValue(value1);
                highValueHandler.handleValue(value2);
            } else {
                lowValueHandler.handleValue(value2);
                highValueHandler.handleValue(value1);
            }
        }
    }
}
