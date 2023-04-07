package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day22Test {
    private Day22 testSubject;

    @BeforeEach
    public void setUp() {
        testSubject = new Day22();
    }

    @Test
    void testPart1Case1() {
        testSubject.setPlayerHitPoints(10);
        testSubject.setPlayerMana(250);
        
        String input = """
                       Hit Points: 13
                       Damage: 8
                       """;

        assertEquals(226, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Case2() {
        testSubject.setPlayerHitPoints(10);
        testSubject.setPlayerMana(250);

        String input = """
                       Hit Points: 14
                       Damage: 8
                       """;

        assertEquals(641, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        testSubject.setPlayerHitPoints(50);
        testSubject.setPlayerMana(500);
        
        assertEquals(900, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day22/input").toURI()))));
    }

    @Test
    void testPart2Input() throws Exception {
        testSubject.setPlayerHitPoints(50);
        testSubject.setPlayerMana(500);
        
        assertEquals(1216, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day22/input").toURI()))));
    }
}