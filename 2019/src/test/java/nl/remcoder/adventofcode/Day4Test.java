package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day4Test {

    private Day4 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day4();
    }

    @Test
    public void testPart1Case1() {
        assertEquals(1, testSubject.handlePart1(111111, 111111));
    }

    @Test
    public void testPart1Case2() {
        assertEquals(0, testSubject.handlePart1(223450, 223450));
    }

    @Test
    public void testPart1Case3() {
        assertEquals(0, testSubject.handlePart1(123789, 123789));
    }

    @Test
    public void testPart1Input() {
        System.out.println(testSubject.handlePart1(234208, 765869));
    }

    @Test
    public void testPart2Case1() {
        assertEquals(1, testSubject.handlePart2(112233, 112233));
    }

    @Test
    public void testPart2Case2() {
        assertEquals(0, testSubject.handlePart2(123444, 123444));
    }

    @Test
    public void testPart2Case3() {
        assertEquals(1, testSubject.handlePart2(111122, 111122));
    }

    @Test
    public void testPart2Input() {
        System.out.println(testSubject.handlePart2(234208, 765869));
    }
}