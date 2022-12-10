package king.greg.aoc2022;

import java.util.List;

public class Day10 {

  private final List<String> console;
  private final char[][] pixels;
  int register = 1;
  int nextX = 1;
  int cyclesToGo = 0;
  int cycle = 0;
  boolean eof = false;
  int consoleLine = -1;

  public Day10(final List<String> lines) {
    console = lines;
    pixels = new char[6][40];
  }

  public long strengthTest(final int offset, final int frequency) {
    var sum = 0L;
    while (!eof) {
      if (cycle == offset || (cycle - offset) % frequency == 0) {
        sum += (long) register * cycle;
      }
      cycle();
    }
    return sum;
  }

  private void cycle() {
    cycle++;
    cyclesToGo--;
    if (cyclesToGo < 1) {
      register = nextX;
      consoleLine++;
      if (consoleLine >= console.size()) {
        eof = true;
      } else {
        final String[] input = console.get(consoleLine).split("\s+");
        if ("addx".equals(input[0])) {
          cyclesToGo = 2;
          nextX = register + Integer.parseInt(input[1]);
        } else {
          cyclesToGo = 1;
        }
      }
    }
  }

  public String draw() {
    while (!eof) {
      cycle();
      update(cycle, register);
    }
    return print();
  }

  private void update(final int cycle, final int spriteLocation) {
    int row = (cycle - 1) / 40;
    if (row > 5) {
      return;
    }
    int position = (cycle - 1) % 40;
    pixels[row][position] =
        (position >= spriteLocation - 1 && position <= spriteLocation + 1) ? '#' : '.';
  }

  private String print() {
    final var display = new StringBuilder();
    for (var y = 0; y < 6; y++) {
      for (var x = 0; x < 40; x++) {
        display.append(pixels[y][x]);
      }
      display.append("\n");
    }
    display.deleteCharAt(display.length() - 1);
    return display.toString();
  }
}
