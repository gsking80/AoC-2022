package king.greg.aoc2022;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class Day19 {

  final List<Integer[]> blueprints;
  final Map<State, Integer> memo;

  public Day19(final List<String> input) {
    final var pattern = Pattern.compile(
        "Blueprint (\\d+): Each ore robot costs (\\d+) ore. Each clay robot costs (\\d+) ore. Each obsidian robot costs (\\d+) ore and (\\d+) clay. Each geode robot costs (\\d+) ore and (\\d+) obsidian.");
    blueprints = new ArrayList<>();
    memo = new HashMap<>();
    for (final var line : input) {
      final var matcher = pattern.matcher(line);
      if (matcher.matches()) {
        var blueprint = new Integer[7];
        for (var i = 0; i < 7; i++) {
          blueprint[i] = Integer.parseInt(matcher.group(i + 1));
        }
        blueprints.add(blueprint);
      }
    }
  }

  public int totalQualityLevel(final int minutes) {
    var qualityLevel = 0;
    for (final var blueprint : blueprints) {
      var maxGeodes = maxGeodes(new State(blueprint, minutes));
      qualityLevel += blueprint[0] * maxGeodes;
    }
    return qualityLevel;
  }

  public int mostGeodes(final int blueprintIndex, final int minutes) {
    return maxGeodes(new State(blueprints.get(blueprintIndex), minutes));
  }

  private int maxGeodes(final State state) {

    final var queue = new ArrayDeque<State>();
    final var visited = new HashSet<State>();
    queue.addLast(state);

    var maxGeodes = 0;

    while (!queue.isEmpty()) {
      var current = queue.removeFirst();
      if (current.bestPossible() < maxGeodes) {
        continue;
      }
      if (maxGeodes < current.crackedGeodes) {
        maxGeodes = current.crackedGeodes;
      }
      if (current.timeRemaining != 0 && !visited.contains(current)) {
        visited.add(current);
        for (final var nextState : current.nextStates()) {
          queue.addLast(nextState);
        }
      }
    }
    return maxGeodes;
  }

  static class State {

    final Integer[] blueprint;
    int timeRemaining;
    int oreRobots;
    int collectedOre;
    int clayRobots;
    int collectedClay;
    int obsidianRobots;
    int collectedObsidian;
    int geodeRobots;
    int crackedGeodes;

    public State(final Integer[] blueprint, final int timeRemaining) {
      this.blueprint = blueprint;
      oreRobots = 1;
      this.timeRemaining = timeRemaining;
    }

    public State(final State state) {
      this.blueprint = state.blueprint;
      this.timeRemaining = state.timeRemaining;
      this.oreRobots = state.oreRobots;
      this.collectedOre = state.collectedOre;
      this.clayRobots = state.clayRobots;
      this.collectedClay = state.collectedClay;
      this.obsidianRobots = state.obsidianRobots;
      this.collectedObsidian = state.collectedObsidian;
      this.geodeRobots = state.geodeRobots;
      this.crackedGeodes = state.crackedGeodes;
    }

    private State doNothing() {
      var nextState = new State(this);
      nextState.tick();
      return nextState;
    }

    private State buildOreRobot() {
      var nextState = new State(this);
      nextState.collectedOre -= blueprint[1];
      nextState.tick();
      nextState.oreRobots++;
      return nextState;
    }

    private State buildClayRobot() {
      var nextState = new State(this);
      nextState.collectedOre -= blueprint[2];
      nextState.tick();
      nextState.clayRobots++;
      return nextState;
    }

    private State buildObsidianRobot() {
      var nextState = new State(this);
      nextState.collectedOre -= blueprint[3];
      nextState.collectedClay -= blueprint[4];
      nextState.tick();
      nextState.obsidianRobots++;
      return nextState;
    }

    private State buildGeodeRobot() {
      var nextState = new State(this);
      nextState.collectedOre -= blueprint[5];
      nextState.collectedObsidian -= blueprint[6];
      nextState.tick();
      nextState.geodeRobots++;
      return nextState;
    }

    private boolean shouldBuildOreRobot() {
      return Math.max(Math.max(Math.max(blueprint[1], blueprint[2]), blueprint[3]), blueprint[5])
          > oreRobots;
    }

    private boolean shouldBuildClayRobot() {
      return blueprint[4] > clayRobots;
    }

    private boolean shouldBuildObsidianRobot() {
      return blueprint[6] > obsidianRobots;
    }

    private int bestPossible() {
      var maxGeodeRobots = geodeRobots + timeRemaining - 1;
      return crackedGeodes + (maxGeodeRobots * (maxGeodeRobots + 1) / 2) - (
          (geodeRobots - 1) * (geodeRobots) / 2);
    }

    public List<State> nextStates() {
      final var nextStates = new ArrayList<State>();
      //Options
      var worthWaiting = false;
      if (canBuildGeodeRobot()) {
        nextStates.add(buildGeodeRobot());
      } else {
        if (shouldBuildObsidianRobot()) {
          if (canBuildObsidianRobot()) {
            nextStates.add(buildObsidianRobot());
          } else {
            worthWaiting = true;
          }
        }
        if (shouldBuildClayRobot()) {
          if (canBuildClayRobot()) {
            nextStates.add(buildClayRobot());
          } else {
            worthWaiting = true;
          }
        }
        if (shouldBuildOreRobot()) {
          if (canBuildOreRobot()) {
            nextStates.add(buildOreRobot());
          } else {
            worthWaiting = true;
          }
        }
        if (worthWaiting) {
          nextStates.add(doNothing());
        }
      }
      return nextStates;
    }

    private boolean canBuildOreRobot() {
      return collectedOre >= blueprint[1];
    }

    private boolean canBuildClayRobot() {
      return collectedOre >= blueprint[2];
    }

    private boolean canBuildObsidianRobot() {
      return collectedOre >= blueprint[3] && collectedClay >= blueprint[4];
    }

    private boolean canBuildGeodeRobot() {
      return collectedOre >= blueprint[5] && collectedObsidian >= blueprint[6];
    }

    private void tick() {
      timeRemaining--;
      collectedOre += oreRobots;
      collectedClay += clayRobots;
      collectedObsidian += obsidianRobots;
      crackedGeodes += geodeRobots;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      var state = (State) o;
      return timeRemaining == state.timeRemaining && oreRobots == state.oreRobots
          && collectedOre == state.collectedOre && clayRobots == state.clayRobots
          && collectedClay == state.collectedClay && obsidianRobots == state.obsidianRobots
          && collectedObsidian == state.collectedObsidian && geodeRobots == state.geodeRobots
          && crackedGeodes == state.crackedGeodes && Arrays.equals(blueprint,
          state.blueprint);
    }

    @Override
    public int hashCode() {
      int result = Objects.hash(timeRemaining, oreRobots, collectedOre, clayRobots, collectedClay,
          obsidianRobots, collectedObsidian, geodeRobots, crackedGeodes);
      result = 31 * result + Arrays.hashCode(blueprint);
      return result;
    }
  }
}
