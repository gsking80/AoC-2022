package king.greg.aoc2022;

import java.awt.Point;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class Day12 {

  final char[][] elevationMap;
  final int width;
  final int height;
  final Point start;
  final Point end;

  public Day12(final List<String> input) {
    width = input.get(0).length();
    height = input.size();
    elevationMap = new char[height][width];
    start = new Point();
    end = new Point();
    for (var y = 0; y < height; y++) {
      for (var x = 0; x < width; x++) {
        final var location = input.get(y).charAt(x);

        if (location == 'S') {
          start.x = x;
          start.y = y;
          elevationMap[y][x] = 'a';
        } else if (location == 'E') {
          end.x = x;
          end.y = y;
          elevationMap[y][x] = 'z';
        } else {
          elevationMap[y][x] = location;
        }
      }
    }
  }

  public int fewestSteps() {
    final Set<Point> visitedPoints = new HashSet<>();
    var queue = initQueue();
    queue.add(new Node(0, start));
    Node current;
    while (!queue.isEmpty()) {
      current = queue.remove();
      if (!visitedPoints.contains(current.getLocation())) {
        visitedPoints.add(current.getLocation());
        if (end.equals(current.getLocation())) {
          return current.getStepsTaken();
        }
        queue.addAll(current.getNextSteps());
      }
    }
    return 0;
  }

  public int fewestStepsReverse() {
    final Set<Point> visitedPoints = new HashSet<>();
    var queue = initReverseQueue();
    queue.add(new Node(0, end));
    Node current;
    while (!queue.isEmpty()) {
      current = queue.remove();
      if (!visitedPoints.contains(current.getLocation())) {
        visitedPoints.add(current.getLocation());
        if ('a' == current.getElevation()) {
          return current.getStepsTaken();
        }
        queue.addAll(current.getNextStepsDown());
      }
    }
    return 0;
  }

  private PriorityQueue<Node> initQueue() {
    return new PriorityQueue<>(10,
        (arg0, arg1) -> Comparator.comparing(Node::estimatedTotalSteps).compare(arg0, arg1));
  }

  private PriorityQueue<Node> initReverseQueue() {
    return new PriorityQueue<>(10,
        (arg0, arg1) -> Comparator.comparing(Node::getStepsTaken).compare(arg0, arg1));
  }

  class Node {

    private static final List<Point> directions = Arrays.asList(new Point(-1, 0), new Point(1, 0),
        new Point(0, -1), new Point(0, 1));
    private final int stepsTaken;
    private final Point location;
    private final char elevation;

    public Node(final int stepsTaken, final Point location) {
      this.stepsTaken = stepsTaken;
      this.location = new Point(location);
      elevation = elevationMap[location.y][location.x];
    }

    public int getStepsTaken() {
      return stepsTaken;
    }

    public Point getLocation() {
      return location;
    }

    public char getElevation() {
      return elevation;
    }

    public int estimatedTotalSteps() {
      return stepsTaken + Math.abs(end.x - location.x) + Math.abs(end.y - location.y);
    }

    public Set<Node> getNextSteps() {
      final var neighbors = new HashSet<Node>();
      for (final Point direction : directions) {
        final var newX = location.x + direction.x;
        final var newY = location.y + direction.y;
        if (newX >= 0 && newX < width && newY >= 0 && newY < height) {
          final var newElevation = elevationMap[newY][newX];
          if (newElevation <= elevation + 1) {
            neighbors.add(new Node(stepsTaken + 1, new Point(newX, newY)));
          }
        }
      }
      return neighbors;
    }

    public Set<Node> getNextStepsDown() {
      final var neighbors = new HashSet<Node>();
      for (final Point direction : directions) {
        final var newX = location.x + direction.x;
        final var newY = location.y + direction.y;
        if (newX >= 0 && newX < width && newY >= 0 && newY < height) {
          final var newElevation = elevationMap[newY][newX];
          if (newElevation >= elevation - 1) {
            neighbors.add(new Node(stepsTaken + 1, new Point(newX, newY)));
          }
        }
      }
      return neighbors;
    }
  }
}
