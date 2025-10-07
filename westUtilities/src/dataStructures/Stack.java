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
public class Stack <E>
{
    private LinkedListNode<E> top;

    /**
    * Creates a standard empty stack
    */
    public Stack() {}

    public boolean isEmpty()
    {
        return top == null;
    }

    public void makeEmpty()
    {
        top = null;
    }

    public void push(E obj)
    {
        //Just make the new top the pushed one:
        top = new LinkedListNode<E>(obj, top);
    }

    /**
    * This function grabs the top of the stack. If this is empty then it will return FAILED_RETREIVE
    */
    public E top()
    {
        return top.getObject();
    }
    /**
    * This function removes the top object. If the stack is already empty, then this function will do nothing
    */
    public void pop()
    {
        if(!isEmpty())
        {
            top = top.next();
        }
    }
    /**
    * This function grabs the top of the stack, and removes it. If this is empty then it will return FAILED_RETREIVE
    */
    public E popTop()
    {
        LinkedListNode<E> result = top;
        if(!isEmpty())
        {
            top = top.next();
        }
        return result.getObject();
    }
}
