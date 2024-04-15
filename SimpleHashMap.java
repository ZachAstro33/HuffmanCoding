class SimpleHashMap<K, V> {

  private Entry<K, V>[] table;
  private int capacity;

  static class Entry<K, V> {

    final K key;
    V value;
    Entry<K, V> next;

    Entry(K key, V value) {
      this.key = key;
      this.value = value;
    }
  }

  @SuppressWarnings("unchecked")
  public SimpleHashMap(int capacity) {
    this.capacity = capacity;
    table = new Entry[capacity];
  }

  private int hash(K key) {
    return (key.hashCode() % capacity + capacity) % capacity; // Handle negative hashes
  }

  public void put(K key, V value) {
    int index = hash(key);
    Entry<K, V> newEntry = new Entry<>(key, value);
    if (table[index] == null) {
      table[index] = newEntry;
    } else {
      Entry<K, V> previous = null;
      Entry<K, V> current = table[index];
      while (current != null) {
        if (current.key.equals(key)) {
          current.value = value;
          return;
        }
        previous = current;
        current = current.next;
      }
      previous.next = newEntry;
    }
  }

  public V getOrDefault(K key, V defaultValue) {
    int index = hash(key);
    Entry<K, V> entry = table[index];
    while (entry != null) {
      if (entry.key.equals(key)) {
        return entry.value;
      }
      entry = entry.next;
    }
    return defaultValue;
  }
}
