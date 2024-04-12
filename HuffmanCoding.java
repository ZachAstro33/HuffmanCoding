import java.util.*;

class HuffmanNode {
    char data;
    int frequency;
    HuffmanNode left, right;

    HuffmanNode(char data, int frequency) {
        this.data = data;
        this.frequency = frequency;
        left = right = null;
    }
}

public class HuffmanCoding {
    // Build Huffman Tree and return the root
    public static HuffmanNode buildHuffmanTree(Map<Character, Integer> freqMap) {
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>((a, b) -> a.frequency - b.frequency);

        // Create a leaf node for each character and add it to the priority queue
        for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
            pq.offer(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        // Build the Huffman Tree
        while (pq.size() > 1) {
            // Take the two nodes with the lowest frequency
            HuffmanNode left = pq.poll();
            HuffmanNode right = pq.poll();

            // Create a new internal node with these two nodes as children
            HuffmanNode internalNode = new HuffmanNode('\0', left.frequency + right.frequency);
            internalNode.left = left;
            internalNode.right = right;

            // Add the new internal node back to the priority queue
            pq.offer(internalNode);
        }

        // Return the root of the Huffman Tree
        return pq.poll();
    }

    // Traverse the Huffman Tree and generate codes for each character
    public static void generateCodes(HuffmanNode root, String code, Map<Character, String> codes) {
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

    public static void main(String[] args) {
        String input = "Huffman coding is a data compression algorithm";
        String encoded = encode(input);
        System.out.println("Encoded: " + encoded);
    }
}
