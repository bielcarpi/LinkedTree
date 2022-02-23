package model.utilities;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * The class ArrayList is a resizable array.
 * <p>It grows dynamically and ensures that there is always space to add new elements
 * <p>It does not implement the {@link java.util.List} interface, but it provides basic ArrayList functionality,
 * with methods such as {@link #add(Object)}, {@link #get(int)} and {@link #remove(int)}
 * 
 * @param <T> The type of object that the ArrayList will hold
 */
public class ArrayList<T> {
    private static final int DEFAULT_CAPACITY = 15;

    /**
     * The reference to the array that holds the data.
     */
    private T[] data;

    /**
     * The index where a new value will be in the array
     */
    private int newValue = 0;

    /**
     * For creating a generic array, we need its class.
     * We'll ask it on ArrayList instantiation.
     */
    private final Class<T> tClass;


    /**
     * ArrayList Constructor that allows you to define an initial capacity.
     * @param initialCapacity The initial capacity of the ArrayList (must be 1 or greater)
     * @param tClass The class of the object that will be held in this ArrayList
     * @throws IllegalArgumentException if the initialCapacity provided is less or equal than 0
     */
    public ArrayList(int initialCapacity, Class<T> tClass){
        if(initialCapacity <= 0) throw new IllegalArgumentException("Illegal Initial Capacity");
        this.tClass = tClass;

        @SuppressWarnings("unchecked")
        T[] d = (T[]) Array.newInstance(this.tClass, initialCapacity);
        data = d;
    }

    /**
     * Default ArrayList Constructor
     * <p>Initializes a new ArrayList with the default initial capacity
     * @param tClass The class of the object that will be held in this ArrayList
     */
    public ArrayList(Class<T> tClass){
        this(DEFAULT_CAPACITY, tClass);
    }

    /**
     * Adds a new object of type T to the ArrayList
     * @param t The object to be added to the ArrayList
     */
    public void add(T t){
        if(newValue >= data.length) grow();

        data[newValue] = t;
        newValue++;
    }

    /**
     * Returns the object in the index specified
     * @param index The index of the object that will be returned
     * @throws IllegalArgumentException If the index specified does not exist
     */
    public T get(int index){
        if(index < 0 || index > newValue - 1) throw new IllegalArgumentException("The index specified does not exist");

        return data[index];
    }

    /**
     * Removes the element from the specified index
     * @param index The index of the element to be removed
     * @throws IllegalArgumentException If the index specified does not exist
     */
    public void remove(int index){
        if(index < 0 || index > newValue-1) throw new IllegalArgumentException("The index specified does not exist");

        T[] oldArray = data;

        @SuppressWarnings("unchecked")
        T[] d = (T[]) Array.newInstance(this.tClass, data.length - 1);
        data = d;
        newValue--;

        System.arraycopy(oldArray, 0, data, 0, index); //Copy from 0 to index
        System.arraycopy(oldArray, index+1, data, index, oldArray.length - index - 1); //Copy from index+1 to final
    }

    /**
     * Removes the first occurrence of the element introduced
     * @param t The element to be removed
     */
    public void remove(T t){
        for(int i = 0; i < newValue; i++){
            if(data[i].equals(t)){
                remove(i);
                return;
            }
        }
    }

    /**
     * Returns whether the ArrayList is empty or not
     * @return Whether the ArrayList is empty or not
     */
    public boolean isEmpty(){
        return data[0] == null;
    }

    /**
     * Returns the size of the ArrayList (how many elements there are)
     * @return Int representing how many elements there are
     */
    public int size(){
        return newValue;
    }

    /**
     * Increases the capacity of the internal array by 50%
     * <p>More specifically, it creates a new array 50% bigger than the old one and
     * drops the reference to the old one for garbage collection.
     */
    private void grow() {
        int oldCapacity = data.length;
        int newCapacity = oldCapacity*3/2; //Increase the ArrayList by 50%

        data = Arrays.copyOf(data, newCapacity);
    }
}
