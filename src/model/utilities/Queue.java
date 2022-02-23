package model.utilities;


import model.interfaces.GraphNode;

/**
 * The class Queue implements a First-In-First-Out (FIFO) behaviour for the data introduced.
 * <p>Internally, it makes use of {@link model.utilities.ArrayList}
 * @param <T> The type of object the queue will hold
 */
public class Queue<T> {

    /**
     * Internally, our queue will use an arraylist.
     * We'll add elements to it, and always remove the first element
     */
    private final ArrayList<T> list;

    /**
     * Queue Default Constructor
     */
    public Queue(Class<T> tClass){
        list = new ArrayList<>(tClass);
    }

    /**
     * Adds an element to the queue
     * @param t The element to be added
     */
    public void add(T t){
        list.add(t);
    }

    /**
     * Removes and returns the first element of the queue
     * @return The first element of the queue, that will be removed
     */
    public T remove(){
        T t = list.get(0);
        list.remove(0);
        return t;
    }

    /**
     * Returns the first element of the queue
     * @return The first element of the queue
     */
    public T get(){
        return list.get(0);
    }

    /**
     * Returns the current size of the queue
     * @return The current size of the queue
     */
    public int size(){
        return list.size();
    }

    /**
     * Returns whether the queue is empty or not
     * @return Whether the queue is empty or not
     */
    public boolean isEmpty(){
        return list.isEmpty();
    }

}
