public class MinHeap {

  // Array to store Huffman nodes
  private HuffmanNode[] heap; 
  private int size; 

  // constructor
  public MinHeap(int capacity) {
    heap = new HuffmanNode[capacity]; 
    size = 0; 
  }

  public void add(HuffmanNode node) {
    // check for heap being full
    if (size == heap.length) {
      throw new IllegalStateException("Heap is full"); 
    }
    // Add new node to the end of the heap array
    heap[size] = node; 
    // ensure node is in correct position
    swim(); 
    size++; 
  }

  private void swim() {
     // Start with index of the last added node
    int index = size;
    // While the current node is not the root and its parent has higher frequency
    while (index > 0 && heap[(index - 1) / 2].frequency > heap[index].frequency) {
      swap((index - 1) / 2, index);
      index = (index - 1) / 2;
    }
  }

  private void swap(int index1, int index2) {
    // triple swap 
    HuffmanNode temp = heap[index1]; 
    heap[index1] = heap[index2]; 
    heap[index2] = temp; 
  }

  public HuffmanNode remove() {
    // if heap is empty return null 
    if (size == 0) { 
      return null;
    }
    HuffmanNode root = heap[0]; 
    // replace root node with last element 
    heap[0] = heap[size - 1];
    size--; 
    // sink node to proper position 
    sink();
    return root; 
  }

  private void sink() {
    // start at root 
    int index = 0; 
    // While node has at least one child
    while (index < size / 2) { 
      int leftChildIndex = 2 * index + 1; 
      int rightChildIndex = 2 * index + 2; 

      // assume left child is smaller for starters
      int smallerChildIndex = leftChildIndex; 
      // If right child exists and is smaller, update smallerChildIndex
      if (rightChildIndex < size && heap[rightChildIndex].frequency < heap[leftChildIndex].frequency) {
        smallerChildIndex = rightChildIndex; 
      }

      // If node is smaller than its smallest child, break loop
      if (heap[index].frequency <= heap[smallerChildIndex].frequency) {
        break; 
      }

      swap(index, smallerChildIndex);
      index = smallerChildIndex; 
    }
  }

  public int size() {
    return size; 
  }

  public boolean isEmpty() {
    return size == 0; 
  }
}