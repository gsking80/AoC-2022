package king.greg.aoc2022;


import java.util.ArrayList;
import java.util.Collections;
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
    Collections.sort(elves);
    long value = 0;
    for (var i = 1; i <4 ; i++) {
      value += elves.get(elves.size() - i);
    }
    return value;
  }

}
