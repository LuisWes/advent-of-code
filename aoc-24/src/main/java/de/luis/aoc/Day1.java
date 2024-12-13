package de.luis.aoc;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Problem 1:
 * <br>
 * Throughout the Chief's office, the historically significant locations are
 * listed not by name but by a unique number called the location ID. To make
 * sure they don't miss anything, The Historians split into two groups, each
 * searching the office and trying to create their own complete list of
 * location IDs.
 *<br>
 * There's just one problem: by holding the two lists up side by side
 * (your puzzle input), it quickly becomes clear that the lists aren't very
 * similar. Maybe you can help The Historians reconcile their lists?
 *<br>
 * For example:
 * <ul>
 *     <li>3   4</li>
 *     <li>4   3</li>
 *     <li>2   5</li>
 *     <li>1   3</li>
 *     <li>3   9</li>
 *     <li>3   3</li>
 * </ul>
 * Maybe the lists are only off by a small amount! To find out, pair up the
 * numbers and measure how far apart they are. Pair up the smallest number in
 * the left list with the smallest number in the right list, then the second-smallest
 * left number with the second-smallest right number, and so on.
 *
 * Within each pair, figure out how far apart the two numbers are; you'll need to add
 * up all of those distances. For example, if you pair up a 3 from the left list with
 * a 7 from the right list, the distance apart is 4; if you pair up a 9 with a 3, the
 * distance apart is 6.
 *
 * In the example list above, the pairs and distances would be as follows:
 *
 * The smallest number in the left list is 1, and the smallest number in the right list
 * is 3. The distance between them is 2.
 * The second-smallest number in the left list is 2, and the second-smallest number in
 * the right list is another 3. The distance between them is 1.
 * The third-smallest number in both lists is 3, so the distance between them is 0.
 * The next numbers to pair up are 3 and 4, a distance of 1.
 * The fifth-smallest numbers in each list are 3 and 5, a distance of 2.
 * Finally, the largest number in the left list is 4, while the largest number in the right
 * list is 9; these are a distance 5 apart.
 * <br>
 * To find the total distance between the left list and the right list, add up the distances
 * between all of the pairs you found. In the example above, this is 2 + 1 + 0 + 1 + 2 + 5,
 * a total distance of 11!
 * <br>
 * Part 2:
 * <br>
 * This time, you'll need to figure out exactly how often each number from the left list
 * appears in the right list. Calculate a total similarity score by adding up each number in the
 * left list after multiplying it by the number of times that number appears in the right list.
 *
 * Here are the same example lists again:
 * <br>
 * <ul>
 *     <li>3   4</li>
 *     <li>4   3</li>
 *     <li>2   5</li>
 *     <li>1   3</li>
 *     <li>3   9</li>
 *     <li>3   3</li>
 * </ul>
 * For these example lists, here is the process of finding the similarity score:
 *<br>
 * The first number in the left list is 3. It appears in the right list three times, so the similarity score increases by 3 * 3 = 9.
 * The second number in the left list is 4. It appears in the right list once, so the similarity score increases by 4 * 1 = 4.
 * The third number in the left list is 2. It does not appear in the right list, so the similarity score does not increase (2 * 0 = 0).
 * The fourth number, 1, also does not appear in the right list.
 * The fifth number, 3, appears in the right list three times; the similarity score increases by 9.
 * The last number, 3, appears in the right list three times; the similarity score again increases by 9.
 * So, for these example lists, the similarity score at the end of this process is 31 (9 + 4 + 0 + 0 + 9 + 9).
 */
public class Day1 {

    public static void main(String[] args) {
        var entry = pairsFromFile("input.txt");
        System.out.printf("[PART A] Result: %d", findDistance(entry.getKey(), entry.getValue()));
        System.out.printf("[PART B] Result: %d", calculateSimilarityScore(entry.getKey(), entry.getValue()));
    }


    /* ========== DAY 1 - PART ONE ========== */

    private static int findDistance(List<Integer> first, List<Integer> second) {
        Collections.sort(first);
        Collections.sort(second);

        int distance = 0;
        for (int i = 0; i < first.size(); i++) {
            distance += Math.abs(first.get(i) - second.get(i));
        }
        return distance;
    }

    /* ========== DAY 1 - PART TWO ========== */

    @Contract(pure = true)
    private static long calculateSimilarityScore(@NotNull List<Integer> first, @NotNull List<Integer> second) {
        long score = 0;
        for (Integer elem : first) {
            score += elem * countOccurrences(second, elem);
        }
        return score;
    }

    private static long countOccurrences(@NotNull List<Integer> list, int i) {
        return list.stream().filter(elem -> elem == i).count();
    }


    /* ========== DAY 1 - UTILITY ========== */

    private static Map.@NotNull Entry<List<Integer>, List<Integer>> pairsFromFile(String fileName) {
        try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
            List<List<Integer>> lists = lines
                    .map(line -> line.split(" {3}"))
                    .map(split -> new int[] { Integer.parseInt(split[0]), Integer.parseInt(split[1]) } )
                    .collect(
                            Collectors.teeing(
                                    Collectors.mapping(pair -> pair[0], Collectors.toList()),
                                    Collectors.mapping(pair -> pair[1], Collectors.toList()),
                                    List::of
                            )
                    );
            return new AbstractMap.SimpleImmutableEntry<>(lists.get(0), lists.get(1));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}