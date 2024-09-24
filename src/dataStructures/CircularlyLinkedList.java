/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataStructures;

/**
 *
 * @author Westley
 * A list made of forward and backward references but in a circular design so there is no top and no bottom. This design uses an internal iterator
 */
public class CircularlyLinkedList
{
    private DoublyLinkedListNode current;

    public CircularlyLinkedList() {}

    public boolean isEmpty()
    {
        return current == null;
    }
    public void makeEmpty()
    {
        current = null;
    }

    /**
    * This returns the current positon of the iterator object. If the iterator is not within proper bounds, then the output will be null
    * @return Object The object that was at the current iterator's position.
    */
    public Object getCurrent()
    {
        //If the position is at the header, then don't return the header node
        if(current == null)
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
        if(current != null)
        {
            current = (DoublyLinkedListNode)current.next();
        }
    }

    /**
    * This will advance the current position by one, if and only if the current object can
    */
    public void retreat()
    {
        if(current != null)
        {
            current = (DoublyLinkedListNode)current.back();
        }
    }

    /**
    * Inserts an object right after the current postion (So this is the same and add(e) when the current positon is the same as the end) Changes the position of current to the new object
    * @param e The object to be added
    */
    public void insertNext(Object e)
    {
        if(current == null)
        {
            current = new DoublyLinkedListNode(e);
            current.setNext(current);
            current.setBack(current);
        }
        else
        {
            DoublyLinkedListNode newNode = new DoublyLinkedListNode(e, current.next(), current);
            current.setNext(newNode);
            ((DoublyLinkedListNode)newNode.next()).setBack(newNode);
            current = newNode;
        }
    }

    /**
    * Inserts an object right before the current postion (So this is the same and add(e) when the current positon is the same as the end) Changes the position of current to the new object
    * @param e The object to be added
    */
    public void insertBack(Object e)
    {
        if(current == null)
        {
            current = new DoublyLinkedListNode(e);
            current.setNext(current);
            current.setBack(current);
        }
        else
        {
            DoublyLinkedListNode newNode = new DoublyLinkedListNode(e, current, current.back());
            current.back().setNext(newNode);
            ((DoublyLinkedListNode)current).setBack(newNode);
            current = newNode;
        }
    }

    /**
    * Removes the current node. If current is at the end of the list, then no objects are removed from the list. Returns the object that was removed. The position of current will the the object before the removed item.
    * @return Object The object in the node that was removed, null if there is an error with that action
    */
    public Object remove()
    {
        DoublyLinkedListNode oldNode = current;
        if(current != null && current.next() != current)
        {
            current = (DoublyLinkedListNode)current.back();
            current.setNext(oldNode.next());
            ((DoublyLinkedListNode)current.next()).setBack(current);
            return oldNode.getObject();
        }
        else if(current.next() == current)
        {
            current = null;
            return oldNode.getObject();
        }
        return null;
    }

    /**
    * Removes the next node. If current is at the end of the list, then no objects are removed from the list. Returns the object that was removed. The position of current will remain the same
    * @return Object The object in the node that was removed, null if there is an error with that action
    */
    public Object removeNext()
    {
        DoublyLinkedListNode oldNode = (DoublyLinkedListNode)current.next();
        if(current.next() == current)
        {
            current = null;
            return oldNode;
        }
        current.setNext(oldNode.next());
        ((DoublyLinkedListNode)oldNode.next()).setBack(current);
        return oldNode.getObject();
    }

    /**
    * Removes the previous node. If current is at the end of the list, then no objects are removed from the list. Returns the object that was removed. The position of current will remain the same
    * @return Object The object in the node that was removed, null if there is an error with that action
    */
    public Object removeBack()
    {
        DoublyLinkedListNode oldNode = (DoublyLinkedListNode)current.back();
        if(current.back() == current)
        {
            current = null;
            return oldNode;
        }
        oldNode.back().setNext(current);
        current.setBack(oldNode.back());
        return oldNode.getObject();
    }

    /**
    * Finds the node with the object in it, then sets current to its position
    * @param e The object that needs to be found in the linkedlist. If it cannot be found, the position of current is untouched
    */
    public void find(Object e)
    {
        DoublyLinkedListNode finder = current;
        while(finder.next() != current) //No looping forever
        {
            finder = (DoublyLinkedListNode)finder.next();
            Object obj = finder.getObject();
            if(obj != null && obj.equals(e))
            {
                current = finder;
                break;
            }
        }
    }
}
