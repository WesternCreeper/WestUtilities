/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataStructures;

/**
 *
 * @author Westley
 * @param <E> The class that is represented in this data structure
 */
public class Queue<E>
{
    private LinkedListNode<E> top;
    private LinkedListNode<E> last;

    /**
    * Creates a standard empty queue 
    */
    public Queue() {}

    //Methods:
    /**
    * Tests to see if the queue is empty, and returns the result
    */
    public boolean isEmpty()
    {
        return top == null;
    }

    public void makeEmpty()
    {
        top = null;
        last = null;
    }

    public void enqueue(E obj)
    {
        //Make sure that we have a top
        if(isEmpty())
        {
            top = new LinkedListNode<E>(obj);
            last = top;
        }
        else //We have a top, add to the end
        {
            last.setNext(new LinkedListNode<E>(obj));
            last = last.next();
        }
    }

    /*
    * Returns and removes the front item in the list
    * Will return null if the front does not exist
    */
    public E dequeue()
    {
        LinkedListNode<E> result = top;
        if(!isEmpty())
        {
            top = top.next();
        }
        return result.getObject();
    }

    public E getFront()
    {
        return top.getObject();
    }
}
