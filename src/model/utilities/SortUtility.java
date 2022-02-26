package model.utilities;

import model.interfaces.Graph;
import model.interfaces.GraphNode;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * {@code model.utilities.SortUtility} is a class designed to help with sorting arrays of {@code Objects}.
 * <p>As we want to order an Object T, we'll use {@code Generics}.
 * <br>
 * <p>The class will provide methods for sorting an array with different algorithms, such as merge sort, quick sort or bucket sort
 *
 * @see <a href="https://docs.oracle.com/javase/tutorial/java/generics/types.html">Generics</a>
 * @author bielcarpi
 * @version 1.0
 */
public class SortUtility {

    /**
     * Orders (using merge sort) an array of {@code T Objects} given a {@link Comparator<T>} for that same {@code object}
     * <p>The array passed as parameter won't be modified. The one ordered will be returned
     *
     * @see <a href="https://en.wikipedia.org/wiki/Merge_sort">Merge Sort</a>
     *
     * @param array The array that wants to be ordered
     * @param comparator The criteria that will order the array
     * @return the array provided, but ordered
     */
    public static <T> T[] mergeSort(T[] array, Comparator<T> comparator){
        //Trivial case --> If length of the array == 1, then the array is already sorted
        if(array.length == 1)
            return array;

        //Non-Trivial case --> if length != 0
        int mid = array.length/2; //Calculate middle position of the array

        //Split the current array in two parts: left and right
        T[] leftPart = Arrays.copyOfRange(array, 0, mid);
        T[] rightPart = Arrays.copyOfRange(array, mid, array.length);

        leftPart = mergeSort(leftPart, comparator); //MergeSort the left part of the current array
        rightPart = mergeSort(rightPart, comparator); //Merge sort the right part of the current array
        return merge(leftPart, rightPart, comparator); // Merge both left and right parts, in order to get an ordered array
    }

    private static <T> T[] merge(T[] leftPart, T[] rightPart, Comparator<T> comparator){
        //We need to merge both left and right parts ordered in the arrayOrdered.
        //We know both leftPart and rightPart arrays are ordered! We only need to merge them

        //Create new array of T[], that will contain the ordered elements in leftPart + rightPart
        @SuppressWarnings("unchecked")
        T[] arrayOrdered = (T[]) Array.newInstance(leftPart.getClass().getComponentType(), leftPart.length + rightPart.length);
        int i = 0; //Index of the arrayOrdered
        int l = 0, r = 0; //leftPart and rightPart cursors

        while(l < leftPart.length || r < rightPart.length){ //While the left and right cursors aren't on its end

            if(l == leftPart.length){ //If the whole left part is already on the arrayOrdered
                //Then let's add the next element of the right array
                arrayOrdered[i] = rightPart[r];
                r++;
            }
            else if(r == rightPart.length){ //If the whole right part is already on the arrayOrdered
                //Then let's add the next element of the left array
                arrayOrdered[i] = leftPart[l];
                l++;
            }
            else if(comparator.compare(leftPart[l], rightPart[r]) > 0){ //If leftPart is bigger than rightPart
                arrayOrdered[i] = leftPart[l];
                l++;
            }
            else if(comparator.compare(leftPart[l], rightPart[r]) < 0){ //If rightPart is bigger than leftPart
                arrayOrdered[i] = rightPart[r];
                r++;
            }
            else if(comparator.compare(leftPart[l], rightPart[r]) == 0){ //If rightPart and leftPart are equal, copy leftPart
                arrayOrdered[i] = leftPart[l];
                l++;
            }

            //Increment i
            i++;
        }

        return arrayOrdered;
    }


    /**
     * Orders (using quicksort) an array of {@code T Objects} given a {@link Comparator<T>} for that same {@code object}
     * <p>The array passed as parameter will be the one modified (by reference). If you don't want this behavior,
     *   be sure to pass a copy of the original array
     *
     * @see <a href="https://en.wikipedia.org/wiki/Quicksort">Quicksort</a>
     *
     * @param array The array that wants to be ordered
     * @param comparator The criteria that will order the array
     */
    public static <T> void quickSort(T[] array, Comparator<T> comparator){
        //This method is a facade, let's invoke the real quickSort
        quickSortImplementation(array, comparator, 0, array.length-1);
    }

