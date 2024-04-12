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
        left = right = null;
    }
}

public class HuffmanCoding {

    // Function to recursively build Huffman Tree from frequency map
    public static HuffmanNode buildHuffmanTree(Map<Character, Integer> freqMap) {
        // Priority queue to store nodes based on their frequencies
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>((a, b) -> a.frequency - b.frequency);

        // Create leaf nodes for each character and add them to the priority queue
        for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
            pq.offer(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        // Recursive helper function to build the tree
        return buildTreeHelper(pq);
    }

    // Recursive helper function to build Huffman Tree from priority queue
    private static HuffmanNode buildTreeHelper(PriorityQueue<HuffmanNode> pq) {
        // If there is only one node in the queue, it becomes the root of the tree
        if (pq.size() == 1) {
            return pq.poll();
        }

        // Take the two nodes with the lowest frequencies
        HuffmanNode left = pq.poll();
        HuffmanNode right = pq.poll();

        // Create a new internal node with these two nodes as children
        HuffmanNode internalNode = new HuffmanNode('\0', left.frequency + right.frequency);
        internalNode.left = left;
        internalNode.right = right;

        // Add the new internal node back to the priority queue
        pq.offer(internalNode);

        // Recursively build the rest of the tree
        return buildTreeHelper(pq);
    }

    // Recursive function to generate Huffman codes for each character
    public static void generateCodes(HuffmanNode root, String code, Map<Character, String> codes) {
        // If the node is null, return
        if (root == null) return;

        // If the node is a leaf, store the code for the character
        if (root.data != '\0') {
            codes.put(root.data, code);
        }

        // Recursively traverse left and right and append '0' for left and '1' for right
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

        // Step 2: Build Huffman Tree recursively
        HuffmanNode root = buildHuffmanTree(freqMap);

        // Step 3: Generate Huffman Codes recursively
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
        String input = "Huffman coding is a data compression algorithm";
        String encoded = encode(input);
        System.out.println("Encoded: " + encoded);
    }
}
