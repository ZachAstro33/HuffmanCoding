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

public class TestHuffmanEncoding {

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
    String input = "pussypp";
    SimpleMap freqMap = new SimpleMap(256);
    for (char c : input.toCharArray()) {
      freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
    }

    HuffmanNode root = buildHuffmanTree(freqMap);
    SimpleHashMap<Character, String> codes = new SimpleHashMap<>(256);
    generateCodes(root, "", codes);

    StringBuilder encoded = new StringBuilder();
    for (char c : input.toCharArray()) {
      encoded.append(codes.getOrDefault(c, ""));
    }

    System.out.println("Encoded: " + encoded);
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
}
