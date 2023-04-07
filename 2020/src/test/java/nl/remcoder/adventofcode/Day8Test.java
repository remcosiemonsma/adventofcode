package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day8Test {
    private Day8 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day8();
    }

    @Test
    public void testPart1Case1() {
        String input = """
                       nop +0
                       acc +1
                       jmp +4
                       acc +3
                       jmp -3
                       acc -99
                       acc +1
                       jmp -4
                       acc +6
                       """;

        assertEquals(5, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(1087, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day8/input").toURI()))));
    }

    @Test
    public void testPart2Case1() {
        String input = """
                       nop +0
                       acc +1
                       jmp +4
                       acc +3
                       jmp -3
                       acc -99
                       acc +1
                       jmp -4
                       acc +6
                       """;

        assertEquals(8, testSubject.handlePart2(input.lines()));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(780, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day8/input").toURI()))));
    }
}