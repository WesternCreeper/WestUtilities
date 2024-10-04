package dataStructures;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Westley
 * 
 * A standard node for a linked list. This only goes forwards. Not backwards
 */
class LinkedListNode<E>
{
    private LinkedListNode<E> next;
    private E nodeObj;

    public LinkedListNode(LinkedListNode<E> next)
    {
        this.next = next;
    }
    public LinkedListNode(E obj)
    {
        nodeObj = obj;
    }
    public LinkedListNode(E obj, LinkedListNode<E> next)
    {
        this.next = next;
        nodeObj = obj;
    }

    public LinkedListNode<E> next()
    {
        return next;
    }
    public void setNext(LinkedListNode<E> next)
    {
        this.next = next;
    }
    public E getObject()
    {
        return nodeObj;
    }
    @Override
    public String toString()
    {
        return nodeObj.toString();
    }
}