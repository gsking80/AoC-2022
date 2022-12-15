package king.greg.aoc2022;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Pattern;
import org.apache.commons.lang3.tuple.Pair;

public class Day15 {

  final Set<Sensor> sensors;

  public Day15(final List<String> inputs) {
    sensors = new HashSet<>();
    var pattern = Pattern.compile("(-?\\d+)");
    for (final var input : inputs) {
      final var matcher = pattern.matcher(input);
      final List<Long> coordinates = new ArrayList<>();
      while (matcher.find()) {
        coordinates.add(Long.parseLong(matcher.group()));
      }
      sensors.add(new Sensor(coordinates));
    }
  }

  public long beaconlessSpaces(final long row) {
    final List<Pair<Long, Long>> ranges = new ArrayList<>();
    for (final var sensor : sensors) {
      var range = sensor.emptyRange(row);
      if (null != range) {
        ranges.add(range);
      }
    }

    final List<Pair<Long, Long>> reducedRanges = reduce(ranges);
    long emptySpaces = 0;
    for (final var range : reducedRanges) {
      emptySpaces += (range.getRight() - range.getLeft()) + 1;
    }
    return emptySpaces;
  }

  private List<Pair<Long, Long>> reduce(final List<Pair<Long, Long>> ranges) {
    ranges.sort(Comparator.comparing(Pair::getLeft));
    var reducedRanges = new ArrayList<Pair<Long, Long>>();
    var currentRange = ranges.get(0);
    for (var i = 1; i < ranges.size(); i++) {
      var compareRange = ranges.get(i);
      if (currentRange.getRight() < compareRange.getLeft()) {
        reducedRanges.add(currentRange);
        currentRange = compareRange;
      } else {
        currentRange = Pair.of(currentRange.getLeft(),
            Math.max(currentRange.getRight(), compareRange.getRight()));
      }
    }
    reducedRanges.add(currentRange);
    return reducedRanges;
  }


  public long beaconFrequency(final long upperBound) {
    final var initialArea = new SearchArea(0, 0, upperBound, upperBound);
    Queue<SearchArea> priorityQueue = initQueue();
    priorityQueue.add(initialArea);

    SearchArea current;
    while (!priorityQueue.isEmpty()) {
      current = priorityQueue.remove();
      if (current.sensorsInRange == 0) {
        return (current.minX * 4000000) + current.minY;
      }
      if (current.distance == 0 || current.selfContained()) {
        continue;
      }
      long midX = (current.minX + current.maxX) / 2;
      long midY = (current.minY + current.maxY) / 2;
      priorityQueue.add(new SearchArea(current.minX, current.minY, midX, midY));
      priorityQueue.add(
          new SearchArea(current.minX, Math.min(midY + 1, current.maxY), midX, current.maxY));
      priorityQueue.add(
          new SearchArea(Math.min(midX + 1, current.maxX), current.minY, current.maxX, midY));
      priorityQueue.add(
          new SearchArea(Math.min(midX + 1, current.maxX), Math.min(midY + 1, current.maxY),
              current.maxX, current.maxY));
    }

    return 0;
  }

  private PriorityQueue<SearchArea> initQueue() {
    return new PriorityQueue<>(10, (o1, o2) -> Comparator.comparing(SearchArea::getSensorsInRange)
        .thenComparing(SearchArea::getDistance).compare(o1, o2));
  }

  static class Sensor {

    private final long x;
    private final long y;
    private final long nearestBeaconX;
    private final long range;

    public Sensor(final List<Long> coordinates) {
      x = coordinates.get(0);
      y = coordinates.get(1);
      nearestBeaconX = coordinates.get(2);
      var nearestBeaconY = coordinates.get(3);
      range = Math.abs(x - nearestBeaconX) + Math.abs(y - nearestBeaconY);
    }


    public Pair<Long, Long> emptyRange(final long row) {
      if (y - range <= row && y + range >= row) {
        var offset = range - Math.abs(row - y);
        if (offset == 0) {
          return null;
        }
        var left = x - offset;
        if (left == nearestBeaconX) {
          left += 1;
        }
        var right = x + offset;
        if (right == nearestBeaconX) {
          right -= 1;
        }
        return Pair.of(left, right);
      }
      return null;
    }
  }

  class SearchArea {

    private final long minX;
    private final long minY;
    private final long maxX;
    private final long maxY;
    private final long distance;
    private final int sensorsInRange;

    SearchArea(final long minX, final long minY, final long maxX, final long maxY) {
      this.minX = minX;
      this.minY = minY;
      this.maxX = maxX;
      this.maxY = maxY;
      this.distance = (maxX - minX) + (maxY - minY);
      this.sensorsInRange = sensorsInRange(minX, minY, maxX, maxY);
    }

    public int getSensorsInRange() {
      return sensorsInRange;
    }

    public long getDistance() {
      return distance;
    }

    private int sensorsInRange(final long minX, final long minY, final long maxX, final long maxY) {
      var inRange = 0;
      for (final var sensor : sensors) {
        var distanceToArea = 0;
        if (sensor.x < minX) {
          distanceToArea += minX - sensor.x;
        }
        if (sensor.x > maxX) {
          distanceToArea += sensor.x - maxX;
        }
        if (sensor.y < minY) {
          distanceToArea += minY - sensor.y;
        }
        if (sensor.y > maxY) {
          distanceToArea += sensor.y - maxY;
        }
        if (distanceToArea <= sensor.range) {
          inRange++;
        }
      }
      return inRange;
    }

    public boolean selfContained() {
      for (final var sensor : sensors) {
        if ((Math.abs(minX - sensor.x) + Math.abs(minY - sensor.y) <= sensor.range) &&
            (Math.abs(minX - sensor.x) + Math.abs(maxY - sensor.y) <= sensor.range) &&
            (Math.abs(maxX - sensor.x) + Math.abs(minY - sensor.y) <= sensor.range) &&
            (Math.abs(maxX - sensor.x) + Math.abs(maxY - sensor.y) <= sensor.range)) {
          return true;
        }
      }
      return false;
    }
  }
}
