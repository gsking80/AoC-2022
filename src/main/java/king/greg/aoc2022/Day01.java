package king.greg.aoc2022;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Day01 {

  private final List<Long> elves = new ArrayList<>();

  Day01(final List<String> lines) {
    long currCount = 0;
    for (final String line : lines) {
      if (line.isBlank()) {
        elves.add(currCount);
        currCount = 0;
        continue;
      }
      currCount += Long.parseLong(line);
    }
  }

  long topCalories() {
    return elves.stream().mapToLong(v -> v).max().orElse(0);
  }

  long topThreeCalories() {
    return elves.stream().sorted(Comparator.reverseOrder()).mapToLong(v -> v).limit(3).sum();
  }
}
