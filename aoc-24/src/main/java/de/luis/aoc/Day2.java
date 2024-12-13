package de.luis.aoc;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 * Problem 2:
 * <br>
 * The unusual data (your puzzle input) consists of many reports, one report per line.
 * Each report is a list of numbers called levels that are separated by spaces. For example:
 * <ul>
 *     <li> 7 6 4 2 1</li>
 *     <li>1 2 7 8 9</li>
 *     <li>9 7 6 2 1</li>
 *     <li>1 3 2 4 5</li>
 *     <li>8 6 4 4 1</li>
 *     <li>1 3 6 7 9</li>
 * </ul>
 * This example data contains six reports each containing five levels.

 * The engineers are trying to figure out which reports are safe. The Red-Nosed reactor
 * safety systems can only tolerate levels that are either gradually increasing or gradually
 * decreasing. So, a report only counts as safe if both of the following are true:
 * The levels are either all increasing or all decreasing.
 * Any two adjacent levels differ by at least one and at most three.
 * In the example above, the reports can be found safe or unsafe by checking those rules:
 * <ul>
 *     <li>7 6 4 2 1: Safe because the levels are all decreasing by 1 or 2.</li>
 *     <li>1 2 7 8 9: Unsafe because 2 7 is an increase of 5.</li>
 *     <li>9 7 6 2 1: Unsafe because 6 2 is a decrease of 4.</li>
 *     <li>1 3 2 4 5: Unsafe because 1 3 is increasing but 3 2 is decreasing.</li>
 *     <li>8 6 4 4 1: Unsafe because 4 4 is neither an increase or a decrease.</li>
 *     <li>1 3 6 7 9: Safe because the levels are all increasing by 1, 2, or 3.</li>
 * </ul>
 * So, in this example, 2 reports are safe.
 *<br>
 * Part 2:
 * <br>
 * Now, the same rules apply as before, except if removing a single level
 * from an unsafe report would make it safe, the report instead counts as safe.
 */
public class Day2 {

    public static void main(String[] args) {
        var input = getInputFromFile("input.txt");
        System.out.printf("[PART A] Result: %d", countSafeReports(input));
        System.out.printf("[PART B] Result: %d", countSafeReportsPartTwo(input));
    }

    /* ========== DAY 2 - PART ONE ========== */

    private static int countSafeReports(List<List<Integer>> lists) {
        int count = 0;
        for (var list : lists) {
            if (isSafe(list)) {
                count++;
            }
        }
        return count;
    }

    private static boolean isSafe(List<Integer> list) {
        return isDecreasing(list) || isIncreasing(list);
    }

    private static boolean isDecreasing(List<Integer> list) {
        return IntStream.range(1, list.size())
                .allMatch(i -> {
                    int diff = list.get(i - 1) - list.get(i);
                    return diff == 1 || diff == 2 || diff == 3;
                });
    }

    private static boolean isIncreasing(List<Integer> list) {
        return IntStream.range(1, list.size())
                .allMatch(i -> {
                    int diff = list.get(i) - list.get(i - 1);
                    return diff == 1 || diff == 2 || diff == 3;
                });
    }

    /* ========== DAY 2 - PART TWO ========== */

    private static int countSafeReportsPartTwo(List<List<Integer>> lists) {
        int count = 0;
        for (var list : lists) {
            if (isSafeAfterRemovingOne(list)) {
                count++;
            }
        }
        return count;
    }

    private static boolean isSafeAfterRemovingOne(List<Integer> list) {
        // already safe?
        if (isSafe(list)) {
            return true;
        }

        return IntStream.range(0, list.size())
                .anyMatch(i -> {
                    var mod = IntStream.range(0, list.size())
                            .filter(j -> j != i)
                            .mapToObj(list::get)
                            .toList();

                    return isSafe(mod);
                });
    }

    /* ========== DAY 2 - UTILITY ========== */

    private static @NotNull List<List<Integer>> getInputFromFile(String fileName) {
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            return stream.map(line -> List.of(line.split(" ")).stream()
                            .map(Integer::parseInt)
                            .collect(Collectors.toList()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String listToString(List<Integer> list) {
        return list.stream().map(String::valueOf).collect(Collectors.joining(",", "[", "]"));
    }
}
