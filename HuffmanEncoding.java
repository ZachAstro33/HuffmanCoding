import java.util.Scanner;

// class representing every node in the huffman tree
class HuffmanNode {

  // Character stored in the node
  char data;
  int frequency; 
  HuffmanNode left, right;

  // Constructor to initialize node with character and frequency
  HuffmanNode(char data, int frequency) {
    this.data = data;
    this.frequency = frequency;
    this.left = null;
    this.right = null;
  }
}

public class HuffmanEncoding {

  // Function to build Huffman tree from frequency map
  public static HuffmanNode buildHuffmanTree(FrequencyHashMap freqMap) {
    // Priority queue to build Huffman tree
    MinHeap pq = new MinHeap(256);

    // Iterate over frequency map to populate priority queue
    FrequencyHashMap.Entry[] entries = freqMap.entrySet(); 
    for (FrequencyHashMap.Entry entry : entries) {
      // check null entries 
      while (entry != null) {
        // Add nodes to priority queue
        pq.add(new HuffmanNode(entry.key, entry.value));
        entry = entry.next;
      }
    }

    // Build Huffman tree from priority queue
    // ensure the pq has more than one node 
    while (!pq.isEmpty() && pq.size() > 1) { 
      // Remove two nodes with lowest frequencies
      HuffmanNode left = pq.remove(); 
      HuffmanNode right = pq.remove(); 

      // Create internal node with combined frequency of the two nodes
      // represented with null character to show it's not actually a character node
      // but just an internal one 
      HuffmanNode internalNode = new HuffmanNode('\0', left.frequency + right.frequency);
      internalNode.left = left; 
      internalNode.right = right; 

      // Add internal node back to priority queue
      pq.add(internalNode);
    }

    // Return root of Huffman tree
    return pq.remove();
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter text for Huffman encoding: ");
    String input = scanner.nextLine(); 
    // Frequency map for characters
    FrequencyHashMap freqMap = new FrequencyHashMap(256);

    // Calculate character frequencies and print them
    // "test" -> ["t", "e", "s", "t"]
    for (char c : input.toCharArray()) { 
      // properlu update frequency
      freqMap.put(c, freqMap.getOrDefault(c, 0) + 1); 
    }
    printFrequencies(freqMap);

    // Build Huffman tree from frequency map
    HuffmanNode root = buildHuffmanTree(freqMap); 

    // Generate Huffman codes for characters
    CodeHashMap<Character, String> codes = new CodeHashMap<>(256);
    generateCodes(root, "", codes);

    // Encode input text using Huffman codes
    StringBuilder encoded = new StringBuilder(); 
    // Append Huffman code for each character
    for (char c : input.toCharArray()) {
      encoded.append(codes.getOrDefault(c, ""));
    }

    System.out.println("Encoded: " + encoded); 
    scanner.close(); 
  }

  // Recursive function to generate Huffman codes
  public static void generateCodes(
    HuffmanNode root,
    String code,
    CodeHashMap<Character, String> codes
  ) {
    // base case 
    if (root == null) return; 

    // If node is leaf node, assign code
    if (root.left == null && root.right == null) { 
      // If single node, assign "0" as code
      if (code.isEmpty()) { 
        code = "0"; 
      }
      // Assign code to character
      codes.put(root.data, code); 
      System.out.println("Character: " + root.data + ", Code: " + code);
    } else {
      // Traverse left and right for internal nodes
      generateCodes(root.left, code + "0", codes); 
      generateCodes(root.right, code + "1", codes); 
    }
  }

  // Method to print character frequencies
  private static void printFrequencies(FrequencyHashMap freqMap) {
     // Get entries from frequency map
    FrequencyHashMap.Entry[] entries = freqMap.entrySet();
    System.out.println("Character Frequencies:");
    // for loop to print each frequency
    for (FrequencyHashMap.Entry entry : entries) { 
      while (entry != null) { 
        System.out.println(entry.key + ": " + entry.value); 
        entry = entry.next; 
      }
    }
    System.out.println();
  }
}
