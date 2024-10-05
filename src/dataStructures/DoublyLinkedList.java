/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataStructures;

/**
 *
 * @author Westley
 * A list made of forward and backward references allowing for bidirectional movement. This design uses an internal iterator
 * @param <E> The class that is represented in this data structure
 */
public class DoublyLinkedList<E>
{
    private DoublyLinkedListNode<E> header;
    private DoublyLinkedListNode<E> tail;
    private DoublyLinkedListNode<E> current;

    public DoublyLinkedList()
    {
        header = new DoublyLinkedListNode<E>(null, null);
        tail = new DoublyLinkedListNode<E>(null, header);
        header.setNext(tail);
        current = header;
    }

    public boolean isEmpty()
    {
        return header.next() == null;
    }
    public void makeEmpty()
    {
        header.setNext(tail);
        tail.setBack(header);
        current = header;
    }
    
    /**
    * This adds an object to the end of the linked list. This does NOT change the position of current
    * @param e The object to tbe added to the end of the list.
    */
    public void add(E e)
    {
        DoublyLinkedListNode<E> newNode = new DoublyLinkedListNode<E>(e);
        tail.back().setNext(newNode);
        newNode.setBack(tail.back());
        tail.setBack(newNode);
        newNode.setNext(tail);
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
        if(current != null && current.next() != tail)
        {
            current = (DoublyLinkedListNode<E>)current.next();
        }
    }
    public boolean canAdvance()
    {
        if(current != null && current.next() != tail)
        {
            return true;
        }
        return false;
    }

    /**
    * This will advance the current position by one, if and only if the current object can
    */
    public void retreat()
    {
        if(current != null && (DoublyLinkedListNode<E>)current.back() != header)
        {
            current = (DoublyLinkedListNode<E>)current.back();
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
    * Takes the iterator and puts it at the beginning position
    */
    public void last()
    {
        current = header;
    }

    /**
    * Inserts an object right after the current postion (So this is the same and add(e) when the current positon is the same as the end) The position of current remains unchanged
    * @param e The object to be added
    */
    public void insertNext(E e)
    {
        DoublyLinkedListNode<E> newNode = new DoublyLinkedListNode<E>(e, current.next(), current);
        current.setNext(newNode);
        ((DoublyLinkedListNode<E>)newNode.next()).setBack(newNode);
    }

    /**
    * Inserts an object right before the current postion (So this is the same and add(e) when the current positon is the same as the end) The position of current remains unchanged
    * @param e The object to be added
    */
    public void insertBack(E e)
    {
        DoublyLinkedListNode<E> newNode = new DoublyLinkedListNode<E>(e, current, current.back());
        current.back().setNext(newNode);
        ((DoublyLinkedListNode<E>)current).setBack(newNode);
    }

    /**
    * Removes the current node. If current is at the end of the list, then no objects are removed from the list. Returns the object that was removed. The position of current will the the object before the removed item.
    * @return Object The object in the node that was removed, null if there is an error with that action
    */
    public E remove()
    {
        DoublyLinkedListNode<E> oldNode = current;
        current = (DoublyLinkedListNode<E>)current.back();
        current.setNext(oldNode.next());
        ((DoublyLinkedListNode<E>)current.next()).setBack(current);
        return oldNode.getObject();
    }

    /**
    * Removes the next node. If current is at the end of the list, then no objects are removed from the list. Returns the object that was removed. The position of current will remain the same
    * @return Object The object in the node that was removed, null if there is an error with that action
    */
    public E removeNext()
    {
        if(current.next() == tail)
        {
            return null;
        }
        DoublyLinkedListNode<E> oldNode = (DoublyLinkedListNode<E>)current.next();
        current.setNext(oldNode.next());
        ((DoublyLinkedListNode<E>)oldNode.next()).setBack(current);
        return oldNode.getObject();
    }

    /**
    * Removes the previous node. If current is at the end of the list, then no objects are removed from the list. Returns the object that was removed. The position of current will remain the same
    * @return Object The object in the node that was removed, null if there is an error with that action
    */
    public E removeBack()
    {
        if(current.back() == header)
        {
            return null;
        }
        DoublyLinkedListNode<E> oldNode = (DoublyLinkedListNode<E>)current.back();
        oldNode.back().setNext(current);
        current.setBack(oldNode.back());
        return oldNode.getObject();
    }

    /**
    * Finds the node with the object in it, then sets current to its position
    * @param e The object that needs to be found in the linkedlist. If it cannot be found, the position of current is untouched
    */
    public void findFirst(E e)
    {
        DoublyLinkedListNode<E> finder = header;
        while(finder.next() != tail)
        {
            finder = (DoublyLinkedListNode<E>)finder.next();
            E obj = finder.getObject();
            if(obj != null && obj.equals(e))
            {
                current = finder;
                break;
            }
        }
    }

    /**
    * Finds the node with the object in it, then sets current to its position
    * @param e The object that needs to be found in the linkedlist. If it cannot be found, the position of current is untouched
    */
    public void findLast(E e)
    {
        DoublyLinkedListNode<E> finder = tail;
        while(finder.back() != header)
        {
            finder = (DoublyLinkedListNode<E>)finder.back();
            E obj = finder.getObject();
            if(obj != null && obj.equals(e))
            {
                current = finder;
                break;
            }
        }
    }

    @Override
    public String toString()
    {
        //This returns the entire list like so:
        DoublyLinkedListNode<E> printer = (DoublyLinkedListNode<E>)header.next();
        if(printer == tail)
        {
            return "Empty List";
        }
        String str = "" + printer.getObject().toString();
        while(printer.next() != tail)
        {
            printer = (DoublyLinkedListNode<E>)printer.next();
            str += " <-> " + printer.getObject();
        }
        return str;
    }
}