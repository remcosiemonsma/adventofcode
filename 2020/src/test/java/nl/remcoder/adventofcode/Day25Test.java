package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class Day25Test {
    private Day25 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day25();
    }

    @Test
    public void testPart1Case1() {
        String data = """
                5764801
                17807724
                """;

        Stream<String> input = data.lines();

        assertEquals(14897079, testSubject.handlePart1(input));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(11707042, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day25/input").toURI()))));
    }
}