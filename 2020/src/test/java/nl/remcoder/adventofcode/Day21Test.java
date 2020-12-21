package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class Day21Test {
    private Day21 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day21();
    }

    @Test
    public void testPart1Case1() {
        String data = """
                mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
                trh fvjkl sbzzf mxmxvkd (contains dairy)
                sqjhc fvjkl (contains soy)
                sqjhc mxmxvkd sbzzf (contains fish)
                """;

        Stream<String> input = data.lines();

        assertEquals(5, testSubject.handlePart1(input));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(2412, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day21/input").toURI()))));
    }

    @Test
    public void testPart2Case1() {
        String data = """
                mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
                trh fvjkl sbzzf mxmxvkd (contains dairy)
                sqjhc fvjkl (contains soy)
                sqjhc mxmxvkd sbzzf (contains fish)
                """;

        Stream<String> input = data.lines();

        assertEquals("mxmxvkd,sqjhc,fvjkl", testSubject.handlePart2(input));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals("mfp,mgvfmvp,nhdjth,hcdchl,dvkbjh,dcvrf,bcjz,mhnrqp", testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day21/input").toURI()))));
    }
}