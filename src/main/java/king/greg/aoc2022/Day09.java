package king.greg.aoc2022;

import java.awt.Point;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day09 {

  private final List<String> steps;

  public Day09(final List<String> input) {
    steps = input;
  }

  public long tailPositions(final int knots) {
    final var rope = new Rope(knots);
    for (final var step : steps) {
      String[] parts = step.split("\s+");
      rope.move(parts[0], Integer.parseInt(parts[1]));
    }
    return rope.totalTailLocations();
  }

  static class Rope {

    private static final Map<String, Point> directions;

    static {
      directions = Map.of("U", new Point(0, 1), "D", new Point(0, -1), "L", new Point(-1, 0), "R",
          new Point(1, 0));
    }

    final Point[] knots;
    private final Set<Point> tailLocations;

    public Rope(final int numberOfKnots) {
      knots = new Point[numberOfKnots];
      for (var i = 0; i < numberOfKnots; i++) {
        knots[i] = new Point(0, 0);
      }
      tailLocations = new HashSet<>();
      tailLocations.add(new Point(knots[numberOfKnots - 1]));
    }

    public void move(final String direction, final int distance) {
      final Point delta = directions.get(direction);
      for (var i = 0; i < distance; i++) {
        knots[0].x += delta.x;
        knots[0].y += delta.y;
        for (var j = 1; j < knots.length; j++) {
          var leadDelta = new Point(knots[j - 1].x - knots[j].x, knots[j - 1].y - knots[j].y);
          if (Math.abs(leadDelta.x) > 1 || Math.abs(leadDelta.y) > 1) {
            knots[j].x += Math.max(-1, Math.min(1, leadDelta.x));
            knots[j].y += Math.max(-1, Math.min(1, leadDelta.y));
          }
        }
        tailLocations.add(new Point(knots[knots.length - 1]));
      }
    }

    public int totalTailLocations() {
      return tailLocations.size();
    }
  }
}
