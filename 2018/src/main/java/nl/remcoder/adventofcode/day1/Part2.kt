package nl.remcoder.adventofcode.day1

import java.nio.file.Files
import java.nio.file.Paths
import java.util.HashSet
import java.util.stream.Collectors

fun main(args: Array<String>) {
    val frequencyShifts = Files.lines(Paths.get(ClassLoader.getSystemResource("day1/input").toURI()))
            .map { Integer.parseInt(it) }
            .collect(Collectors.toList())

    var frequency = 0

    val frequencies = HashSet<Int>()

    var frequencyCounter = 0

    while (frequencies.add(frequency)) {
        frequency += frequencyShifts[frequencyCounter]

        frequencyCounter++
        if (frequencyCounter >= frequencyShifts.size) {
            frequencyCounter = 0
        }
    }

    println(frequency)
}