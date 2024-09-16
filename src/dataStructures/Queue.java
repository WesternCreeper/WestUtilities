/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataStructures;

/**
 *
 * @author Westley
 */
public class Queue 
{
    private LinkedListNode top;
    private LinkedListNode last;

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

    public void enqueue(Object obj)
    {
        //Make sure that we have a top
        if(isEmpty())
        {
            top = new LinkedListNode(obj);
            last = top;
        }
        else //We have a top, add to the end
        {
            last.setNext(new LinkedListNode(obj));
            last = last.next();
        }
    }

    /*
    * Returns and removes the front item in the list
    * Will return null if the front does not exist
    */
    public Object dequeue()
    {
        LinkedListNode result = top;
        if(!isEmpty())
        {
            top = top.next();
        }
        return result;
    }

    public Object getFront()
    {
        return top;
    }
}
