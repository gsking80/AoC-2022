package king.greg.aoc2022;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.tuple.Pair;

public class Day22 {

  public static final char RIGHT = 'R';
  public static final char LEFT = 'L';
  static final Map<Integer, Point> directionMap;

  static {
    directionMap = Map.of(0, new Point(1, 0),
        1, new Point(0, 1),
        2, new Point(-1, 0),
        3, new Point(0, -1));
  }

  final Map<Point, Character> map;
  final String path;

  final Map<Pair<Point, Integer>, Pair<Point, Integer>> transferMap;

  final int length;

  public Day22(final List<String> input) {
    map = new HashMap<>();
    path = input.get(input.size() - 1);
    for (var y = 1; y < input.size() - 1; y++) {
      final String line = input.get(y - 1);
      for (var x = 1; x <= line.length(); x++) {
        final var space = line.charAt(x - 1);
        if (space != ' ') {
          map.put(new Point(x, y), space);
        }
      }
    }
    transferMap = new HashMap<>();
    length = (int) Math.sqrt((double) map.size() / 6);
  }

  public int getPassword() {
    var position = getStart();
    var direction = 0;
    var distance = 0;
    for (var i = 0; i < path.length(); i++) {
      final var next = path.charAt(i);
      switch (next) {
        case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' ->
            distance = (distance * 10) + (next - '0');
        case RIGHT, LEFT -> {
          position = move(position, direction, distance);
          distance = 0;
          direction = turn(direction, next);
        }
        default -> throw new UnsupportedOperationException();
      }
    }
    position = move(position, direction, distance);

    return (1000 * position.y) + (4 * position.x) + direction;
  }

  public int getPassword2() {

    zipCube();

    var position = getStart();
    var direction = 0;
    var distance = 0;
    for (var i = 0; i < path.length(); i++) {
      final var next = path.charAt(i);
      switch (next) {
        case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' ->
            distance = (distance * 10) + (next - '0');
        case RIGHT, LEFT -> {
          var result = move3d(position, direction, distance, false);
          position = result.getLeft();
          direction = result.getRight();
          distance = 0;
          direction = turn(direction, next);
        }
        default -> throw new UnsupportedOperationException();
      }
    }
    position = move(position, direction, distance);
    return (1000 * position.y) + (4 * position.x) + direction;
  }

  private void zipCube() {
    final var xPanels = map.keySet().stream().mapToInt(p -> p.x).max().orElse(0) / length;
    final var yPanels = map.keySet().stream().mapToInt(p -> p.y).max().orElse(0) / length;

    final var unknownTransfers = new ArrayDeque<Pair<Point, Integer>>();
    for (var y = 0; y < yPanels; y++) {
      for (var x = 0; x < xPanels; x++) {
        var upperLeft = new Point((x * length) + 1, (y * length) + 1);
        final var panel = new Point(x, y);
        if (map.get(upperLeft) == null) {
          continue;
        }
        for (var direction = 0; direction < 4; direction++) {
          var step = directionMap.get(direction);
          if (null == map.get(
              new Point(upperLeft.x + (length * step.x), upperLeft.y + (length * step.y)))) {
            unknownTransfers.addLast(Pair.of(panel, direction));
          }
        }
      }
    }
    while (!unknownTransfers.isEmpty()) {
      var currentEdge = unknownTransfers.removeFirst();
      if (transferMap.get(currentEdge) == null && !canZip(currentEdge)) {
        unknownTransfers.addLast(currentEdge);
      }
    }
  }

  private boolean canZip(final Pair<Point, Integer> currentEdge) {
    var panel = currentEdge.getLeft();
    var direction = currentEdge.getRight();
    var minX = (panel.x * length) + 1;
    var maxX = minX + length - 1;
    var minY = (panel.y * length) + 1;
    var maxY = minY + length - 1;
    Pair<Point, Integer> test;
    switch (direction) {
      case 0 -> {
        test = testCorner(new Point(maxX, minY), 0, true);
        test = test != null ? test : testCorner(new Point(maxX, maxY), 0, false);
      }
      case 1 -> {
        test = testCorner(new Point(maxX, maxY), 1, true);
        test = test != null ? test : testCorner(new Point(minX, maxY), 1, false);
      }
      case 2 -> {
        test = testCorner(new Point(minX, maxY), 2, true);
        test = test != null ? test : testCorner(new Point(minX, minY), 2, false);
      }
      case 3 -> {
        test = testCorner(new Point(minX, minY), 3, true);
        test = test != null ? test : testCorner(new Point(maxX, minY), 3, false);
      }
      default -> throw new UnsupportedOperationException();
    }
    if (test == null) {
      return false;
    }
    var nextPanel = getPanel(test.getLeft());
    transferMap.put(Pair.of(panel, direction), Pair.of(nextPanel, (test.getRight() + 2) % 4));
    transferMap.put(Pair.of(nextPanel, test.getRight()), Pair.of(panel, (direction + 2) % 4));
    return true;
  }

  private Point getPanel(final Point point) {
    return new Point((point.x - 1) / length, (point.y - 1) / length);
  }

  private Pair<Point, Integer> testCorner(final Point position, final int direction,
      final boolean leftFirst) {
    var nextStep = move3d(position, turn(direction, leftFirst ? LEFT : RIGHT), 1, true);
    if (nextStep == null) {
      return null;
    }
    nextStep = move3d(nextStep.getLeft(), turn(nextStep.getRight(), leftFirst ? RIGHT : LEFT), 1,
        true);
    if (nextStep == null) {
      return null;
    }
    return Pair.of(nextStep.getLeft(), turn(nextStep.getRight(), leftFirst ? RIGHT : LEFT));
  }


