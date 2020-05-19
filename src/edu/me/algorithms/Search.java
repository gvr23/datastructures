package edu.me.algorithms;

public class Search {
    /*
        Name: linearSearch
        Requirements: no additional place.
        Time Complexity: O(N)
        Space Complexity: O(1)
        For: Linked Lists, Unsorted Arrays
        Type: Linear Searching
        Disadvantages: Direct relation to the input amount
    */
    public static int linearSearch(int[] numbers, int criteria) {
        for (int number : numbers) {
            if (number == criteria) return number;
        }
        return -1;
    }
    /*
        Name: binarySearch
        Explanation: The idea is to look in the middle as a reference until the item is found
        Requirements: sorted in increasing order.
        Time Complexity: O(Log(N) for iterative version
        Space Complexity: O(1) for iterative version
        For:
        Type: Divide and Conquer
        Disadvantages:
    */
    public static int binarySearchRecursive(int[] numbers, int start, int end, int criteria) {
        if (start > end) return -1;
        int middle = (end + start) / 2;
        int numberExamined = numbers[middle];
        if (numberExamined == criteria) return numberExamined;
        else if (numberExamined > criteria) return binarySearchRecursive(numbers, start, middle - 1, criteria);
        else return binarySearchRecursive(numbers, middle + 1, end, criteria);
    }
    public static int binarySearch(int[] numbers, int criteria) {
        int start = 0;
        int end = numbers.length;

        while (start < end) {
            int middle = (start + end) / 2;
            int numberExamined = numbers[middle];

            if (numberExamined == criteria) return numberExamined;
            else if (numberExamined > criteria) end = middle - 1;
            else start = middle + 1;
        }
        return -1;
    }
     /*
        Name: interpolationSearch (estimateSearch)
        Explanation: Uses the value of the target to guess
        Requirements: i). sorted in increasing order ii). uniform distribution.
        Time Complexity: O(Log(Log(N)) evenly distributed, O(N) uneven
        Space Complexity: O(1)
        For: If we know in advance the structure of how the elements are stored to limit the closes searching index
        Type: Divide and Conquer
        Disadvantages:
    */
    public static int interpolationSearch(int[] numbers, int criteria) {
        int start = 0;
        int end = numbers.length - 1;

        while (numbers[start] <= criteria && numbers[end] >= criteria) {
            int searchIndex = start + ((criteria - numbers[start]) / (numbers[end] - numbers[start])) * (end - start);
            int numberExamined = numbers[searchIndex];

            if (numberExamined == criteria) return numberExamined;
            else if (numberExamined > criteria) end = searchIndex - 1;
            else start = searchIndex + 1;
        }
        return -1;
    }
}
