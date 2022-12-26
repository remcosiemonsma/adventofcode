package nl.remcoder.adventofcode.asembunny;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class CPU {
    private final BlockingQueue<Integer> output = new ArrayBlockingQueue<>(100000);
    private int registera = 0;
    private int registerb = 0;
    private int registerc = 0;
    private int registerd = 0;
    private boolean running;

    public int getRegistera() {
        return registera;
    }

    public void setRegistera(int registera) {
        this.registera = registera;
    }

    public int getRegisterb() {
        return registerb;
    }

    public void setRegisterb(int registerb) {
        this.registerb = registerb;
    }

    public int getRegisterc() {
        return registerc;
    }

    public void setRegisterc(int registerc) {
        this.registerc = registerc;
    }

    public int getRegisterd() {
        return registerd;
    }

    public void setRegisterd(int registerd) {
        this.registerd = registerd;
    }

    public void stop() {
        this.running = false;
    }

    public BlockingQueue<Integer> getOutput() {
        return output;
    }

    public void performOperations(String[][] instructions) {
        running = true;
        var instructionCounter = 0;

        while (instructionCounter < instructions.length && running) {
            var parts = instructions[instructionCounter];

            switch (parts[0]) {
                case "cpy" -> {
                    performCopyOperation(parts[1], parts[2]);
                    instructionCounter++;
                }
                case "inc" -> {
                    performIncrementOperation(parts[1]);
                    instructionCounter++;
                }
                case "dec" -> {
                    performDecrementOperation(parts[1]);
                    instructionCounter++;
                }
                case "jnz" -> {
                    int value = getValue(parts[1]);
                    if (value != 0) {
                        instructionCounter += getValue(parts[2]);
                    } else {
                        instructionCounter++;
                    }
                }
                case "tgl" -> {
                    int value = getValue(parts[1]);
                    int index = instructionCounter + value;
                    if (index > 0 && index < instructions.length) {
                        String[] instruction = instructions[index];
                        instruction[0] = switch (instruction[0]) {
                            case "cpy" -> "jnz";
                            case "inc" -> "dec";
                            case "dec" -> "inc";
                            case "jnz" -> "cpy";
                            case "tgl" -> "inc";
                            default -> throw new AssertionError("Eek!");
                        };
                    }
                    instructionCounter++;
                }
                case "out" -> {
                    int value = getValue(parts[1]);
                    output.add(value);
                    instructionCounter++;
                }
            }
        }
    }

    private void performDecrementOperation(String register) {
        switch (register) {
            case "a" -> registera--;
            case "b" -> registerb--;
            case "c" -> registerc--;
            case "d" -> registerd--;
        }
    }

    private void performIncrementOperation(String register) {
        switch (register) {
            case "a" -> registera++;
            case "b" -> registerb++;
            case "c" -> registerc++;
            case "d" -> registerd++;
        }
    }

    private void performCopyOperation(String sourceRegister, String targetRegister) {
        var value = getValue(sourceRegister);
        switch (targetRegister) {
            case "a" -> registera = value;
            case "b" -> registerb = value;
            case "c" -> registerc = value;
            case "d" -> registerd = value;
        }
    }

    private int getValue(String register) {
        var value = 0;

        if (register.charAt(0) == '-' || Character.isDigit(register.charAt(0))) {
            value = Integer.parseInt(register);
        } else {
            value = switch (register) {
                case "a" -> registera;
                case "b" -> registerb;
                case "c" -> registerc;
                case "d" -> registerd;
                default -> value;
            };
        }
        return value;
    }
}