  private Pair<Point, Integer> move3d(final Point position, final int direction, final int distance,
      final boolean ignoreWalls) {
    var nextPoint = new Point(position);
    var currentDirection = direction;
    var directionShift = directionMap.get(currentDirection);
    for (var i = 0; i < distance; i++) {
      var testPoint = new Point(nextPoint.x + directionShift.x, nextPoint.y + directionShift.y);
      var result = map.getOrDefault(testPoint, ' ');
      switch (result) {
        case '.':
          nextPoint = testPoint;
          break;
        case '#':
          if (ignoreWalls) {
            nextPoint = testPoint;
            break;
          } else {
            return Pair.of(nextPoint, currentDirection);
          }
        case ' ':
          try {
            var wrapped = attemptToWrap3d(nextPoint, currentDirection, ignoreWalls);
            if (wrapped == null) {
              return null;
            }
            nextPoint = wrapped.getLeft();
            currentDirection = wrapped.getRight();
            directionShift = directionMap.get(currentDirection);
          } catch (final IllegalArgumentException iae) {
            return Pair.of(nextPoint, currentDirection);
          }
          break;
        default:
          throw new UnsupportedOperationException();
      }
    }
    return Pair.of(nextPoint, currentDirection);
  }

  private Pair<Point, Integer> attemptToWrap3d(final Point currentPosition,
      final int currentDirection, final boolean ignoreWalls) {
    final var panel = getPanel(currentPosition);
    final var nextPanel = transferMap.get(Pair.of(panel, currentDirection));
    if (nextPanel == null) {
      return null;
    }
    int baseX;
    int baseY;
    int[] offsetMod;
    int offset = switch (currentDirection) {
      case 0 -> ((currentPosition.y - 1) % length) + 1;
      case 1 -> length - ((currentPosition.x - 1) % length);
      case 2 -> length - ((currentPosition.y - 1) % length);
      case 3 -> ((currentPosition.x - 1) % length) + 1;
      default -> throw new UnsupportedOperationException();
    };
    switch (nextPanel.getRight()) {
      case 0 -> {
        baseX = (nextPanel.getLeft().x * length) + 1;
        baseY = (nextPanel.getLeft().y * length);
        offsetMod = new int[]{0, 1};
      }
      case 1 -> {
        baseX = (nextPanel.getLeft().x * length) + length + 1;
        baseY = (nextPanel.getLeft().y * length) + 1;
        offsetMod = new int[]{-1, 0};
      }
      case 2 -> {
        baseX = (nextPanel.getLeft().x * length) + length;
        baseY = (nextPanel.getLeft().y * length) + length + 1;
        offsetMod = new int[]{0, -1};
      }
      case 3 -> {
        baseX = (nextPanel.getLeft().x * length);
        baseY = (nextPanel.getLeft().y * length) + length;
        offsetMod = new int[]{1, 0};
      }
      default -> throw new UnsupportedOperationException();
    }
    var nextPoint = new Point(baseX + (offset * offsetMod[0]), baseY + (offset * offsetMod[1]));

    var character = map.get(nextPoint);
    if (character == null) {
      return null;
    }
    if (character == '.') {
      return Pair.of(nextPoint, nextPanel.getRight());
    } else if (character == '#') {
      if (ignoreWalls) {
        return Pair.of(nextPoint, nextPanel.getRight());
      }
      throw new IllegalArgumentException();
    }
    return null;
  }

  private Point move(final Point position, final int direction, final int distance) {
    var nextPoint = new Point(position);
    var directionShift = directionMap.get(direction);
    for (var i = 0; i < distance; i++) {
      var testPoint = new Point(nextPoint.x + directionShift.x, nextPoint.y + directionShift.y);
      var result = map.getOrDefault(testPoint, ' ');
      switch (result) {
        case '.':
          nextPoint = testPoint;
          break;
        case '#':
          return nextPoint;
        case ' ':
          try {
            nextPoint = attemptToWrap(nextPoint, direction);
          } catch (final IllegalArgumentException iae) {
            return nextPoint;
          }
          break;
        default:
          throw new UnsupportedOperationException();
      }
    }
    return nextPoint;
  }

  private Point attemptToWrap(final Point currentPoint, final int direction) {
    var testPoint = switch (direction) {
      case 0 -> new Point(
          map.keySet().stream().filter(p -> p.getY() == currentPoint.y).mapToInt(p -> p.x).min()
              .orElse(0), currentPoint.y);
      case 1 -> new Point(currentPoint.x,
          map.keySet().stream().filter(p -> p.x == currentPoint.x).mapToInt(p -> p.y).min()
              .orElse(0));
      case 2 -> new Point(
          map.keySet().stream().filter(p -> p.getY() == currentPoint.y).mapToInt(p -> p.x).max()
              .orElse(0), currentPoint.y);
      case 3 -> new Point(currentPoint.x,
          map.keySet().stream().filter(p -> p.x == currentPoint.x).mapToInt(p -> p.y).max()
              .orElse(0));
      default -> throw new UnsupportedOperationException();
    };
    var character = map.get(testPoint);
    if (character == '.') {
      return testPoint;
    }

    throw new IllegalArgumentException();
  }

  private int turn(final int direction, final char next) {
    if (RIGHT == next) {
      return (direction + 1) % 4;
    }
    return (direction + 3) % 4;
  }

  private Point getStart() {
    int minX = map.keySet().stream().filter(p -> p.getY() == 1).mapToInt(p -> p.x).min().orElse(0);
    var testPoint = new Point(minX, 1);
    while (map.get(testPoint) != '.') {
      testPoint = new Point(testPoint.x + 1, testPoint.y);
    }
    return testPoint;
  }
}
