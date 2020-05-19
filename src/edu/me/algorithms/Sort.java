package edu.me.algorithms;

public class Sort {
    /*
        Name: insertionSort
        Requirements: no additional place.
        Time Complexity: O(N^2)
        Space Complexity: O(N^2)
        For: Used when nearly sorted or if the input is small
        Type: Linear Sorting
        Disadvantages: doesn't scale well
    */
    public static int[] insertionSort(int[] unsortedArray) {
        int end = unsortedArray.length;
        for (int progress = 0; progress < end; progress++) {
            if (progress + 1 < end && unsortedArray[progress] > unsortedArray[progress + 1]) {
                int temp = unsortedArray[progress + 1];
                unsortedArray[progress + 1] = unsortedArray[progress];
                int correctIndex = progress;
                while (correctIndex > 0 && (unsortedArray[correctIndex - 1] > temp)) {
                    unsortedArray[correctIndex] = unsortedArray[correctIndex - 1];
                    correctIndex--;
                }
                unsortedArray[correctIndex] = temp;
            }
        }
        return unsortedArray;
    }
    /*
        Name: selectionSort
        Requirements: no additional place.
        Time Complexity: O(N^2)
        Space Complexity: O(1)
        For: Best suited for bigger values and small keys
        Type: Linear Sorting
        Disadvantages: doesn't scale well
    */
    public static int[] selectionSort(int[] unsortedArray) {
        int sorted, sequence, min, temp;

        for (sorted = 0; sorted < unsortedArray.length; sorted++) {
            min = sorted;
            for (sequence = sorted + 1; sequence < unsortedArray.length; sequence++) {
                if (unsortedArray[sequence] < unsortedArray[min]) min = sequence;
            }
            swap(unsortedArray, min, sorted);
        }

        return unsortedArray;
    }
    /*
        Name: mergeSort
        Requirements: no additional place.
        Time Complexity: O(Nlog(N))
        Space Complexity: O(N)
        For: Sorting linked list
        Type: Divide and Conquer
        Disadvantages: doesn't scale well
    */
    public static void mergeSort(int[] inputArray) {
        sort(inputArray, 0, inputArray.length - 1);
    }
    /*
        Name: bubbleShakeSort
        Requirements: no additional place.
        Time Complexity: O(N^2)
        Space Complexity: O(1)
        For:
        Type: Linear Sorting
        Disadvantages:
    */
//    QUICKSORT
    public static void bubbleShakeSort(int[] inputArray) {
        int end = inputArray.length;
        boolean changed = true;
        int left = 1;
        int right = end - 1;

        while (right >= left && changed) {
            changed = false;
            // right to left
            for (int rightCursor = right; rightCursor >= left; rightCursor--) {
                if (inputArray[rightCursor - 1] > inputArray[rightCursor]) {
                    swap(inputArray, rightCursor - 1, rightCursor);
                    changed = true;
                }
            }
            // left to right
            for (int leftCursor = left; leftCursor <= right; leftCursor++) {
                if (inputArray[leftCursor - 1] > inputArray[leftCursor]) {
                    swap(inputArray, leftCursor - 1, leftCursor);
                    changed = true;
                }
            }
            left++;
            right--;
        }
    }
     /*
        Name: shellSort
        Requirements: no additional place.
        Time Complexity: between O(N) and O(N^2) fastest of all the quadratic time complexity
        Space Complexity: O(N)
        For: Best for medium size list  less > 5000, repetitive sorting of smaller lists
        Type: Linear Sorting, unstable
        Disadvantages: doesn't scale well for 5000 and up elements
    */
    public static void shellSort(int[] inputArray) {
        int size = inputArray.length;
        int gap = size / 2;

        while (gap > 0) {
            for (int current = 0; current + gap < size; current = current + gap) {
                if (inputArray[current] > inputArray[current + gap]) {
                    swap(inputArray, current + gap, current);
                }
                if (current - gap >= 0) {
                    int back = current - gap;
                    while (back >= 0 && inputArray[back] > inputArray[back + gap]) {
                        swap(inputArray, back + gap, back);
                        back -= gap;
                    }
                }
            }
            gap /= 2;
        }
    }
    /*
        Name: countingSort
        Explanation: Tries to determine how many elements are before an X input
        Requirements: i). know or figure out the max element in advance ii). Assumes all elements are integers.
        Time Complexity: O(N)
        Space Complexity: O(N)
        For: Sorting data in a limited range
        Type: Not comparison based, Linear Sorting, unstable
        Disadvantages: only with values between 0 and 1000
    */
    public static void countingSort(int[] numbers) {
        int size = numbers.length;
        int max = getMax(numbers);
        int[] countArray = new int[max + 1];
        int[] resultArray = new int[size];
        for (int number : numbers) {
            countArray[number]++;
        }
        // Get the accrue of the integers to tell the frequency
        for (int accrue = 1; accrue <= max; accrue++) {
            countArray[accrue] += countArray[accrue - 1];
        }
        //Decrement to see how many numbers come before the desired one
        for (int current = 0; current <= max; current++) {
            countArray[current]--;
        }

        for (int current = size - 1; current >= 0; current--) {
            int numberOfElementsBefore =  countArray[numbers[current]];
            resultArray[numberOfElementsBefore] = numbers[current];
            countArray[numbers[current]]--;
        }
        //copy of the result to the original array
        for (int current = 0; current < size; current++) {
            numbers[current] = resultArray[current];
        }
    }
    /*
        Name: radixSort
        Explanation: Tries to determine how many elements are before each digit of an X input.
        Requirements: i). all numbers must be digits.
        Time Complexity: O(N)
        Space Complexity: O(N)
        For: Sorting data without a limited range because it always has ten buckets to sort 0 - 9
        Type: Not comparison based, Linear Sorting, unstable
        Disadvantages: range is higher than in counting sort
    */
    public static void radixSort(int[] inputArray) {
        int max = getMax(inputArray);
        for (int placeValue = 1; max / placeValue > 0; placeValue *= 10) {
            countSort(inputArray, placeValue);
        }
    }

