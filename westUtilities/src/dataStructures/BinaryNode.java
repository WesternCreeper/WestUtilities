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
    public final static int COLOR_RED = 1;
    public final static int COLOR_BLACK = 0;
    private E object;
    private BinaryNode<E> left;
    private BinaryNode<E> right;
    private int color = -1;
    private int level = 0;
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

    /**
    * Sets the current color
    */
    public void setColor(int newColor)
    {
        color = newColor;
    }

    /**
    * Returns the current color
    * @return INTEGER - The current color
    */
    public int getColor()
    {
        return color;
    }

    public void setLevel(int newLevel)
    {
        level = newLevel;
    }

    public int getLevel()
    {
        return level;
    }
   
    /**
    * Finds out if the binary node has any children (1 or 2)
    */
    public boolean hasChildren()
    {
        return right != null || left != null;
    }

    /**
    * Removes the child given, if the given node is not its child then nothing will happen
    */
    public void removeChild(BinaryNode<E> child)
    {
        if(left.equals(child))
        {
           left = null;
        }
        else if(right.equals(child))
        {
           right = null;
        }
    }
    /**
    * This will ONLY return a non-null value when there is one child to this node
    */
    public BinaryNode<E> getChild()
    {
        if(left == null && right != null)
        {
           return right;
        }
        else if(left != null && right == null)
        {
           return left;
        }
        else
        {
           return null;
        }
    }

    /**
    * Replaces the child given, with the second node given. If the first node given is not a child of this node, then nothing will happen
    */
    public void replaceChild(BinaryNode<E> child, BinaryNode<E> node)
    {
        if(left.equals(child))
        {
           left = node;
        }
        else if(right.equals(child))
        {
           right = node;
        }
    }

    public int getHeight()
    {
        return getHeight(this);
    }
    private int getHeight(BinaryNode<E> node)
    {
        if(!node.hasChildren())
        {
           return 0;
        }
        int leftHeight = 0;
        if(node.getLeft() != null)
        {
           leftHeight = getHeight(node.getLeft());
        }
        int rightHeight = 0;
        if(node.getRight() != null)
        {
           rightHeight = getHeight(node.getRight());
        }

        if(rightHeight > leftHeight)
        {
           return rightHeight + 1;
        }
        else
        {
           return leftHeight + 1;
        }
    }
}
