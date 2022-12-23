package king.greg.aoc2022;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;

public class Day23 {

  private static final Point[] adjacencyOrder;
  private static final List<Pair<Point, List<Integer>>> directionChoices;

  static {
    adjacencyOrder = new Point[]{
        new Point(-1, -1),
        new Point(0, -1),
        new Point(1, -1),
        new Point(1, 0),
        new Point(1, 1),
        new Point(0, 1),
        new Point(-1, 1),
        new Point(-1, 0)
    };

    directionChoices = Arrays.asList(
        Pair.of(new Point(0, -1), Arrays.asList(0, 1, 2)),
        Pair.of(new Point(0, 1), Arrays.asList(4, 5, 6)),
        Pair.of(new Point(-1, 0), Arrays.asList(6, 7, 0)),
        Pair.of(new Point(1, 0), Arrays.asList(2, 3, 4))
    );
  }

  Set<Point> elfLocations;

  public Day23(final List<String> input) {
    elfLocations = new HashSet<>();
    for (var y = 0; y < input.size(); y++) {
      var line = input.get(y);
      for (var x = 0; x < line.length(); x++) {
        if (line.charAt(x) == '#') {
          elfLocations.add(new Point(x, y));
        }
      }
    }
  }

  public long smallestRectangleSpaces(final int rounds) {
    var shouldMove = true;
    for (var i = 0; shouldMove && i < rounds; i++) {
      shouldMove = performRound(i);
    }
    return calculateEmptySpaces();
  }

  public int calculateRounds() {
    var shouldMove = true;
    var roundCount = 0;
    while (shouldMove) {
      shouldMove = performRound(roundCount++);
    }
    return roundCount;
  }

  private boolean performRound(final int round) {
    //  Part 1
    final Map<Point, List<Point>> proposedMoves = new HashMap<>();
    for (final var elf : elfLocations) {
      var proposedLocation = proposeMove(elf, round);
      if (null != proposedLocation) {
        proposedMoves.computeIfAbsent(proposedLocation, k -> new ArrayList<>());
        proposedMoves.get(proposedLocation).add(elf);
      }
    }

    //  Part 2
    for (final var proposedMove : proposedMoves.entrySet()) {
      if (proposedMove.getValue().size() == 1) {
        elfLocations.remove(proposedMove.getValue().get(0));
        elfLocations.add(proposedMove.getKey());
      }
    }

    return proposedMoves.size() > 0;
  }

  private Point proposeMove(final Point elf, final int round) {
    var neighbors = neighbors(elf);
    if (neighbors.contains(true)) {  // move
      for (var choice = 0; choice < 4; choice++) {
        var currentChoice = directionChoices.get((choice + round) % 4);
        var move = true;
        for (var testPosition : currentChoice.getRight()) {
          if (Boolean.TRUE.equals(neighbors.get(testPosition))) {
            move = false;
            break;
          }
        }
        if (move) {
          return new Point(elf.x + currentChoice.getLeft().x, elf.y + currentChoice.getLeft().y);
        }
      }
    }
    return null;
  }

  private List<Boolean> neighbors(final Point elf) {
    final var neighbors = new ArrayList<Boolean>(8);
    for (var i = 0; i < 8; i++) {
      neighbors.add(elfLocations.contains(
          new Point(elf.x + adjacencyOrder[i].x, elf.y + adjacencyOrder[i].y)));
    }
    return neighbors;
  }

  private long calculateEmptySpaces() {
    var minX = elfLocations.stream().mapToInt(p -> p.x).min().orElse(0);
    var maxX = elfLocations.stream().mapToInt(p -> p.x).max().orElse(0);
    var minY = elfLocations.stream().mapToInt(p -> p.y).min().orElse(0);
    var maxY = elfLocations.stream().mapToInt(p -> p.y).max().orElse(0);

    long area = (long) (1 + maxX - minX) * (1 + maxY - minY);
    return area - elfLocations.size();
  }
}
