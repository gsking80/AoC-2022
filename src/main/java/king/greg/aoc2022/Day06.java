package king.greg.aoc2022;

import java.util.HashMap;

public class Day06 {

  private Day06(){}

  public static int marker(final String input, final int distinct) {
    final var lastSeen = new HashMap<Character, Integer>();
    var count = 0;
    for (var marker = 0; marker < input.length(); marker++) {
      final Character current = input.charAt(marker);
      var last = lastSeen.get(current);
      if (null == last || last < marker - count) {
        count++;
      } else {
        count = marker - last;
      }
      lastSeen.put(current, marker);
      if (count == distinct) {
        return marker + 1;
      }
    }

    return -1;
  }

}
