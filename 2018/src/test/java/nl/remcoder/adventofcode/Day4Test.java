package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day4Test {
    private Day4 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day4();
    }

    @Test
    void part1Case1() {
        String data = """
                      [1518-11-01 00:00] Guard #10 begins shift
                      [1518-11-01 00:05] falls asleep
                      [1518-11-01 00:25] wakes up
                      [1518-11-01 00:30] falls asleep
                      [1518-11-01 00:55] wakes up
                      [1518-11-01 23:58] Guard #99 begins shift
                      [1518-11-02 00:40] falls asleep
                      [1518-11-02 00:50] wakes up
                      [1518-11-03 00:05] Guard #10 begins shift
                      [1518-11-03 00:24] falls asleep
                      [1518-11-03 00:29] wakes up
                      [1518-11-04 00:02] Guard #99 begins shift
                      [1518-11-04 00:36] falls asleep
                      [1518-11-04 00:46] wakes up
                      [1518-11-05 00:03] Guard #99 begins shift
                      [1518-11-05 00:45] falls asleep
                      [1518-11-05 00:55] wakes up
                      """;

        assertEquals(240, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(8421, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2018/day4/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      [1518-11-01 00:00] Guard #10 begins shift
                      [1518-11-01 00:05] falls asleep
                      [1518-11-01 00:25] wakes up
                      [1518-11-01 00:30] falls asleep
                      [1518-11-01 00:55] wakes up
                      [1518-11-01 23:58] Guard #99 begins shift
                      [1518-11-02 00:40] falls asleep
                      [1518-11-02 00:50] wakes up
                      [1518-11-03 00:05] Guard #10 begins shift
                      [1518-11-03 00:24] falls asleep
                      [1518-11-03 00:29] wakes up
                      [1518-11-04 00:02] Guard #99 begins shift
                      [1518-11-04 00:36] falls asleep
                      [1518-11-04 00:46] wakes up
                      [1518-11-05 00:03] Guard #99 begins shift
                      [1518-11-05 00:45] falls asleep
                      [1518-11-05 00:55] wakes up
                      """;

        assertEquals(4455, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(83359, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2018/day4/input"))));
    }
}