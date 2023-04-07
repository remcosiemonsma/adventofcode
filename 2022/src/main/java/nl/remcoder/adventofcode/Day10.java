package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.BiAdventOfCodeSolution;
import nl.remcoder.adventofcode.library.drawing.Screen;

import java.util.List;
import java.util.stream.Stream;

public class Day10 implements BiAdventOfCodeSolution<Integer, String> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        CPU cpu = new CPU(input.toList());
        
        int signalStrength = 0;
        
        for (int i = 1; i <= 220; i++) {
            if (i == 20 || i == 60 || i == 100 || i == 140 || i == 180 || i == 220) {
                signalStrength += cpu.getRegister() * i;
            }
            cpu.cycle();
        }
        
        return signalStrength;
    }

    @Override
    public String handlePart2(Stream<String> input) {
        CPU cpu = new CPU(input.toList());

        for (int i = 0; i < 240; i++) {
            cpu.cycle();
        }
        
        return cpu.getScreen().readScreen();
    }
    
    private static class CPU {
        private final List<String> instructions;
        private final Screen screen = new Screen(40, 6);
        private int counter;
        private int cycle;
        private Mode mode;
        private int register;
        private int screenCounter = 0;
        private int line = 0;
        
        private CPU(List<String> instructions) {
            this.instructions = instructions;
            counter = 0;
            cycle = 0;
            register = 1;
            setMode();
        }

        public void cycle() {
            draw();
            switch (mode) {
                case NOOP -> doNoop();
                case ADDX -> doAddx();
            }
        }

        public int getRegister() {
            return register;
        }

        public Screen getScreen() {
            return screen;
        }

        private void draw() {
            if (shouldDraw()) {
                screen.drawPixel(screenCounter, line);
            }
            screenCounter++;
            if (screenCounter == 40) {
                line ++;
                screenCounter = 0;
            }
        }

        private boolean shouldDraw() {
            return register - 1 == screenCounter || 
                   register == screenCounter || 
                   register + 1 == screenCounter;
        }
        
        private void doAddx() {
            cycle++;
            if (cycle == 2) {
                register += Integer.parseInt(instructions.get(counter).split(" ")[1]);
                counter++;
                cycle = 0;
                setMode();
            }
        }

        private void doNoop() {
            counter++;
            setMode();
        }

        private void setMode() {
            String instruction;
            if (counter < instructions.size()) {
                instruction = instructions.get(counter);
            } else {
                instruction = "noop";
            }
            String[] parts = instruction.split(" ");
            switch (parts[0]) {
                case "noop" -> mode = Mode.NOOP;
                case "addx" -> mode = Mode.ADDX;
                default -> throw new AssertionError("Eek!");
            }
        }
        
        private enum Mode {
            NOOP,
            ADDX
        }
    }
}
