/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataStructures;

/**
 *
 * @author Westley
 */
public class AVLTree<E extends Comparable<E>> extends BinarySearchTree<E>
{

    protected BinaryNode<E> singleRotateLeft(BinaryNode<E> node)
    {
        BinaryNode<E> k1 = node.getLeft();
        node.setLeft(k1.getRight());
        k1.setRight(node);
        //If this was the root, then update this:
        if(node == root)
        {
            root = k1;
        }
        return k1;
    }
    protected BinaryNode<E> singleRotateRight(BinaryNode<E> node)
    {
        BinaryNode<E> k1 = node.getRight();
        node.setRight(k1.getLeft());
        k1.setLeft(node);
        //If this was the root, then update this:
        if(node == root)
        {
            root = k1;
        }
        return k1;
    }
    protected BinaryNode<E> doubleRotateLeft(BinaryNode<E> k3)
    {
        k3.setLeft(singleRotateRight(k3.getLeft()));
        BinaryNode<E> k2 = singleRotateLeft(k3);
        if(k3 == root)
        {
            root = k2;
        }
        return k2;
    }
    protected BinaryNode<E> doubleRotateRight(BinaryNode<E> k3)
    {
        k3.setRight(singleRotateLeft(k3.getRight()));
        BinaryNode<E> k2 = singleRotateRight(k3);
        if(k3 == root)
        {
            root = k2;
        }
        return k2;
    }

    protected void verifyBalanceTopDown()
    {
        verifyBalanceTopDown(root, null);
    }
    protected void verifyBalanceTopDown(BinaryNode<E> node, BinaryNode<E> parent)
    {
        if(node == null)
        {
            return;
        }
        
        int leftHeight = getHeight(node.getLeft());
        int rightHeight = getHeight(node.getRight());
        if(Math.abs(leftHeight - rightHeight) > 1) //If this breaks the predefined rule:
        {
            //Do the needed flips:
            //Find out if it is left or right along the tree:
            if(rightHeight > leftHeight)
            {
                //Now find out if it is the right right or the right left
                BinaryNode<E> newNode = node.getRight();
                leftHeight = getHeight(newNode.getLeft());
                rightHeight = getHeight(newNode.getRight());
                //Find out if we need a single or double flip:
                if(rightHeight > leftHeight)
                {
                    replaceChild(parent, node, singleRotateRight(node));
                }
                else
                {
                    //We need a double flip!
                    replaceChild(parent, node, doubleRotateRight(node));
                }
            }
            else
            {
                //Now find out if it is the left left or the left right
                BinaryNode<E> newNode = node.getLeft();
                leftHeight = getHeight(newNode.getLeft());
                rightHeight = getHeight(newNode.getRight());
                //Find out if we need a single or double flip:
                if(leftHeight > rightHeight)
                {
                    replaceChild(parent, node, singleRotateLeft(node));
                }
                else
                {
                    //We need a double flip!
                    replaceChild(parent, node, doubleRotateLeft(node));
                }
            }

            //If we got in here then we did something so we have to start all over again:
            verifyBalanceTopDown();
            return;
        }
        verifyBalanceTopDown(node.getLeft(), node);
        verifyBalanceTopDown(node.getRight(), node);
        
    }

    @Override
    public void insert(E object)
    {
        super.insert(object);
        verifyBalanceTopDown();
    }

    @Override
    public void remove(E object)
    {
        super.remove(object);
        verifyBalanceTopDown();
    }

    protected int getHeight(BinaryNode<E> node)
    {
        if(node == null)
        {
            return -1;
        }
        return node.getHeight();
    }

    protected void replaceChild(BinaryNode<E> parent, BinaryNode<E> node, BinaryNode<E> newNode)
    {
        if(parent == null)
        {
            return;
        }
       if(parent.getLeft() != null && parent.getLeft().equals(node))
       {
           parent.setLeft(newNode);
       }
       else if(parent.getRight() != null && parent.getRight().equals(node))
       {
           parent.setRight(newNode);
       }
    }
}
