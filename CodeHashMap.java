// This class is used to generate Huffman codes for characters, where the 
// characters themselves serve as keys and the Huffman codes (strings) as values.
public class CodeHashMap<K, V> {

  // Array of linked lists to store key-value pairs
  private Entry<K, V>[] table; 
  private int capacity; 

  // Static nested class representing each key-value pair
  static class Entry<K, V> { 

    final K key; 
    V value; 
    Entry<K, V> next; 

    Entry(K key, V value) { 
      this.key = key;
      this.value = value;
    }
  }

  // Constructor to initialize the hash table with a given capacity
  @SuppressWarnings("unchecked")
  public CodeHashMap(int capacity) { 
    this.capacity = capacity;
    table = new Entry[capacity]; 
  }

  private int hash(K key) { 
    return Math.abs((key.hashCode() % capacity)); 
  }

  // Method to insert or update a key-value pair in the hash map
  public void put(K key, V value) { 
    int index = hash(key); 
    // Create a new entry with the given key-value pair
    Entry<K, V> newEntry = new Entry<>(key, value); 
    // if spot is open, fill it
    if (table[index] == null) { 
      table[index] = newEntry;
    } else { 
      // Iterate through the linked list at the index
      Entry<K, V> previous = null; 
      Entry<K, V> current = table[index]; 
      while (current != null) {
        // if key is the same, update it 
        if (current.key.equals(key)) {
          current.value = value; 
          return; 
        }
        previous = current;
        current = current.next;
      }
      // Add the new entry at the end of the linked list
      previous.next = newEntry; 
    }
  }

  // Method to get the value associated with a key or return a default value if key not found
  public V getOrDefault(K key, V defaultValue) { 
    int index = hash(key); 
    Entry<K, V> entry = table[index]; 
    while (entry != null) { 
      // if key is found, return its value
      if (entry.key.equals(key)) { 
        return entry.value; 
      }
      // else, move through linked list
      entry = entry.next; 
    }
    return defaultValue; 
  }
}
