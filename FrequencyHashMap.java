// This class is used to store character frequencies, which are integers.
public class FrequencyHashMap {

  // Array to store key-value pairs
  private Entry[] entries; 

  public FrequencyHashMap(int capacity) { 
    entries = new Entry[capacity]; 
  }

  // Static nested class representing each key-value pair
  static class Entry { 

    char key; 
    int value; 
    Entry next; 

    Entry(char key, int value) { 
      this.key = key;
      this.value = value;
      // Initialize next reference to null
      this.next = null; 
    }
  }

  private int getIndex(char key) { 
    // Simple modulo operation to determine index
    return key % entries.length; 
  }

  // Method to insert or update a key-value pair in the map
  public void put(char key, int value) { 
    int index = getIndex(key); 
    Entry current = entries[index]; 
    while (current != null) { 
      // update val if it exists
      if (current.key == key) { 
        current.value = value; 
        return; 
      }
      current = current.next; 
    }
    // Create a new entry with the given key-value pair
    Entry newEntry = new Entry(key, value); 
    // Set the next reference of the new entry to the current head of the linked list
    newEntry.next = entries[index]; 
    // Update the head of the linked list at the calculated index
    entries[index] = newEntry; 
  }

  // Method to get the value associated with a key or return a default value if key not found
  public int getOrDefault(char key, int defaultValue) {
    int index = getIndex(key); 
    Entry current = entries[index]; 
    while (current != null) { 
      // If the key is found, return its value
      if (current.key == key) {
        return current.value;
      }
      // else move through linked list
      current = current.next; 
    }
    return defaultValue; 
  }

  // Method to return all entries in the map
  public Entry[] entrySet() { 
    return entries;
  }
}
