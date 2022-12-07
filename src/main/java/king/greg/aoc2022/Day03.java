package king.greg.aoc2022;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day03 {

  private final List<String> rucksacks;

  Day03(final List<String> lines) {
    rucksacks = lines;
  }

  public long priorities() {
    long priorities = 0;
    for (final String rucksack : rucksacks) {
      final Set<Character> compartmentA = characterSet(
          rucksack.substring(0, rucksack.length() / 2));
      final Set<Character> compartmentB = characterSet(rucksack.substring(rucksack.length() / 2));
      compartmentA.retainAll(compartmentB);
      var priority = ((char) compartmentA.toArray()[0] - 96);
      priorities += priority + (priority > 0 ? 0 : 58);
    }
    return priorities;
  }

  public long badgePriorities() {
    long priorities = 0;
    for (var i = 0; i < rucksacks.size(); i += 3) {

      final Set<Character> sackA = characterSet(rucksacks.get(i));
      final Set<Character> sackB = characterSet(rucksacks.get(i + 1));
      final Set<Character> sackC = characterSet(rucksacks.get(i + 2));
      sackA.retainAll(sackB);
      sackA.retainAll(sackC);
      var priority = ((char) sackA.toArray()[0] - 96);
      priorities += priority + (priority > 0 ? 0 : 58);
    }
    return priorities;
  }

  private Set<Character> characterSet(final String string) {
    return IntStream.range(0, string.length()).mapToObj(string::charAt).collect(Collectors.toSet());
  }
}
