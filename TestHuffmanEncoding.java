class HuffmanNode {

  char data; 
  int frequency; 
  HuffmanNode left, right; 

  
  HuffmanNode(char data, int frequency) {
    this.data = data;
    this.frequency = frequency;
    this.left = null;
    this.right = null;
  }
}

public class TestHuffmanEncoding {

  public static HuffmanNode buildHuffmanTree(FrequencyHashMap freqMap) {
    MinHeap pq = new MinHeap(256); 

    FrequencyHashMap.Entry[] entries = freqMap.entrySet();
    for (FrequencyHashMap.Entry entry : entries) {
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
    String input = "";
    FrequencyHashMap freqMap = new FrequencyHashMap(256);
    for (char c : input.toCharArray()) {
      freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
    }

    HuffmanNode root = buildHuffmanTree(freqMap);
    CodeHashMap<Character, String> codes = new CodeHashMap<>(256);
    generateCodes(root, "", codes);

    StringBuilder encoded = new StringBuilder();
    for (char c : input.toCharArray()) {
      encoded.append(codes.getOrDefault(c, ""));
    }

    System.out.println("Encoded: " + encoded);
  }

  public static void generateCodes(
    HuffmanNode root,
    String code,
    CodeHashMap<Character, String> codes
  ) {
    if (root == null) return;

    if (root.left == null && root.right == null) {
      if (code.isEmpty()) {
        code = "0";
      }
      codes.put(root.data, code);
      System.out.println("Character: " + root.data + ", Code: " + code);
    } else {
      generateCodes(root.left, code + "0", codes);
      generateCodes(root.right, code + "1", codes);
    }
  }
}
