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
class DoublyLinkedListNode extends LinkedListNode
{
    private LinkedListNode back;
    
    public DoublyLinkedListNode(LinkedListNode next, LinkedListNode back)
    {
        super(next);
        this.back = back;
    }
    public DoublyLinkedListNode(Object object)
    {
        super(object);
    }
    public DoublyLinkedListNode(Object object, LinkedListNode next, LinkedListNode back)
    {
        super(object, next);
        this.back = back;
    }
    
    //Methods:
    public LinkedListNode back()
    {
        return back;
    }
    public void setBack(LinkedListNode back)
    {
        this.back = back;
    }
}
