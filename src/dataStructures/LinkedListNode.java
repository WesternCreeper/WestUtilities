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
class LinkedListNode
{
    private LinkedListNode next;
    private Object nodeObject;

    public LinkedListNode(LinkedListNode next)
    {
        this.next = next;
    }
    public LinkedListNode(Object object)
    {
        nodeObject = object;
    }
    public LinkedListNode(Object object, LinkedListNode next)
    {
        this.next = next;
        nodeObject = object;
    }

    //Methods:
    public LinkedListNode next()
    {
        return next;
    }
    public void setNext(LinkedListNode next)
    {
        this.next = next;
    }
    public Object getObject()
    {
        return nodeObject;
    }
    @Override
    public String toString()
    {
        return nodeObject.toString();
    }
}
