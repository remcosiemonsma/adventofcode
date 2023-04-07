package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day16Test {
    private Day16 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day16();
    }

    @Test
    void testPart1Case1() {
        String input = """
                       D2FE28
                       """;

        assertEquals(6, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Case2() {
        String input = """
                       8A004A801A8002F478
                       """;

        assertEquals(16, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Case3() {
        String input = """
                       620080001611562C8802118E34
                       """;

        assertEquals(12, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Case4() {
        String input = """
                       C0015000016115A2E0802F182340
                       """;

        assertEquals(23, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Case5() {
        String input = """
                       A0016C880162017C3686B18A3D4780
                       """;

        assertEquals(31, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(984, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day16/input").toURI()))));
    }

    @Test
    void testPart2Case1() {
        String input = """
                       C200B40A82
                       """;

        assertEquals(3, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Case2() {
        String input = """
                       04005AC33890
                       """;

        assertEquals(54, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Case3() {
        String input = """
                       880086C3E88112
                       """;

        assertEquals(7, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Case5() {
        String input = """
                       CE00C43D881120
                       """;

        assertEquals(9, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Case6() {
        String input = """
                       D8005AC2A8F0
                       """;

        assertEquals(1, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Case7() {
        String input = """
                       F600BC2D8F
                       """;

        assertEquals(0, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Case8() {
        String input = """
                       9C005AC2F8F0
                       """;

        assertEquals(0, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Case9() {
        String input = """
                       9C0141080250320F1802104A08
                       """;

        assertEquals(1, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(1015320896946L, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day16/input").toURI()))));
    }
}