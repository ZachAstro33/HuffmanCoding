import java.util.Scanner;

public class HuffmanEncoding {

  public static HuffmanNode buildHuffmanTree(SimpleMap freqMap) {
    MinHeap pq = new MinHeap(256); // Assume ASCII

    // Iterate over entries
    SimpleMap.Entry[] entries = freqMap.entrySet();
    for (SimpleMap.Entry entry : entries) {
      if (entry != null) {
        while (entry != null) {
          pq.add(new HuffmanNode(entry.key, entry.value));
          entry = entry.next;
        }
      }
    }

    while (!pq.isEmpty() && pq.size() > 1) {
      HuffmanNode left = pq.remove();
      HuffmanNode right = pq.remove();

      HuffmanNode internalNode = new HuffmanNode(
        '\0',
        left.frequency + right.frequency
      );
      internalNode.left = left;
      internalNode.right = right;

      pq.add(internalNode);
    }

    return pq.remove();
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter text for Huffman encoding: ");
    String input = scanner.nextLine();
    SimpleMap freqMap = new SimpleMap(256);

    // Calculate frequencies and print them
    for (char c : input.toCharArray()) {
      freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
    }
    printFrequencies(freqMap);

    HuffmanNode root = buildHuffmanTree(freqMap);
    SimpleHashMap<Character, String> codes = new SimpleHashMap<>(256);
    generateCodes(root, "", codes);

    StringBuilder encoded = new StringBuilder();
    for (char c : input.toCharArray()) {
      encoded.append(codes.getOrDefault(c, ""));
    }

    System.out.println("Encoded: " + encoded);
    scanner.close();
  }

  // Recursive apporach.
  public static void generateCodes(
    HuffmanNode root,
    String code,
    SimpleHashMap<Character, String> codes
  ) {
    if (root == null) return;

    // If the node is a leaf node, or if it's the only node (root with no children), assign a code
    if (root.left == null && root.right == null) {
      // If the tree has only one node, we still need to encode it. Assign "0" if no code has been assigned.
      if (code.isEmpty()) {
        code = "0";
      }
      codes.put(root.data, code);
      System.out.println("Character: " + root.data + ", Code: " + code);
    } else {
      // Traverse left and right
      generateCodes(root.left, code + "0", codes);
      generateCodes(root.right, code + "1", codes);
    }
  }

  // Method to print character frequencies
  private static void printFrequencies(SimpleMap freqMap) {
    SimpleMap.Entry[] entries = freqMap.entrySet();
    System.out.println("Character Frequencies:");
    for (SimpleMap.Entry entry : entries) {
      if (entry != null) {
        while (entry != null) {
          System.out.println(entry.key + ": " + entry.value);
          entry = entry.next;
        }
      }
    }
    System.out.println();
  }
}
