class SimpleMap {

  private Entry[] entries;

  public SimpleMap(int capacity) {
    entries = new Entry[capacity];
  }

  static class Entry {

    char key;
    int value;
    Entry next;

    Entry(char key, int value) {
      this.key = key;
      this.value = value;
      this.next = null;
    }
  }

  private int getIndex(char key) {
    return key % entries.length;
  }

  public void put(char key, int value) {
    int index = getIndex(key);
    Entry current = entries[index];
    while (current != null) {
      if (current.key == key) {
        current.value = value;
        return;
      }
      current = current.next;
    }
    Entry newEntry = new Entry(key, value);
    newEntry.next = entries[index];
    entries[index] = newEntry;
  }

  public int getOrDefault(char key, int defaultValue) {
    int index = getIndex(key);
    Entry current = entries[index];
    while (current != null) {
      if (current.key == key) {
        return current.value;
      }
      current = current.next;
    }
    return defaultValue;
  }

  public Entry[] entrySet() {
    return entries;
  }
}
