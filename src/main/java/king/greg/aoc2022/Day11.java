package king.greg.aoc2022;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

public class Day11 {

  final List<Monkey> monkeys;

  public Day11(final List<String> input) {
    monkeys = new ArrayList<>();
    long lcm = 1;
    for (var i = 0; i < input.size(); i += 7) {
      final var operationSymbol = input.get(i + 2).charAt(23);
      final var operationValue = input.get(i + 2).substring(25);
      final var divisor = Long.parseLong(input.get(i + 3).substring(21));
      lcm *= divisor;
      final var trueDest = Integer.parseInt(input.get(i + 4).substring(29));
      final var falseDest = Integer.parseInt(input.get(i + 5).substring(30));
      final var monkey = new Monkey(operationSymbol, operationValue, divisor, trueDest,
          falseDest);
      final String[] itemParts = input.get(i + 1).substring(18).split(", +");
      for (final String itemPart : itemParts) {
        monkey.catchItem(Long.parseLong(itemPart));
      }
      monkeys.add(monkey);
    }
    for (final var monkey : monkeys) {
      monkey.setLcm(lcm);
    }
  }

  public long monkeyBusiness(final int rounds, final boolean reduceWorry) {
    for (var i = 0; i < rounds; i++) {
      for (final Monkey monkey : monkeys) {
        monkey.takeTurn(monkeys, reduceWorry);
      }
    }

    return monkeys.stream().mapToLong(Monkey::getInspectionCount).boxed()
        .sorted(Collections.reverseOrder()).limit(2).reduce(1L, (a, b) -> (a * b));
  }

  static class Monkey {

    private final Deque<Long> items;
    private final char operationSymbol;
    private final String operationValue;
    private final long divisor;
    private final int trueDest;
    private final int falseDest;
    private long lcm;
    private long inspectionCount;

    public Monkey(final char operationSymbol, final String operationValue, final long divisor,
        final int trueDest, final int falseDest) {
      this.items = new ArrayDeque<>();
      this.operationSymbol = operationSymbol;
      this.operationValue = operationValue;
      this.divisor = divisor;
      this.trueDest = trueDest;
      this.falseDest = falseDest;
      this.inspectionCount = 0L;
    }

    public void takeTurn(final List<Monkey> monkeys, final boolean reduceWorry) {
      while (!items.isEmpty()) {
        inspectionCount++;
        var currentWorry = (adjustWorry(items.removeFirst(), reduceWorry));
        if (currentWorry % divisor == 0L) {
          monkeys.get(trueDest).catchItem(currentWorry);
        } else {
          monkeys.get(falseDest).catchItem(currentWorry);
        }
      }
    }

    private long adjustWorry(final long worryLevel, final boolean reduceWorry) {
      long adjustValue = "old".equals(operationValue) ? worryLevel : Long.parseLong(operationValue);
      return ((operationSymbol == '*') ? worryLevel * adjustValue : worryLevel + adjustValue) / (
          reduceWorry ? 3L : 1L) % lcm;
    }

    public void catchItem(final long item) {
      items.addLast(item);
    }

    public long getInspectionCount() {
      return inspectionCount;
    }

    public void setLcm(final long lcm) {
      this.lcm = lcm;
    }
  }
}
