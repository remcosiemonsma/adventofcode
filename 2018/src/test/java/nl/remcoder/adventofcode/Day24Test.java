package nl.remcoder.adventofcode;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day24Test {
    private Day24 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day24();
    }

    @Test
    void part1Case1() {
        String data = """
                      Immune System:
                      17 units each with 5390 hit points (weak to radiation, bludgeoning) with an attack that does 4507 fire damage at initiative 2
                      989 units each with 1274 hit points (immune to fire; weak to bludgeoning, slashing) with an attack that does 25 slashing damage at initiative 3
                                            
                      Infection:
                      801 units each with 4706 hit points (weak to radiation) with an attack that does 116 bludgeoning damage at initiative 1
                      4485 units each with 2961 hit points (immune to radiation; weak to fire, cold) with an attack that does 12 slashing damage at initiative 4
                      """;

        assertEquals(5216, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        //26099 too low
        assertEquals(253, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day24/input").toURI()))));
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
                Files.lines(Paths.get(ClassLoader.getSystemResource("day24/input").toURI()))));
    }
}