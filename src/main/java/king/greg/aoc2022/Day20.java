package king.greg.aoc2022;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Day20 {

  final List<Tuple> sequence;

  public Day20(final List<String> input) {
    sequence = new ArrayList<>();
    for (var i = 0; i < input.size(); i++) {
      var entry = Integer.parseInt(input.get(i));
      sequence.add(new Tuple(entry, i));
    }
  }

  public long groveCoordinateSum() {
    mix();
    return calculateGroveCoordinateSum();
  }

  public long correctedGroveCoordinateSum() {
    for (var entry : sequence) {
      entry.value *= 811589153;
    }
    for (var i = 0; i < 10; i++) {
      mix();
    }
    return calculateGroveCoordinateSum();
  }

  private void mix() {
    final var length = sequence.size();
    for (final var entry : sequence) {
      var currentPosition = entry.position;
      var newPosition = entry.value + entry.position;
      newPosition = newPosition % (length - 1);
      if (newPosition < 0) {
        newPosition += length - 1;
      }
      for (var moveEntry : sequence) {
        if (moveEntry == entry) {
          moveEntry.position = newPosition;
        } else if (moveEntry.position >= currentPosition && moveEntry.position <= newPosition) {
          moveEntry.position--;
        } else if (moveEntry.position >= newPosition && moveEntry.position <= currentPosition) {
          moveEntry.position++;
        }
      }
    }
  }

  private long calculateGroveCoordinateSum() {
    var length = sequence.size();
    sequence.sort(Comparator.comparing(Tuple::getPosition));
    var zeroIndex = -1;
    for (var i = 0; i < length; i++) {
      if (sequence.get(i).value == 0) {
        zeroIndex = i;
      }
    }
    return sequence.get((zeroIndex + 1000) % length).value
        + sequence.get((zeroIndex + 2000) % length).value
        + sequence.get((zeroIndex + 3000) % length).value;
  }

  static class Tuple {

    long value;
    long position;

    public Tuple(long value, long position) {
      this.value = value;
      this.position = position;
    }

    public long getPosition() {
      return position;
    }

    @Override
    public String toString() {
      return value + " (" + position + ")";
    }
  }
}
