package king.greg.aoc2022;

import java.util.List;

public class Day25 {

  final List<String> input;

  public Day25(final List<String> input) {
    this.input = input;
  }

  public String calculateSNAFUsum() {
    long sum = 0;
    for (final var line : input) {
      long decimalValue = 0;
      for (var i = 0; i < line.length(); i++) {
        decimalValue *= 5;
        switch (line.charAt(i)) {
          case '1' -> decimalValue += 1;
          case '2' -> decimalValue += 2;
          case '-' -> decimalValue -= 1;
          case '=' -> decimalValue -= 2;
          default -> {
            // Do nothing
          }
        }
      }
      sum += decimalValue;
    }

    final var sumSNAFU = new StringBuilder();
    long carry = 0;
    while (sum > 0) {
      var nextValue = (sum % 5) + carry;
      carry = 0;
      if (nextValue < 3) {
        sumSNAFU.append(nextValue);
      } else if (nextValue == 3) {
        carry = 1;
        sumSNAFU.append('=');
      } else if (nextValue == 4) {
        carry = 1;
        sumSNAFU.append('-');
      } else {
        carry = 1;
        sumSNAFU.append(0);
      }
      sum /= 5;
    }

    return sumSNAFU.reverse().toString();
  }
}
