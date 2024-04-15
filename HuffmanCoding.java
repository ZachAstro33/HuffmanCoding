import java.util.*;

// Node structure for the Huffman Tree
class HuffmanNode {

  char data; // Character stored in the node
  int frequency; // Frequency of the character
  HuffmanNode left, right; // Left and right children of the node

  // Constructor to initialize node with character and frequency
  HuffmanNode(char data, int frequency) {
    this.data = data;
    this.frequency = frequency;
    this.left = null;
    this.right = null;
  }
}

public class HuffmanCoding {

  // Function to recursively build Huffman Tree from frequency map
  public static HuffmanNode buildHuffmanTree(Map<Character, Integer> freqMap) {
    // Priority queue to store nodes based on their frequencies, with tie-breaking on characters
    PriorityQueue<HuffmanNode> pq = new PriorityQueue<>((a, b) -> {
      int freqComparison = Integer.compare(a.frequency, b.frequency);
      if (freqComparison != 0) {
        return freqComparison;
      }
      return Character.compare(a.data, b.data); // Tie-breaking based on character
    });

    // Create leaf nodes for each character and add them to the priority queue
    for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
      pq.offer(new HuffmanNode(entry.getKey(), entry.getValue()));
    }

    // Iterative helper function to build the tree
    while (pq.size() > 1) {
      // Take the two nodes with the lowest frequencies
      HuffmanNode left = pq.poll();
      HuffmanNode right = pq.poll();

      // Create a new internal node with these two nodes as children
      HuffmanNode internalNode = new HuffmanNode(
        '\0',
        left.frequency + right.frequency
      );
      internalNode.left = left;
      internalNode.right = right;

      // Add the new internal node back to the priority queue
      pq.offer(internalNode);
    }

    // The last node in the queue is the root of the Huffman tree
    return pq.poll();
  }

  // Recursive function to generate Huffman codes for each character
  public static void generateCodes(
    HuffmanNode root,
    String code,
    Map<Character, String> codes
  ) {
    if (root == null) return;

    if (root.data != '\0') {
      codes.put(root.data, code);
      System.out.println("Character: " + root.data + ", Code: " + code); // Debug output
    }

    generateCodes(root.left, code + "0", codes);
    generateCodes(root.right, code + "1", codes);
  }

  // Huffman encoding function
  public static String encode(String input) {
    // Step 1: Calculate frequency of each character in the input
    Map<Character, Integer> freqMap = new HashMap<>();
    for (char c : input.toCharArray()) {
      freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
    }

    // Step 2: Build Huffman Tree
    HuffmanNode root = buildHuffmanTree(freqMap);

    // Step 3: Generate Huffman Codes
    Map<Character, String> codes = new HashMap<>();
    generateCodes(root, "", codes);

    // Step 4: Encode the input using Huffman Codes
    StringBuilder encoded = new StringBuilder();
    for (char c : input.toCharArray()) {
      encoded.append(codes.get(c));
    }

    return encoded.toString();
  }

  // Main method to test the Huffman coding algorithm
  public static void main(String[] args) {
    String input = "test";
    String encoded = encode(input);
    System.out.println("Encoded: " + encoded);
  }
}
