package model.table;

import model.utilities.Tuple;

import java.util.ArrayList;

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
    private static final int DEFAULT_CAPACITY = 64; //Can only be po2!!

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
                if(internalArray[position].isEmpty()){ //If there are no more values on that node
                    internalArray[position] = null; //Delete the arrayList (GC)
                    filledPositions--;
                }
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

    private ArrayList<Tuple<K, V>> getAllValuesAsTuple(){
        ArrayList<Tuple<K, V>> values = new ArrayList<>();
        for(ArrayList<Tuple<K, V>> entry: internalArray) {
            if (entry != null)
                values.addAll(entry);
        }

        return values;
    }


    private int getHash(K key){
        String stringKey = key.toString();
        //Our hashing algorithm will sum half the letters of the string and multiply it by the other half
        long firstHalf = getInt(stringKey.substring(0, stringKey.length()/2));
        long secondHalf = getInt(stringKey.substring(stringKey.length()/2 + 1));
        long hash = firstHalf * secondHalf;

        //Now, in order to ensure our hash is between the internal array's possible values, we'll
        // have the po2 of the current value and obtain its log(R) central bits (being R the internal array length)
        hash = hash * hash;
        int bits = (int)(Math.log(internalArray.length) / Math.log(2));
        int movingPositions = Long.SIZE/2 - bits/2;
        hash = hash << movingPositions;
        hash = hash >>> movingPositions;
        hash = hash >>> movingPositions;

        return (int)hash; //Return the hash
    }

    private int getInt(String s){
        //Given a String, it returns the sum of all its characters in ASCII
        int sum = 0;
        for(int i = 0; i < s.length(); i++)
            sum += s.charAt(i) << i;

        return sum;
    }


    private void checkGrowing(){
        //If the filledPositions are more than the 70% of the array length, increase the internal array by 100%
        if(filledPositions > internalArray.length * 70.0/100.0){
            ArrayList<Tuple<K, V>> values = getAllValuesAsTuple();
            internalArray = new ArrayList[internalArray.length * 2];

            //Now that we have expanded the internal array, let's re-calculate positions for all the elements we had
            for(Tuple<K, V> t: values) put(t.getFirst(), t.getLast());
        }
    }


    public void printInternalStats(){
        int filledPositions = 0;
        int totalPositions = internalArray.length;
        double mitjaObjectesPerPosicio = 0;

        for(int i = 0; i < internalArray.length; i++){
            if(internalArray[i] != null){
                filledPositions++;
                mitjaObjectesPerPosicio += internalArray[i].size();
            }
        }

        mitjaObjectesPerPosicio = mitjaObjectesPerPosicio / filledPositions;

        System.out.println("Filled Positions -> " + filledPositions);
        System.out.println("Total Positions -> " + totalPositions);
        System.out.println("Mitja d'objectes per cada posicio ->" + mitjaObjectesPerPosicio);
    }
}
