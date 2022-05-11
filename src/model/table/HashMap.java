package model.table;

import model.utilities.Tuple;

import java.util.ArrayList;
import java.util.Arrays;

public class HashMap<K, T> {

    private ArrayList<Tuple<K, T>>[] internalArray; //Each position of the array has an arraylist
    private int filledPositions; //The positions of the array that have been filled with an arraylist
    private static final int DEFAULT_CAPACITY = 500;

    public HashMap(){
        internalArray = new ArrayList[DEFAULT_CAPACITY];
        filledPositions = 0;
    }

    public void put(K key, T object){
        int position = getHash(key);
        if(internalArray[position] == null){
            internalArray[position] = new ArrayList<>();
            filledPositions++;
        }

        internalArray[position].add(new Tuple<>(key, object));
        checkGrowing(); //Checks if the internal array needs to grow
    }

    public boolean remove(K key){
        int position = getHash(key);
        if(internalArray[position] == null) return false;

        for(Tuple<K, T> entry: internalArray[position]){
            if(entry.getFirst().equals(key)){
                internalArray[position].remove(entry);
                filledPositions--;
                return true;
            }
        }

        return false;
    }

    public T get(K key){
        int position = getHash(key);
        if(internalArray[position] == null) return null;

        for(Tuple<K, T> entry: internalArray[position])
            if(entry.getFirst().equals(key)) return entry.getLast();

        return null;
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
