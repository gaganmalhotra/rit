/* 
 * MergeKSortedList.java 
 * 
 * Version: 1.1
 * 
 * 
 */

import java.util.Scanner;

public class MergeKSortedList {

	// This class integer array holds the final array that saves the sorted k
	// lists
	public static int[] baseHeap;
	// This class variable keeps the track of the array size that is required to
	// be declared for final array
	public static int arraySize = 0;

	/**
	 * MergeKSortedList()- Constructor that initializes the baseHeap int array
	 * with size as passed in the argument
	 * 
	 * @param int size array length to be initialized.
	 */
	MergeKSortedList(int size) {
		baseHeap = new int[size];
	}

	/**
	 * Driver method to take all the array as input from scanner and build heap to print the sorted elements
	 */
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		int totalArrays = sc.nextInt();
		int[][] allArray = new int[totalArrays][];

		int maxCol = 0;
		int totalElem = 0;
		for (int i = 0; i < totalArrays; i++) {
			int noOfElem = sc.nextInt();
			if (noOfElem > maxCol) {
				maxCol = noOfElem; // size of the largest array input
			}
			totalElem += noOfElem; // the final array size containing all the
									// elements
			allArray[i] = new int[noOfElem];
			for (int j = 0; j < noOfElem; j++) {
				allArray[i][j] = sc.nextInt();
			}
		}
		sc.close();

		MergeKSortedList obj = new MergeKSortedList(totalElem);
		int pointer = 0; // variable that is used to keep track of the index to
							// be added into the static int[] baseHeap
		while (pointer < totalElem) {
			for (int j = 0; j < maxCol; j++) {
				for (int i = 0; i < totalArrays; i++) {
					if (j < allArray[i].length) {
						obj.add(allArray[i][j], pointer++);
					}
				}
			}
		}
		arraySize = totalElem;
		for (int i = 0; i < totalElem; i++) {
			System.out.print(obj.extractMin(baseHeap) + " ");
		}
	}

	/**
	 * add()- This method adds the element to the heap and balances the heap by
	 * calling heapifyUp recursively.
	 * 
	 * @param int addVal Element value to be added into heap
	 * 
	 * @param int i Value of the current index at which the element has to be
	 *        added
	 */
	public void add(int addVal, int i) {
		int parent = (i - 1) / 2;
		if (i > 0) {
			baseHeap[i] = addVal;
			if (baseHeap[i] < baseHeap[parent]) {
				heapifyUp(baseHeap, i);
			}
		} else {
			baseHeap[0] = addVal;
		}

	}

	/**
	 * extractMin()- This method extracts the minimum element(first) of the
	 * minimum heap and replaces the last element in the heap to the first
	 * element and then calls the heapifyDown() method that balances the heap.
	 * 
	 * @param int[] array input integer array that holds the heap
	 * 
	 * @return int returns the least element at the first position in the array
	 */
	public int extractMin(int[] array) {
		int min = array[0];
		array[0] = array[arraySize - 1];
		arraySize--;
		heapifyDown(array, 0);
		return min;
	}

	/**
	 * heapifyDown()- This method is used to balance the heap by checking the
	 * passed index value with all the child nodes below it.
	 * 
	 * @param int[] array Input array that holds the heap
	 * 
	 * @param int index Position below which the child nodes are required to be
	 *        balanced.
	 * 
	 * @return int[] Returns the integer array of the balanced heap
	 */
	public int[] heapifyDown(int[] array, int index) {
		int leftChild = (2 * index) + 1;
		int rightChild = (2 * index) + 2;
		if (rightChild < array.length || leftChild < array.length) {
			if (rightChild <= arraySize) {
				if (array[rightChild] > array[leftChild]) {
					if (array[index] > array[leftChild]) {
						swap(index, leftChild, array);
						heapifyDown(array, leftChild);
					}
				} else {
					if (array[index] > array[rightChild]) {
						swap(index, rightChild, array);
						heapifyDown(array, rightChild);
					}
				}
			} else if (leftChild <= arraySize) {
				if (array[index] < array[leftChild]) {
					swap(index, leftChild, array);
					heapifyDown(array, leftChild);
				}
			}
		}
		return array;
	}

	/**
	 * heapifyUp()- This method is used to balance the heap by checking the
	 * passed index value with all the parent nodes above it recursively.
	 * 
	 * @param int[] array Input array that holds the heap
	 * 
	 * @param int index Position above which the tree has to be balanced.
	 * 
	 * @return int[] Returns the integer array of the balanced heap
	 */
	public int[] heapifyUp(int[] array, int index) {
		int parent = (index - 1) / 2;
		if (array[index] < array[parent]) {
			swap(index, parent, array);
			heapifyUp(array, parent);
		}
		return array;
	}

	/**
	 * swap()- swaps the two position data in the array passed as an argument.
	 * 
	 * @param int pos1 index value of position 1 to be swapped.
	 * 
	 * @param int pos2 index value of the position 2 to be swapped.
	 * 
	 * @return int[] array swapped position values in the array
	 */
	public int[] swap(int pos1, int pos2, int[] array) {
		int temp;
		temp = array[pos1];
		array[pos1] = array[pos2];
		array[pos2] = temp;
		return array;
	}
}