    private static <T> void quickSortImplementation(T[] array, Comparator<T> comparator, int i, int j){
        //Remind that i = the right index of the array & j = the left index of the array.
        //The array is always full, but we want only to modify it from i to j (j included)

        //Trivial Case --> If i >= j, return (it means that the portion of the array we want to modify
        //  is length 1. In this case it is already ordered, so return)
        if(i >= j)
            return;

        //Non-Trivial Case --> If the portion of the array we want to modify isn't length 1
        int pivotIndex = partition(array, comparator, i , j); //Select a pivot. After this function ends, the pivot
        //  has to be in its correct place inside the array. All elements bigger on its left, all elements smaller
        //  on its right.
        quickSortImplementation(array, comparator, i, pivotIndex - 1); //Perform a quicksort with the array from i to pivotIndex
        quickSortImplementation(array, comparator, pivotIndex + 1, j); //Perform a quicksort with the array from pivotIndex+1 to j
    }

    private static <T> int partition(T[] array, Comparator<T> comparator, int i, int j){
        if(i >= j) return -1; //i can't be bigger or equal than j. If it's the case, return error.

        int l = i; //l is our leftCursor, starting from i
        int r = j - 1; //j is our rightCursor, starting from j-1(as j will be the position where we'll put the pivot)
        //The objective of this method is to select a pivot (object in the array) and, on the end, the pivot has to be in its correct place
        //  inside the array. That means: all elements bigger of the pivot on its left, all elements smaller on its right.

        int pivotIndex = (i+j)/2;
        T pivot = array[pivotIndex]; //Our pivot in this particular implementation will be the center element of the range of the array we're dealing with
        swap(array, j, pivotIndex); //Move the pivot to the last position of the array (j)
        //Before the method ends, we'll swap the rightCursor element with our pivot
        //  as the rightCursor element will be the last element that's smaller than our pivot
        //  when we end the algorithm.

        //There can be a special case when we decrease j. In a 2 position array, i == j after decreasing.
        //The process implemented won't work on this case, so we'll handle length 2 arrays on this conditional
        if(r == l){
            if(comparator.compare(array[l], pivot) < 0) { //If the element with position l is less than the pivot, swap
                swap(array, l, j); //Swap pivot with l. Now the array is ordered
                return l; //Return the position of the pivot
            }
            else return j; //If the element with position l is bigger than the pivot, return the pivotIndex (the array is ordered)
        }


        //Infinite loop. It will break when i<=j, but first we need to move our cursors to its
        //  correct position. In order to achieve this, we'll put the breaking conditional just
        //  before moving the cursors.
        while(true){

            //While the element on the left is bigger than the pivot (that's what we want), increase the leftCursor (i)
            //We'll also check that the cursorLeft isn't getting out of bounds
            while(l < j && comparator.compare(array[l], pivot) > 0)
                l++;

            //While the element on the right is smaller than the pivot (that's what we want), decrease rightCursor (j)
            //We'll also check that the cursorRight isn't getting out of bounds
            while(r > i && comparator.compare(array[r], pivot) < 0)
                r--;

            //If the leftCursor is bigger than the rightCursor (they'll never be equal) stop, we're done.
            if(l >= r) break;

            //If not, it means that we've found two values that need to be swapped
            //  (as we have a value on the right of the pivot that's bigger than
            //  it, and a value on the left of the pivot that's smaller than it)
            swap(array, l, r);

            //As we've swapped, move a position both in j and i
            l++;
            r--;
        }


        swap(array, l, j); //As explained before, move the pivot (stored in j) to the position it should be
        return l; //Return the position of our pivot
    }

    private static <T> void swap(T[] array, int pos1, int pos2){
        if(pos1 == pos2) return;

        //This method swaps pos1 and pos2 of the array. pos1 will become pos2, and vice versa.
        T aux = array[pos1];
        array[pos1] = array[pos2]; //pos1 is pos2
        array[pos2] = aux; //pos2 is pos1
    }

    private static Stack<GraphNode> topoSort(Graph g) {
        Stack<GraphNode> stack = new Stack<>(GraphNode.class);
        GraphNode n;

        // Visitem tots els nodes
        for (int i = 0; i < g.getGraph().length; i++) {
            if (!g.getGraph()[i].isVisited()){
                n = g.getGraph()[i];
                visita(g, n, stack);
            }
        }

        return stack;
    }

    private static void visita(Graph g, GraphNode n, Stack<GraphNode> stack) {
        GraphNode s;
        // Visitem els successors del node que estem visitant
        // per cada node del numero de successors del node
        for (int i = 0; i < g.getAdjacent(n).length; i++) {
            s = g.getAdjacent(n)[i];
            if (!s.isVisited()) {
                visita(g, s, stack);
            }
        }

        n.setVisited(true);
        stack.add(n);
    }
}

