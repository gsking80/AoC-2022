package king.greg.aoc2022;

import java.util.ArrayList;
import java.util.List;

public class Day04 {

  private final List<long[]> sections = new ArrayList<>();

  Day04(final List<String> input) {
    for (final String in : input) {
      String[] stringArray = in.split("[,-]");
      var longArray = new long[4];
      for (var i = 0; i < 4; i++) {
        longArray[i] = Long.parseLong(stringArray[i]);
      }
      sections.add(longArray);
    }
  }

  public long fullContainment() {
    long answer = 0;
    for (final long[] section : sections) {
      if (((section[0] >= section[2]) && (section[1] <= section[3])) ||
          ((section[0] <= section[2]) && (section[1] >= section[3]))) {
        answer++;
      }
    }
    return answer;
  }

  public long partialOverlap() {
    long answer = 0;
    for (final long[] section : sections) {
      if ((section[0] <= section[3]) && (section[2] <= section[1])) {
        answer++;
      }
    }
    return answer;
  }
}
