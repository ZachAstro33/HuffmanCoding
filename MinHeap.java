class MinHeap {

  private HuffmanNode[] heap;
  private int size;

  public MinHeap(int capacity) {
    heap = new HuffmanNode[capacity];
    size = 0;
  }

  public void add(HuffmanNode node) {
    if (size == heap.length) {
      throw new IllegalStateException("Heap is full");
    }
    heap[size] = node;
    bubbleUp();
    size++;
  }

  private void bubbleUp() {
    int index = size;
    while (
      index > 0 && heap[(index - 1) / 2].frequency > heap[index].frequency
    ) {
      swap((index - 1) / 2, index);
      index = (index - 1) / 2;
    }
  }

  private void swap(int index1, int index2) {
    HuffmanNode temp = heap[index1];
    heap[index1] = heap[index2];
    heap[index2] = temp;
  }

  public HuffmanNode remove() {
    if (size == 0) {
      return null;
    }
    HuffmanNode root = heap[0];
    heap[0] = heap[size - 1];
    size--;
    bubbleDown();
    return root;
  }

  private void bubbleDown() {
    int index = 0;
    while (index < size / 2) {
      int leftChildIndex = 2 * index + 1;
      int rightChildIndex = 2 * index + 2;

      int smallerChildIndex = leftChildIndex;
      if (
        rightChildIndex < size &&
        heap[rightChildIndex].frequency < heap[leftChildIndex].frequency
      ) {
        smallerChildIndex = rightChildIndex;
      }

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
