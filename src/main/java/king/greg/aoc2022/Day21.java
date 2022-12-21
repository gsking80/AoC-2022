package king.greg.aoc2022;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day21 {

  final Map<String, Monkey> monkeyMap;

  public Day21(final List<String> input) {
    monkeyMap = new HashMap<>();
    for (final var line : input) {
      final var parts = line.split(": ");
      monkeyMap.put(parts[0], new Monkey(parts[1]));
    }
  }

  public long calculateRoot() {
    return monkeyMap.get("root").getAnswer();
  }

  public long calculateNumberToYell() {
    var human = monkeyMap.get("humn");
    human.human = true;
    human.number = null;
    return monkeyMap.get("root").solveHuman();
  }

  class Monkey {

    String operation;
    String left;
    String right;
    Long number;
    boolean human = false;

    public Monkey(final String input) {
      try {
        number = Long.parseLong(input);
      } catch (final NumberFormatException nfe) {
        var parts = input.split(" ");
        left = parts[0];
        right = parts[2];
        operation = parts[1];
      }
    }

    public long getAnswer() {
      if (null != number) {
        return number;
      }
      final var leftAnswer = monkeyMap.get(left).getAnswer();
      final var rightAnswer = monkeyMap.get(right).getAnswer();
      switch (operation) {
        case "+":
          return leftAnswer + rightAnswer;
        case "-":
          return leftAnswer - rightAnswer;
        case "*":
          return leftAnswer * rightAnswer;
        case "/":
          return leftAnswer / rightAnswer;
        default:
      }
      throw new UnsupportedOperationException("Unsupported");
    }

    private boolean containsHuman() {
      if (human) {
        return true;
      }
      if (null != number) {
        return false;
      }
      return monkeyMap.get(left).containsHuman() || monkeyMap.get(right).containsHuman();
    }

    public long solveHuman() {
      var leftMonkey = monkeyMap.get(left);
      var rightMonkey = monkeyMap.get(right);
      if (leftMonkey.containsHuman()) {
        return leftMonkey.solve(rightMonkey.getAnswer());
      }
      return rightMonkey.solve(leftMonkey.getAnswer());
    }

    public long solve(final long answer) {
      if (human) {
        return answer;
      }
      var leftMonkey = monkeyMap.get(left);
      var rightMonkey = monkeyMap.get(right);
      if (leftMonkey.containsHuman()) {
        switch (operation) {
          case "+":
            return leftMonkey.solve(answer - rightMonkey.getAnswer());
          case "-":
            return leftMonkey.solve(answer + rightMonkey.getAnswer());
          case "*":
            return leftMonkey.solve(answer / rightMonkey.getAnswer());
          case "/":
            return leftMonkey.solve(answer * rightMonkey.getAnswer());
          default:
        }
      } else {
        switch (operation) {
          case "+":
            return rightMonkey.solve(answer - leftMonkey.getAnswer());
          case "-":
            return rightMonkey.solve(leftMonkey.getAnswer() - answer);
          case "*":
            return rightMonkey.solve(answer / leftMonkey.getAnswer());
          case "/":
            return rightMonkey.solve(leftMonkey.getAnswer() / answer);
          default:
        }
      }
      throw new UnsupportedOperationException("Unsupported");
    }
  }
}
