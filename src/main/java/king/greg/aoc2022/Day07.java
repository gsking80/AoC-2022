package king.greg.aoc2022;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day07 {

  private static final long MAX_SIZE = 70000000L;
  private final Directory root = new Directory(null);

  Day07(final List<String> lines) {
    var currentDirectory = root;
    for (final String console : lines) {
      String[] pieces = console.split("\s+");
      switch (pieces[0]) {
        case "$": // command to execute
          if ("cd".equals(pieces[1])) {
            currentDirectory = currentDirectory.changeDirectory(pieces[2]);
          }
          break;
        case "dir": //directory to add
          currentDirectory.addSubdirectory(pieces[1]);
          break;
        default: // file to add
          currentDirectory.addFile(pieces[1], Long.parseLong(pieces[0]));
      }
    }
  }

  public long totalUnder(final long targetSize) {
    long total = 0;
    for (final Long size : root.subdirectorySizes()) {
      if (size <= targetSize) {
        total += size;
      }
    }
    return total;
  }

  public long sizeToDelete(final long targetFreeSpace) {
    final long currentSize = root.getSize();
    final long deletionMinimum = targetFreeSpace - (MAX_SIZE - currentSize);
    long minimum = currentSize;
    for (final Long size : root.subdirectorySizes()) {
      if (size < minimum && size >= deletionMinimum) {
        minimum = size;
      }
    }
    return minimum;
  }

  class Directory {

    final Directory parent;
    final Map<String, Directory> childDirectories = new HashMap<>();
    final Map<String, Long> files = new HashMap<>();
    private Long size;

    Directory(final Directory parentDirectory) {
      parent = parentDirectory;
    }

    public Directory changeDirectory(final String target) {
      return switch (target) {
        case "/" -> root;
        case ".." -> parent;
        default -> childDirectories.get(target);
      };
    }

    public long getSize() {
      if (size == null) {
        size = 0L;
        for (final Directory child : childDirectories.values()) {
          size += child.getSize();
        }
        for (final Long fileSize : files.values()) {
          size += fileSize;
        }
      }
      return size;
    }

    public void addSubdirectory(final String name) {
      childDirectories.putIfAbsent(name, new Directory(this));
    }

    public void addFile(final String name, final long size) {
      files.putIfAbsent(name, size);
    }

    public List<Long> subdirectorySizes() {
      final List<Long> sizes = new ArrayList<>();
      for (final Directory child : childDirectories.values()) {
        sizes.addAll(child.subdirectorySizes());
        sizes.add(child.getSize());
      }
      return sizes;
    }
  }
}
