/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataStructures;

/**
 *
 * @author Westley
 * A representation of a node in a binary tree. The only purpose of this distinction is in the naming convention and the way it is used
 * @param <E> The name of the class that is being passed in the data structure (such as String or Object)
 */
class BinaryNode<E>
{
    private E object;
    private BinaryNode<E> left;
    private BinaryNode<E> right;
    public BinaryNode() 
    {
        object = null;
    }
    public BinaryNode(E e)
    {
        object = e;
    }
    public BinaryNode(E e, BinaryNode<E> left, BinaryNode<E> right)
    {
        object = e;
        this.left = left;
        this.right = right;
    }

    /**
    * Returns the current object
    */
    public E getObject()
    {
       return object; 
    }

    /**
    * Sets the current object to e
    */
    public void setObject(E e)
    {
       object = e; 
    }

    /**
    * Returns the left node
    */
    public BinaryNode<E> getLeft()
    {
       return left; 
    }

    /**
    * Sets the left node to a new left node
    */
    public void setLeft(BinaryNode<E> newLeft)
    {
       left = newLeft; 
    }

    /**
    * Returns the right node
    */
    public BinaryNode<E> getRight()
    {
       return right; 
    }

    /**
    * Sets the right node to a new right node
    */
    public void setRight(BinaryNode<E> newRight)
    {
       right = newRight; 
    }
}