    //    HELPER METHODS
    private static void merge(int[] numbers, int start, int mid, int end) {
        int[] tempArray = new int[end - start + 1];
        int leftSlot = start, rightSlot = mid + 1, indexC = 0;

        while (leftSlot <= mid && rightSlot <= end) {
            if (numbers[leftSlot] < numbers[rightSlot]) {
                tempArray[indexC] = numbers[leftSlot];
                leftSlot++;
            } else {
                tempArray[indexC] = numbers[rightSlot];
                rightSlot++;
            }
            indexC++;
        }

        while (leftSlot <= mid) {
            tempArray[indexC] = numbers[leftSlot];
            leftSlot++;
            indexC++;
        }
        while (rightSlot <= end) {
            tempArray[indexC] = numbers[rightSlot];
            rightSlot++;
            indexC++;
        }
        for (int marker = 0; marker < tempArray.length; marker++) {
            numbers[start + marker] = tempArray[marker];
        }
    }
    private static void sort(int[] numbers, int start, int end) {
        if (end <= start) {
            return;
        }
        int mid = (start + end) / 2;
        sort(numbers, start, mid);
        sort(numbers, mid + 1, end);
        merge(numbers, start, mid, end);
    }
    private static void swap(int[] inputArray, int misplaced, int currentPosition) {
        int temp = inputArray[misplaced];
        inputArray[misplaced] = inputArray[currentPosition];
        inputArray[currentPosition] = temp;
    }
    private static int getMax(int[] inputArray) {
        int max = inputArray[0];
        for (int current = 1; current < inputArray.length; current++) {
            if (inputArray[current] > max) max = inputArray[current];
        }
        return max;
    }
    private static void countSort(int[] digits, int placeValue) {
        int size = digits.length;
        int[] repetitions = new int[10];
        int[] output = new int[size];
        for (int current = 0; current < size; current++) {
            int singleDigit = (digits[current] / placeValue) % 10;
            repetitions[singleDigit]++;
        }
        for (int accrue = 1; accrue < 10; accrue++) {
            repetitions[accrue] += repetitions[accrue - 1];
        }
        for (int current = 0; current < 10; current++) {
            repetitions[current]--;
        }
        for (int current = size - 1; current >= 0; current--) {
            int elementsBehind = repetitions[(digits[current] / placeValue) % 10];
            output[elementsBehind] = digits[current];
            repetitions[(digits[current] / placeValue) % 10]--;
        }
//        copy of each iteration back to the original array
        for (int current = 0; current < size; current++) {
            digits[current] = output[current];
        }

    }
//    HELPER METHODS
}
