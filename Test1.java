public class Test1 {

  public static void main(String[] args) {
    testEmptyInput();
    testSingleCharacter();
    testUniformFrequency();
    testTypicalCase();
  }

  private static void testEmptyInput() {
    String input = "";
    String expected = "";
    System.out.println("Test Empty Input:");
    performTest(input, expected);
  }

  private static void testSingleCharacter() {
    String input = "aaaaaa";
    String expected = "000000"; // Assuming Huffman coding encodes 'a' as '0' due to high frequency
    System.out.println("Test Single Character:");
    performTest(input, expected);
  }

  private static void testUniformFrequency() {
    String input = "abcabcabc";
    String expected = "100111001110011";
    System.out.println("Test Uniform Frequency:");
    performTest(input, expected);
  }

  private static void testTypicalCase() {
    String input = "mississippi";
    String expected = "100011110111101011010";
    System.out.println("Test Typical Case:");
    performTest(input, expected);
  }

  private static void performTest(String input, String expected) {
    SimpleMap freqMap = new SimpleMap(256);
    for (char c : input.toCharArray()) {
      freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
    }

    HuffmanNode root = HuffmanCoding.buildHuffmanTree(freqMap);
    SimpleHashMap<Character, String> codes = new SimpleHashMap<>(256);
    HuffmanCoding.generateCodes(root, "", codes);

    StringBuilder encoded = new StringBuilder();
    for (char c : input.toCharArray()) {
      encoded.append(codes.getOrDefault(c, ""));
    }

    System.out.println("Input: " + input);
    System.out.println("Expected Encoded: " + expected);
    System.out.println("Actual Encoded: " + encoded);
    System.out.println(
      "Test " + (expected.equals(encoded.toString()) ? "PASSED" : "FAILED")
    );
    System.out.println();
  }
}
