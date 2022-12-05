package nl.remcoder.adventofcode;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.*;

public class Day6Test {
    private Day6 testSubject;

    @Before
    public void setUp() {
        testSubject = new Day6();
    }

    @Test
    public void part1Case1() {
        String data = "eedadn\n" +
                      "drvtee\n" +
                      "eandsr\n" +
                      "raavrd\n" +
                      "atevrs\n" +
                      "tsrnev\n" +
                      "sdttsa\n" +
                      "rasrtv\n" +
                      "nssdts\n" +
                      "ntnada\n" +
                      "svetve\n" +
                      "tesnvt\n" +
                      "vntsnd\n" +
                      "vrdear\n" +
                      "dvrsen\n" +
                      "enarar";

        assertEquals("easter", testSubject.handlePart1(data.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals("qoclwvah", testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day6/input").toURI()))));
    }

    @Test
    public void part2Case1() {
        String data = "eedadn\n" +
                      "drvtee\n" +
                      "eandsr\n" +
                      "raavrd\n" +
                      "atevrs\n" +
                      "tsrnev\n" +
                      "sdttsa\n" +
                      "rasrtv\n" +
                      "nssdts\n" +
                      "ntnada\n" +
                      "svetve\n" +
                      "tesnvt\n" +
                      "vntsnd\n" +
                      "vrdear\n" +
                      "dvrsen\n" +
                      "enarar";

        assertEquals("advent", testSubject.handlePart2(data.lines()));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals("ryrgviuv", testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day6/input").toURI()))));
    }
}