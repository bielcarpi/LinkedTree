package model.utilities;


/**
 * The class Queue implements a First-In-First-Out (FIFO) behaviour for the data introduced.
 * <p>Internally, it makes use of {@link ArrayList}
 * @param <T> The type of object the queue will hold
 */
public class Stack<T> {

    /**
     * Internally, our queue will use an arraylist.
     * We'll add elements to it, and always remove the first element
     */
    private final ArrayList<T> list;

    /**
     * Queue Default Constructor
     */
    public Stack(Class<T> tClass){
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
     * Removes and returns the last element of the queue
     * @return The last element of the queue, that will be removed
     */
    public T remove(){
        T t = list.get(list.size() - 1);
        list.remove(list.size() - 1);
        return t;
    }

    /**
     * Returns the last element of the queue
     * @return The last element of the queue
     */
    public T top(){
        return list.get(list.size() - 1);
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
