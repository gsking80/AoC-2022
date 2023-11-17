package king.greg.aoc2022;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Day17 {

  static final List<Rock> rockShapes;

  static {
    rockShapes = List.of(
        new Rock(Set.of(new Point(2, 0), new Point(3, 0), new Point(4, 0), new Point(5, 0))),
        new Rock(Set.of(new Point(3, 0), new Point(2, 1), new Point(3, 1), new Point(4, 1),
            new Point(3, 2))),
        new Rock(Set.of(new Point(2, 0), new Point(3, 0), new Point(4, 0), new Point(4, 1),
            new Point(4, 2))),
        new Rock(Set.of(new Point(2, 0), new Point(2, 1), new Point(2, 2), new Point(2, 3))),
        new Rock(Set.of(new Point(2, 0), new Point(3, 0), new Point(2, 1), new Point(3, 1))));
  }

  final String jetPattern;

  public Day17(final List<String> input) {
    jetPattern = input.get(0);
  }

  public int towerHeight(final int rockCount) {

    var towerHeight = 0;
    var jetIndex = 0;
    final Set<Point> rocks = new HashSet<>();
    for (var i = 0; i < rockCount; i++) {
      var rock = new Rock(rockShapes.get(i % rockShapes.size()), new Point(0, towerHeight + 4));
      while (rock.moving) {
        rock.shift(jetPattern.charAt(jetIndex), rocks);
        rock.drop(rocks);
        jetIndex = (jetIndex + 1) % jetPattern.length();
      }
      towerHeight = rocks.stream().mapToInt(p -> p.y).max().orElse(0);
    }
    return towerHeight;
  }

  public long cyclicTowerHeight(final long rockCount) {
    final var states = new HashMap<Key, Value>();
    final var heights = new ArrayList<Integer>();
    heights.add(0);
    var towerHeight = 0;
    var jetIndex = 0;
    final Set<Point> rocks = new HashSet<>();
    for (var i = 0; i < rockCount; i++) {
      var rockIndex = i % rockShapes.size();
      var rock = new Rock(rockShapes.get(rockIndex), new Point(0, towerHeight + 4));
      while (rock.moving) {
        rock.shift(jetPattern.charAt(jetIndex), rocks);
        rock.drop(rocks);
        jetIndex = (jetIndex + 1) % jetPattern.length();
      }
      towerHeight = rocks.stream().mapToInt(p -> p.y).max().orElse(0);
      heights.add(towerHeight);
      var topShape = new int[7];
      var normalizer = Integer.MAX_VALUE;
      for (var x = 0; x < 7; x++) {
        int finalX = x;
        topShape[x] = rocks.stream().filter(p -> p.x == finalX).mapToInt(p -> p.y).max().orElse(0);
        if (topShape[x] < normalizer) {
          normalizer = topShape[x];
        }
      }
      for (var x = 0; x < 7; x++) {
        topShape[x] -= normalizer;
      }
      final var key = new Key(jetIndex, rockIndex, topShape);
      var periodStart = states.get(key);
      if (null == periodStart) {
        states.put(key, new Value(i + 1, towerHeight));
      } else {
        var currentLanded = i + 1;
        var periodLength = currentLanded - periodStart.numberLanded();
        var periodHeight = towerHeight - periodStart.height();
        var offset = currentLanded - periodLength;

        var cycles = (rockCount - offset) / periodLength;
        var leftToGo = (int) (rockCount - ((cycles * periodLength) + offset));

        var heightRemainder = heights.get(periodStart.numberLanded() + leftToGo) - heights.get(
            periodStart.numberLanded());
        return Integer.toUnsignedLong(periodStart.height()) + (cycles * periodHeight)
            + heightRemainder;
      }
    }
    return towerHeight;
  }

  static class Rock {

    final Set<Point> pixels;
    boolean moving;

    public Rock(final Set<Point> pieces) {
      pixels = new HashSet<>();
      for (final var piece : pieces) {
        pixels.add((Point) piece.clone());
      }
      moving = true;
    }

    public Rock(final Rock otherRock, final Point offset) {
      pixels = new HashSet<>();
      for (final var pixel : otherRock.pixels) {
        pixels.add(new Point(pixel.x + offset.x, pixel.y + offset.y));
      }
      moving = true;
    }

    public void shift(final char puff, final Set<Point> rocks) {
      var direction = puff == '<' ? -1 : 1;
      if (((pixels.stream().mapToInt(p -> p.x).min().orElse(0)) + direction < 0) ||
          (pixels.stream().mapToInt(p -> p.x).max().orElse(6) + direction > 6)) {
        return;
      }
      final var testRock = new Rock(this, new Point(direction, 0));
      if (Collections.disjoint(testRock.pixels, rocks)) {
        for (final var pixel : pixels) {
          pixel.x += direction;
        }
      }
    }

    public void drop(final Set<Point> rocks) {
      if (pixels.stream().mapToInt(p -> p.y).min().orElse(0) - 1 < 1) {
        parkIt(rocks);
      } else {
        final var testRock = new Rock(this, new Point(0, -1));
        if (Collections.disjoint(testRock.pixels, rocks)) {
          for (final var pixel : pixels) {
            pixel.y -= 1;
          }
        } else {
          parkIt(rocks);
        }
      }
    }

    private void parkIt(final Set<Point> rocks) {
      moving = false;
      rocks.addAll(pixels);
    }
  }

  record Key(int jetIndex, int rockIndex, int[] topShape) {

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Key key = (Key) o;
      return jetIndex == key.jetIndex && rockIndex == key.rockIndex && Arrays.equals(
          topShape, key.topShape);
    }

    @Override
    public int hashCode() {
      int result = Objects.hash(jetIndex, rockIndex);
      result = 31 * result + Arrays.hashCode(topShape);
      return result;
    }

    @Override
    public String toString() {
      return "Key{" +
          "jetIndex=" + jetIndex +
          ", rockIndex=" + rockIndex +
          ", topShape=" + Arrays.toString(topShape) +
          '}';
    }
  }

  record Value(int numberLanded, int height) {

  }
}
