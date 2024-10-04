/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataStructures;

/**
 *
 * @author Westley
 * A list made of forward facing references. There is an additional capability to add items to the end of the list, however the basic design uses an internal iterator
 */
public class LinkedList<E>
{
    private LinkedListNode<E> header;
    private LinkedListNode<E> end;
    private LinkedListNode<E> current;

    /**
    * Creates a new empty linked list
    */
    public LinkedList()
    {
        header = new LinkedListNode<E>(null, null);
        end = header;
        current = header;
    }

    public boolean isEmpty()
    {
        return header.next() == null;
    }
    public void makeEmpty()
    {
        header.setNext(null);
        current = header;
    }

    /**
    * This adds an object to the end of the linked list. This does NOT change the position of current
    * @param e The object to tbe added to the end of the list.
    */
    public void add(E e)
    {
        LinkedListNode<E> newNode = new LinkedListNode<E>(e);
        end.setNext(newNode);
        end = newNode;
    }
    
    /**
    * This returns the current positon of the iterator object. If the iterator is not within proper bounds, then the output will be null
    * @return Object The object that was at the current iterator's position.
    */
    public E getCurrent()
    {
        //If the position is at the header, then don't return the header node
        if(current == header)
        {
            return null;
        }
        return current.getObject();
    }

    /**
    * This will advance the current position by one, if and only if the current object can
    */
    public void advance()
    {
        if(current != null && current.next() != null)
        {
            current = current.next();
        }
    }

    /**
    * Takes the iterator and puts it at the beginning position
    */
    public void front()
    {
        current = header;
    }

    /**
    * Inserts an object right after the current postion (So this is the same and add(e) when the current positon is the same as the end)
    * @param e The object to be added
    */
    public void insert(E e)
    {
        LinkedListNode<E> newNode = new LinkedListNode<E>(e, current.next());
        current.setNext(newNode);
        if(newNode.next() == null)
        {
            end = newNode;
        }
    }

    /**
    * Removes the node after the current position. If current is at the end of the list, then no objects are removed from the list. Returns the object that was removed
    * @return Object The object in the node that was removed, null if there is an error with that action
    */
    public E remove()
    {
        if(current == end)
        {
            return null;
        }
        LinkedListNode<E> oldNode = current.next();
        current.setNext(oldNode.next());
        return oldNode.getObject();
    }

    /**
    * Finds the node with the object in it, then sets current to its position
    * @param e The object that needs to be found in the linkedlist. If it cannot be found, the position of current is untouched
    */
    public void find(E e)
    {
        LinkedListNode<E> finder = header;
        while(finder.next() != null)
        {
            finder = finder.next();
            if(finder.getObject().equals(e))
            {
                current = finder;
                break;
            }
        }
    }
}
