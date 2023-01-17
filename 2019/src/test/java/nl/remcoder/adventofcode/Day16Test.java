package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day16Test {
    private Day16 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day16();
    }

    @Test
    void testPart1Case1() {
        var input = "12345678";
        
        testSubject.setSteps(4);

        assertEquals(List.of(0, 1, 0, 2, 9, 4, 9, 8), testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Case2() {
        var input = "80871224585914546619083218645595";

        testSubject.setSteps(100);
        
        assertEquals(List.of(2, 4, 1, 7, 6, 1, 7, 6), testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Case3() {
        var input = "19617804207202209144916044189917";

        testSubject.setSteps(100);
        
        assertEquals(List.of(7, 3, 7, 4, 5, 4, 1, 8), testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Case4() {
        var input = "69317163492948606335995924319873";

        testSubject.setSteps(100);

        assertEquals(List.of(5, 2, 4, 3, 2, 1, 3, 3), testSubject.handlePart1(input.lines()));
    }
    
    @Test
    void testPart1Input() throws Exception {
        testSubject.setSteps(100);
        
        assertEquals(List.of(7, 6, 7, 9, 5, 8, 8, 8), testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day16/input").toURI()))));
    }

    @Test
    void testPart2Case1() {
        var input = "03036732577212944063491565474664";

        assertEquals(List.of(8, 4, 4, 6, 2, 0, 2, 6), testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Case2() {
        var input = "02935109699940807407585447034323";

        assertEquals(List.of(7, 8, 7, 2, 5, 2, 7, 0), testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Case3() {
        var input = "03081770884921959731165446850517";

        assertEquals(List.of(5, 3, 5, 5, 3, 7, 3, 1), testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(List.of(8, 4, 0, 2, 4, 1, 2, 5), testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day16/input").toURI()))));
    }
}