package king.greg.aoc2022;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;

public class Day24 {

  static final Set<Point> directions;

  static {
    directions = Set.of(
        new Point(1, 0),
        new Point(0, 1),
        new Point(-1, 0),
        new Point(0, -1),
        new Point(0, 0));
  }

  final int width;
  final int height;
  final boolean[][] lefts;
  final boolean[][] rights;
  final boolean[][] ups;
  final boolean[][] downs;
  Point start;
  Point end;

  public Day24(final List<String> input) {
    height = input.size() - 2;
    width = input.get(0).length() - 2;
    lefts = new boolean[width][height];
    rights = new boolean[width][height];
    ups = new boolean[width][height];
    downs = new boolean[width][height];

    for (var y = 0; y < height; y++) {
      var line = input.get(y + 1);
      for (var x = 0; x < width; x++) {
        switch (line.charAt(x + 1)) {
          case '^' -> ups[x][y] = true;
          case 'v' -> downs[x][y] = true;
          case '<' -> lefts[x][y] = true;
          case '>' -> rights[x][y] = true;
        }
      }
    }

    start = new Point(input.get(0).indexOf('.') - 1, -1);
    end = new Point(input.get(input.size() - 1).indexOf('.') - 1, height);
  }

  public int fewestMinutes() {
    return fewestMinutes(1);
  }

  public int fewestMinutesWithSnacks() {
    return fewestMinutes(3);
  }

  private int fewestMinutes(final int numberOfTrips) {
    var minutes = 0;
    State initial;
    Set<State> visited;
    PriorityQueue<State> priorityQueue;

    for (var i = 0; i < numberOfTrips; i++) {
      initial = new State(start, minutes);
      visited = new HashSet<>();
      priorityQueue = initQueue();
      priorityQueue.add(initial);

      while (!priorityQueue.isEmpty()) {
        var current = priorityQueue.remove();
        if (!visited.contains(current)) {
          visited.add(current);
          if (current.location.equals(end)) {
            minutes = current.minutes;
            break;
          }
          priorityQueue.addAll(current.nextStates());
        }
      }
      var temp = start;
      start = end;
      end = temp;
    }

    return minutes;
  }

  private PriorityQueue<State> initQueue() {
    return new PriorityQueue<>(Comparator.comparingInt(State::predictedTime));
  }

  class State {

    final Point location;
    final int minutes;
    final List<Point> previous;

    public State(final Point location, final int minutes) {
      this.location = location;
      this.minutes = minutes;
      previous = new ArrayList<>();
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
      return minutes == state.minutes && Objects.equals(location, state.location);
    }

    @Override
    public int hashCode() {
      return Objects.hash(location, minutes);
    }

    public int predictedTime() {
      return minutes + Math.abs(end.x - location.x) + Math.abs(end.y - location.y);
    }

    public Set<State> nextStates() {
      final var nextStates = new HashSet<State>();
      final var nextMinute = minutes + 1;
      for (final var offset : directions) {
        var nextLocation = new Point(location.x + offset.x, location.y + offset.y);
        if (nextLocation.equals(end) || nextLocation.equals(start)) {
          var stateToAdd = new State(nextLocation, nextMinute);
          stateToAdd.previous.addAll(this.previous);
          stateToAdd.previous.add(new Point(location));
          nextStates.add(stateToAdd);
          continue;
        }
        final var possibleState = new State(nextLocation, nextMinute);
        if (possibleState.isValid()) {
          nextStates.add(possibleState);
        }
      }
      return nextStates;
    }

    public boolean isValid() {
      if (location.x < width && location.x >= 0 && location.y < height && location.y >= 0) {
        var leftIndex = (location.x + minutes) % width;
        var rightIndex = (location.x - minutes) % width;
        rightIndex = rightIndex < 0 ? rightIndex + width : rightIndex;
        var upIndex = (location.y + minutes) % height;
        var downIndex = (location.y - minutes) % height;
        downIndex = downIndex < 0 ? downIndex + height : downIndex;
        return !lefts[leftIndex][location.y] &&
            !rights[rightIndex][location.y] &&
            !ups[location.x][upIndex] &&
            !downs[location.x][downIndex];
      }
      return false;
    }

    @Override
    public String toString() {
      return "State{" +
          "location=" + location +
          ", minutes=" + minutes +
          '}';
    }
  }
}
