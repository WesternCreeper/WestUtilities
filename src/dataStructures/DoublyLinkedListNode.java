/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataStructures;

/**
 *
 * @author Westley
 * 
 * A linked list node that can go forwards and backwards
 */
class DoublyLinkedListNode<E> extends LinkedListNode<E>
{
    private LinkedListNode<E> back;

    public DoublyLinkedListNode(LinkedListNode<E> next, LinkedListNode<E> back)
    {
        super(next);
        this.back = back;
    }
    public DoublyLinkedListNode(E object)
    {
        super(object);
    }
    public DoublyLinkedListNode(E object, LinkedListNode<E> next, LinkedListNode<E> back)
    {
        super(object, next);
        this.back = back;
    }

    //Methods:
    public LinkedListNode<E> back()
    {
        return back;
    }
    public void setBack(LinkedListNode<E> back)
    {
        this.back = back;
    }
}
