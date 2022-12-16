package king.greg.aoc2022;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;

public class Day16 {

  private final Map<String, Valve> valves;

  public Day16(final List<String> inputs) {
    var pattern = Pattern.compile(
        "Valve (\\w+) has flow rate=(\\d+); tunnels? leads? to valves? (.*)");
    valves = new HashMap<>();
    for (final var input : inputs) {
      final var matcher = pattern.matcher(input);
      if (matcher.find()) {
        final var key = matcher.group(1);
        final var flowRate = Long.parseLong(matcher.group(2));
        final var tunnels = Arrays.stream(matcher.group(3).split(", ")).toList();
        valves.put(key, new Valve(key, flowRate, tunnels));
      }
    }
    for (final var valve : valves.values()) {
      valve.calculateSteps();
    }
  }

  public long maxPressure(final int minutesRemaining) {
    final var valvesToOpen = valves.values().stream().filter(v -> v.flowRate > 0).map(v -> v.key)
        .collect(
            Collectors.toSet());
    var initialState = new State2("AA", valvesToOpen, new HashSet<>());
    return bestChoice(minutesRemaining, initialState);
  }

  public int maxPressureElephant(final int minutesRemaining) {
    final var valvesToOpen = valves.values().stream().filter(v -> v.flowRate > 0).map(v -> v.key)
        .toList();
    var max = 0;
    for (var i = 0; i < (1 << (valvesToOpen.size())) / 2; i++) {
      final var me = new HashSet<String>();
      final var elephant = new HashSet<String>();
      for (var j = 0; j < valvesToOpen.size(); j++) {
        if ((i & (1 << j)) > 0) {
          me.add(valvesToOpen.get(j));
        } else {
          elephant.add(valvesToOpen.get(j));
        }
      }
      var localMax =
          bestChoice(minutesRemaining, new State2("AA", me, new HashSet<>())) + bestChoice(
              minutesRemaining, new State2("AA", elephant, new HashSet<>()));
      if (localMax > max) {
        max = localMax;
      }
      memo.clear();
    }
    return max;
  }

  private final Map<Pair<Integer, State2>, Integer> memo = new HashMap<>();

  private int bestChoice(final int minutesRemaining, final State2 currentState) {
    var max = memo.get(Pair.of(minutesRemaining, currentState));
    if (null != max) {
      return max;
    }
    max = minutesRemaining * currentState.getFlowRate();
    for (final var nextState : currentState.getNexts().entrySet()) {
      var stepTime = nextState.getValue();
      if (stepTime <= minutesRemaining) {
        var mostPressure =
            (stepTime * currentState.getFlowRate()) + bestChoice(minutesRemaining - stepTime,
                nextState.getKey());
        if (mostPressure > max) {
          max = mostPressure;
        }
      }
    }
    memo.put(Pair.of(minutesRemaining, currentState), max);
    return max;
  }

  class State2 {

    private final String location;
    private final Set<String> valvesToOpen;
    private final Set<String> openValves;

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      var state2 = (State2) o;
      return Objects.equals(location, state2.location) && Objects.equals(
          valvesToOpen, state2.valvesToOpen) && Objects.equals(openValves, state2.openValves);
    }

    @Override
    public int hashCode() {
      return Objects.hash(location, valvesToOpen, openValves);
    }

    public State2(final String location, final Set<String> valvesToOpen,
        final Set<String> openValves) {
      this.location = location;
      this.valvesToOpen = valvesToOpen;
      this.openValves = openValves;
    }

    public Map<State2, Integer> getNexts() {
      final Map<State2, Integer> nexts = new HashMap<>();
      for (final var nextValve : valvesToOpen) {
        final var valvesRemaining = new HashSet<>(valvesToOpen);
        final var valvesDone = new HashSet<>(openValves);
        valvesRemaining.remove(nextValve);
        valvesDone.add(nextValve);
        var minutes = valves.get(location).stepsToValve.get(nextValve);
        nexts.put(new State2(nextValve, valvesRemaining, valvesDone), minutes + 1);
      }
      return nexts;
    }

    public int getFlowRate() {
      var rate = 0;
      for (final var openValve : openValves) {
        rate += valves.get(openValve).flowRate;
      }
      return rate;
    }
  }

  class Valve {

    private final String key;
    private final long flowRate;
    private final List<String> tunnels;

    private final Map<String, Integer> stepsToValve;

    public Valve(final String key, final long flowRate, final List<String> tunnels) {
      this.key = key;
      this.flowRate = flowRate;
      this.tunnels = tunnels;
      stepsToValve = new HashMap<>();
    }

    public void calculateSteps() {
      if (!stepsToValve.isEmpty()) {
        return;
      }
      final var queue = new ArrayDeque<Pair<Valve, Integer>>();
      queue.add(Pair.of(this, 0));
      while (!queue.isEmpty()) {
        var current = queue.removeFirst();
        for (final var valveId : current.getLeft().tunnels) {
          if (!key.equals(valveId) && !stepsToValve.containsKey(valveId)) {
            stepsToValve.put(valveId, current.getRight() + 1);
            queue.add(Pair.of(valves.get(valveId), current.getRight() + 1));
          }
        }
      }
    }
  }
}
