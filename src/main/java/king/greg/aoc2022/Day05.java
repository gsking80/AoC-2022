package king.greg.aoc2022;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Day05 {

  private final List<Deque<Character>> stacks;
  private final List<String> instructions;

  Day05(final List<String> lines) {
    stacks = new ArrayList<>();
    instructions = new ArrayList<>();
    var searching = true;
    for (var i = 0; i < lines.size(); i++) {
      var currentLine = lines.get(i);
      if (searching && currentLine.isBlank()) {
        searching = false;
        String[] countStrings = lines.get(i - 1).split("\s+");
        buildCrates(lines.subList(0, i - 1),
            Integer.parseInt(countStrings[countStrings.length - 1]));
      } else if (!searching) {
        instructions.add(currentLine);
      }
    }
  }

  private void buildCrates(List<String> levels, final int stackCount) {
    for (var i = 0; i < stackCount; i++) {
      stacks.add(new ArrayDeque<>());
    }
    for (var level = levels.size() - 1; level >= 0; level--) {
      String currentLevel = levels.get(level);
      for (var k = 0; k < stacks.size(); k++) {
        final var index = 1 + (4 * k);
        if (index < currentLevel.length()) {
          final var crate = currentLevel.charAt(index);
          if (crate != ' ') {
            stacks.get(k).addLast(crate);
          }
        }
      }
    }
  }

  public String topCrates9000() {
    for (String instruction : instructions) {
      String[] pieces = instruction.split("\s+");
      var amount = Integer.parseInt(pieces[1]);
      var source = Integer.parseInt(pieces[3]) - 1;
      var target = Integer.parseInt(pieces[5]) - 1;
      for (var i = 0; i < amount; i++) {
        stacks.get(target).addLast(stacks.get(source).removeLast());
      }
    }
    final var result = new StringBuilder();
    for (final var stack : stacks) {
      result.append(stack.getLast());
    }
    return result.toString();
  }

  public String topCrates9001() {
    for (String instruction : instructions) {
      String[] pieces = instruction.split("\s+");
      var amount = Integer.parseInt(pieces[1]);
      var source = Integer.parseInt(pieces[3]) - 1;
      var target = Integer.parseInt(pieces[5]) - 1;
      final Deque<Character> tempStack = new ArrayDeque<>();
      for (var i = 0; i < amount; i++) {
        tempStack.addLast(stacks.get(source).removeLast());
      }
      while (!tempStack.isEmpty()) {
        stacks.get(target).addLast(tempStack.removeLast());
      }
    }
    final var result = new StringBuilder();
    for (final var stack : stacks) {
      result.append(stack.getLast());
    }
    return result.toString();
  }
}
