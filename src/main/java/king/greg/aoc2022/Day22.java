package king.greg.aoc2022;

import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.tuple.Pair;

public class Day22 {

  static final Map<Integer, Point> directionMap;

  static {
    directionMap = Map.of(0, new Point(1, 0),
        1, new Point(0, 1),
        2, new Point(-1, 0),
        3, new Point(0, -1));
  }

  final Map<Point, Character> map;
  final String path;

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
        case 'R', 'L' -> {
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

  public int getPassword2(final int edgeLength) {
    var position = getStart();
    var direction = 0;
    var distance = 0;
    for (var i = 0; i < path.length(); i++) {
      final var next = path.charAt(i);
      switch (next) {
        case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' ->
            distance = (distance * 10) + (next - '0');
        case 'R', 'L' -> {
          var result = move3d(position, direction, distance, edgeLength);
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

  private Pair<Point, Integer> move3d(final Point position, final int direction, final int distance,
      final int edgeLength) {
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
          return Pair.of(nextPoint, currentDirection);
        case ' ':
          try {
            var wrapped = attemptToWrap3d(nextPoint, currentDirection, edgeLength);
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

  private Pair<Point, Integer> attemptToWrap3d(final Point currentPoint, final int direction,
      final int edgeLength) {

    // There may be a good dynamic way to do this.  I gave up trying.  Hard-code ALL the edges!!

    Pair<Point, Integer> attempt;
    int x;
    int y;
    int offset;
    switch (direction) {
      case 0 -> {
        x = currentPoint.x / edgeLength;
        y = (currentPoint.y - 1) / edgeLength;
        offset = ((currentPoint.y - 1) % edgeLength) + 1;
        if (x == 1 && y == 3) {
          attempt = Pair.of(new Point((edgeLength) + offset, (edgeLength * 3)), 3);
        } else if (x == 2 && y == 2) {
          attempt = Pair.of(new Point((edgeLength * 3), (edgeLength - offset) + 1), 2);
        } else if (x == 2 && y == 1) {
          attempt = Pair.of(new Point((edgeLength * 2) + offset, edgeLength), 3);
        } else if (x == 3 && y == 0) {
          attempt = Pair.of(new Point((edgeLength * 2), ((edgeLength * 3) - offset) + 1),
              2);
        } else {
          throw new UnsupportedOperationException();
        }
      }
      case 1 -> {
        x = (currentPoint.x - 1) / edgeLength;
        y = currentPoint.y / edgeLength;
        offset = ((currentPoint.x - 1) % edgeLength) + 1;
        if (x == 0 && y == 4) {
          attempt = Pair.of(new Point((2 * edgeLength) + offset, 1), 1);
        } else if (x == 1 && y == 3) {
          attempt = Pair.of(new Point(edgeLength, (3 * edgeLength) + offset), 2);
        } else if (x == 2 && y == 1) {
          attempt = Pair.of(new Point(2 * edgeLength, edgeLength + offset), 2);
        } else {
          throw new UnsupportedOperationException();
        }
      }
      case 2 -> {
        x = (currentPoint.x - 1) / edgeLength;
        y = (currentPoint.y - 1) / edgeLength;
        offset = ((currentPoint.y - 1) % edgeLength) + 1;
        if (x == 0 && y == 3) {
          attempt = Pair.of(new Point((edgeLength + offset), 1), 1);
        } else if (x == 0 && y == 2) {
          attempt = Pair.of(new Point((edgeLength + 1), (edgeLength - offset) + 1), 0);
        } else if (x == 1 && y == 1) {
          attempt = Pair.of(new Point(offset, (2 * edgeLength) + 1), 1);
        } else if (x == 1 && y == 0) {
          attempt = Pair.of(new Point(1, ((3 * edgeLength) - offset) + 1), 0);
        } else {
          throw new UnsupportedOperationException();
        }
      }
      case 3 -> {
        x = (currentPoint.x - 1) / edgeLength;
        y = (currentPoint.y - 1) / edgeLength;
        offset = ((currentPoint.x - 1) % edgeLength) + 1;
        if (x == 0 && y == 2) {
          attempt = Pair.of(new Point(edgeLength + 1, edgeLength + offset), 0);
        } else if (x == 1 && y == 0) {
          attempt = Pair.of(new Point(1, (3 * edgeLength) + offset), 0);
        } else if (x == 2 && y == 0) {
          attempt = Pair.of(new Point(offset, 4 * edgeLength), 3);
        } else {
          throw new UnsupportedOperationException();
        }
      }
      default -> throw new UnsupportedOperationException();
    }
    var character = map.get(attempt.getLeft());
    if (character == '.') {
      return attempt;
    } else if (character == '#') {
      throw new IllegalArgumentException();
    }
    throw new UnsupportedOperationException();
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
    if ('R' == next) {
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
