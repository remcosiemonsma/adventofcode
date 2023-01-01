package nl.remcoder.adventofcode;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day23Test {
    private Day23 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day23();
    }

    @Test
    void part1Case1() {
        String data = """
                      pos=<0,0,0>, r=4
                      pos=<1,0,0>, r=1
                      pos=<4,0,0>, r=3
                      pos=<0,2,0>, r=1
                      pos=<0,5,0>, r=3
                      pos=<0,0,3>, r=1
                      pos=<1,1,1>, r=1
                      pos=<1,1,2>, r=1
                      pos=<1,3,1>, r=1
                      """;

        assertEquals(7, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(253, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day23/input").toURI()))));
    }

    @Test
    @Ignore
    void part2Case1() {
        String data = """
                      pos=<10,12,12>, r=2
                      pos=<12,14,12>, r=2
                      pos=<16,12,12>, r=4
                      pos=<14,14,14>, r=6
                      pos=<50,50,50>, r=200
                      pos=<10,10,10>, r=5
                      """;

        assertEquals(36, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        //108618800 too low
        assertEquals(108618801, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day23/input").toURI()))));
    }
}