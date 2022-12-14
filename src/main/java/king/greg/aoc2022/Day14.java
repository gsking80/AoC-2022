package king.greg.aoc2022;

import java.awt.Point;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day14 {

  final Map<Point, Character> map;
  final int floorLevel;

  public Day14(final List<String> input) {
    map = new HashMap<>();
    map.put(new Point(500, 0), '+');
    for (final var path : input) {
      final String[] nodes = path.split(" -> ");
      for (var i = 0; i < nodes.length - 1; i++) {
        final String[] start = nodes[i].split(",");
        final String[] end = nodes[i + 1].split(",");
        fillRock(Integer.parseInt(start[0]), Integer.parseInt(start[1]), Integer.parseInt(end[0]),
            Integer.parseInt(end[1]));
      }
    }
    floorLevel = map.keySet().stream().max(Comparator.comparing(a -> a.y)).orElse(new Point()).y;
  }

  private void fillRock(final int startX, final int startY, final int endX, final int endY) {
    var startLeft = startX <= endX;
    var startTop = startY <= endY;
    for (var x = (startLeft ? startX : endX); x <= (startLeft ? endX : startX); x++) {
      for (var y = (startTop ? startY : endY); y <= (startTop ? endY : startY); y++) {
        map.put(new Point(x, y), '#');
      }
    }
  }

  public long restingSand() {
    var stopable = true;
    while (stopable) {
      final var grain = new Point(500, 0);
      var grainMoving = true;
      while (grainMoving) {
        if (grain.y >= floorLevel) {
          stopable = false;
          grainMoving = false;
        } else if (map.get(new Point(grain.x, grain.y + 1)) == null) {
          grain.y++;
        } else if (map.get(new Point(grain.x - 1, grain.y + 1)) == null) {
          grain.x--;
          grain.y++;
        } else if (map.get(new Point(grain.x + 1, grain.y + 1)) == null) {
          grain.x++;
          grain.y++;
        } else {
          map.put(grain, 'o');
          grainMoving = false;
        }
      }
    }
    return map.values().stream().filter(val -> val == 'o').count();
  }

  public long restingSandInfiniteFloor() {
    var stopped = false;
    while (!stopped) {
      var x = 500;
      var y = 0;
      var grainMoving = true;
      while (grainMoving) {
        if (y == floorLevel + 1) {
          map.put(new Point(x, y), 'o');
          grainMoving = false;
        } else if (map.get(new Point(x, y + 1)) == null) {
          y++;
        } else if (map.get(new Point(x - 1, y + 1)) == null) {
          x--;
          y++;
        } else if (map.get(new Point(x + 1, y + 1)) == null) {
          x++;
          y++;
        } else {
          map.put(new Point(x, y), 'o');
          if (x == 500 && y == 0) {
            stopped = true;
          }
          grainMoving = false;
        }
      }
    }
    return map.values().stream().filter(val -> val == 'o').count();
  }
}
