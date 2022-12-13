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
      packetPairs.add(Pair.of(new Packet(left.substring(1, left.lastIndexOf(']'))),
          new Packet(right.substring(1, right.lastIndexOf(']')))));
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

    public Packet(final String input) {
      packets = new ArrayList<>();
      if (input.indexOf('[') < 0) {
        final String[] parts = input.split(",");
        if (parts.length == 1) {
          value = parts[0].isBlank() ? null : Integer.parseInt(parts[0]);
        } else {
          for (String part : parts) {
            packets.add(new Packet(part));
          }
        }
      } else {
        var left = 0;
        var i = 0;
        while (i < input.length()) {
          if (input.charAt(i) == '[') {
            var depth = 1;
            var offset = -1;
            for (var j = i + 1; j < input.length(); j++) {
              if (input.charAt(j) == ']') {
                depth--;
              } else if (input.charAt(j) == '[') {
                depth++;
              }
              if (depth == 0) {
                offset = j;
                break;
              }
            }
            packets.add(new Packet(input.substring(i + 1, offset)));
            i = offset + 2;
            left = i;
          } else if (input.charAt(i) == ',') {
            packets.add(new Packet(input.substring(left, i)));
            i++;
            left = i;
          } else {
            i++;
          }
        }
        if (left < input.length()) {
          packets.add(new Packet(input.substring(left)));
        }
      }
    }

    public int compareTo(final Packet comparePacket) {
      if (value != null) {
        if (comparePacket.value != null) {
          return value.compareTo(comparePacket.value);
        }
        if (comparePacket.packets.isEmpty()) {
          return 1;
        }
        var compare = this.compareTo(comparePacket.packets.get(0));
        if (compare != 0) {
          return compare;
        } else if (comparePacket.packets.size() > 1) {
          return -1;
        }
        return 0;
      }
      if (packets.isEmpty()) {
        if (comparePacket.packets.isEmpty() && comparePacket.value == null) {
          return 0;
        }
        return -1;
      }
      if (comparePacket.packets.isEmpty() && comparePacket.value == null) {
        return 1;
      }
      if (comparePacket.value != null) {
        var compare = packets.get(0).compareTo(comparePacket);
        if (compare != 0) {
          return compare;
        }
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

    @Override
    public String toString() {
      final var builder = new StringBuilder();
      if (value != null) {
        builder.append(value);
      }
      if (!packets.isEmpty()) {
        builder.append(packets);
      }
      return builder.toString();
    }
  }
}
