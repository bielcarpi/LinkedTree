package model.table;

import model.utilities.Tuple;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The class HashMap implements basic functionality of a Hash Table
 * <p>Its internal space grows dynamically and ensures that there is always space to add new elements
 * <p>It does not implement the {@link java.util.Map} interface, but it provides basic HashMap functionality,
 * with methods such as {@link #put(Object, Object)}, {@link #remove(Object)} and {@link #get(Object)}
 *
 * @param <K> The type of key that will be used (take into account that the method {@code K.toString()} will
 *           be used as a String is necessary for the key to be generated)
 * @param <V> The type of object that will be saved
 */
public class HashMap<K, V> {

    private ArrayList<Tuple<K, V>>[] internalArray; //Each position of the array has an arraylist
    private int filledPositions; //The positions of the array that have been filled with an arraylist
    private static final int DEFAULT_CAPACITY = 500;

    /**
     * Default Constructor.
     * <p>It creates a new HashMap with the default capacity
     */
    public HashMap(){
        internalArray = new ArrayList[DEFAULT_CAPACITY];
        filledPositions = 0;
    }

    /**
     * Given a key and value, the method puts it into the hashmap for later retrieval
     * @param key The key of the object
     * @param object The object
     */
    public void put(K key, V object){
        int position = getHash(key);
        if(internalArray[position] == null){
            internalArray[position] = new ArrayList<>();
            filledPositions++;
        }

        internalArray[position].add(new Tuple<>(key, object));
        checkGrowing(); //Checks if the internal array needs to grow
    }

    /**
     * Given a key, if the object is found in the hashmap it is removed
     * @param key The key of the object that wants to be deleted
     * @return Whether the deletion has been successful (the object existed) or not
     */
    public boolean remove(K key){
        int position = getHash(key);
        if(internalArray[position] == null) return false;

        for(Tuple<K, V> entry: internalArray[position]){
            if(entry.getFirst().equals(key)){
                internalArray[position].remove(entry);
                filledPositions--;
                return true;
            }
        }

        return false;
    }

    /**
     * Given a key, it returns the value for it on the hashmap
     * @param key The key to retrieve the value
     * @return The value for the given key
     */
    public V get(K key){
        int position = getHash(key);
        if(internalArray[position] == null) return null;

        for(Tuple<K, V> entry: internalArray[position])
            if(entry.getFirst().equals(key)) return entry.getLast();

        return null;
    }

    /**
     * Returns all the values currently contained in the hashmap within an ArrayList of V
     * @return All the values currently contained in the hashmap
     */
    public ArrayList<V> getAllValues(){
        ArrayList<V> values = new ArrayList<>();
        for(ArrayList<Tuple<K, V>> entry: internalArray) {
            if (entry != null)
                for (Tuple<K, V> t : entry) values.add(t.getLast());
        }

        return values;
    }


    private int getHash(K key){
        String stringKey = key.toString();
        //TODO: Make hash & return it
        return 0;
    }


    private void checkGrowing(){
        //If the filledPositions are more than the 70% of the array length, increase the internal array by 50%
        if(filledPositions * 70/100 > internalArray.length)
            internalArray = Arrays.copyOf(internalArray, internalArray.length * 150/100);
    }
}
