package model.utilities;


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
     * Removes and returns an element from the queue
     * @return The first element of the queue, that will be removed
     */
    public T remove(){
        T t = list.get(0);
        list.remove(0);
        return t;
    }

    /**
     * Returns whether the queue is empty or not
     * @return Whether the queue is empty or not
     */
    public boolean isEmpty(){
        return list.isEmpty();
    }

}
