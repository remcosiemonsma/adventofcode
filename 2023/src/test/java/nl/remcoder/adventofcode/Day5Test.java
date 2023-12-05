package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day5Test {
    private Day5 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day5();
    }

    @Test
    void part1Case1() {
        String data = """
                      seeds: 79 14 55 13
                      
                      seed-to-soil map:
                      50 98 2
                      52 50 48
                      
                      soil-to-fertilizer map:
                      0 15 37
                      37 52 2
                      39 0 15
                      
                      fertilizer-to-water map:
                      49 53 8
                      0 11 42
                      42 0 7
                      57 7 4
                      
                      water-to-light map:
                      88 18 7
                      18 25 70
                      
                      light-to-temperature map:
                      45 77 23
                      81 45 19
                      68 64 13
                      
                      temperature-to-humidity map:
                      0 69 1
                      1 0 69
                      
                      humidity-to-location map:
                      60 56 37
                      56 93 4
                      """;

        assertEquals(35, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(331445006, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day5/input").toURI()))));
    }

    @Test
    void part2Case1() {
        String data = """
                      seeds: 79 14 55 13
                      
                      seed-to-soil map:
                      50 98 2
                      52 50 48
                      
                      soil-to-fertilizer map:
                      0 15 37
                      37 52 2
                      39 0 15
                      
                      fertilizer-to-water map:
                      49 53 8
                      0 11 42
                      42 0 7
                      57 7 4
                      
                      water-to-light map:
                      88 18 7
                      18 25 70
                      
                      light-to-temperature map:
                      45 77 23
                      81 45 19
                      68 64 13
                      
                      temperature-to-humidity map:
                      0 69 1
                      1 0 69
                      
                      humidity-to-location map:
                      60 56 37
                      56 93 4
                      """;

        assertEquals(46, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(6472060, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day5/input").toURI()))));
    }
}