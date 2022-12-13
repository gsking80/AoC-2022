package king.greg.aoc2022;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

public class Day13 {

  final List<Pair<Packet, Packet>> packetPairs;

  public Day13(final List<String> input) {
    packetPairs = new ArrayList<>();
    for (var i = 0; i < input.size(); i += 3) {
      final String left = input.get(i);
      final String right = input.get(i + 1);
      packetPairs.add(Pair.of(new Packet(left.substring(1, left.length() - 1)),
          new Packet(right.substring(1, right.length() - 1))));
    }
  }

  public int correctOrderScore() {
    var correctOrderScore = 0;
    for (var i = 0; i < packetPairs.size(); i++) {
      var pair = packetPairs.get(i);
      if (pair.getLeft().compareTo(pair.getRight()) == -1) {
        correctOrderScore += i + 1;
      }
    }
    return correctOrderScore;
  }

  public int decoderKey() {
    final List<Packet> packets = new ArrayList<>();
    for (final var pair : packetPairs) {
      packets.add(pair.getLeft());
      packets.add(pair.getRight());
    }
    var divider1 = new Packet("[2]");
    var divider2 = new Packet("[6]");
    packets.add(divider1);
    packets.add(divider2);
    packets.sort(Packet::compareTo);
    var key1 = packets.indexOf(divider1) + 1;
    var key2 = packets.indexOf(divider2) + 1;
    return key1 * key2;
  }

  static class Packet {

    private final List<Packet> packets;
    private Integer value;

    public Packet() {
      packets = new ArrayList<>();
    }

    public Packet(final Integer input) {
      packets = new ArrayList<>();
      value = input;
    }

    public Packet(final String input) {
      packets = new ArrayList<>();
      var left = 0;
      var depth = 0;
      var right = 0;
      while (right < input.length()) {
        switch (input.charAt(right)) {
          case '[':
            depth++;
            break;
          case ']':
            depth--;
            if (depth == 0) {
              if (right == left + 1) {
                packets.add(new Packet());
              } else {
                packets.add(new Packet(input.substring(left + 1, right)));
              }
              left = right + 2;
              right = left - 1;
            }
            break;
          case ',':
            if (depth == 0) {
              packets.add(new Packet(Integer.valueOf(input.substring(left, right))));
              left = right + 1;
            }
            break;
          default:
        }
        right++;
      }
      if (left < input.length()) {
        packets.add(new Packet(Integer.valueOf(input.substring(left))));
      }
    }

    public int compareTo(final Packet comparePacket) {
      if (value != null) {
        return compareValueToPacket(comparePacket);
      } else if (comparePacket.value != null) {
        return -1 * comparePacket.compareValueToPacket(this);
      }
      if (packets.isEmpty()) {
        if (comparePacket.packets.isEmpty()) {
          return 0;
        }
        return -1;
      }
      if (comparePacket.packets.isEmpty()) {
        return 1;
      }
      for (var i = 0; i < packets.size(); i++) {
        if (i < comparePacket.packets.size()) {
          var compare = packets.get(i).compareTo(comparePacket.packets.get(i));
          if (compare != 0) {
            return compare;
          }
        } else {
          return 1;
        }
      }
      if (comparePacket.packets.size() > packets.size()) {
        return -1;
      }
      return 0;
    }

    private int compareValueToPacket(final Packet comparePacket) {
      if (comparePacket.value != null) {
        return value.compareTo(comparePacket.value);
      }
      if (comparePacket.packets.isEmpty()) {
        return 1;
      }
      var compare = this.compareValueToPacket(comparePacket.packets.get(0));
      if (compare != 0) {
        return compare;
      } else if (comparePacket.packets.size() > 1) {
        return -1;
      }
      return 0;
    }

    @Override
    public String toString() {
      final var builder = new StringBuilder();
      if (value != null) {
        builder.append(value);
      } else {
        builder.append(packets);
      }
      return builder.toString();
    }
  }
}
