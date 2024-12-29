/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataStructures;

/**
 *
 * @author Westley
 * This is a tree that has a maximum of two children nodes per parent. This means that the tree has a right and left side to every node
 * @param <E> The class that is represented in this data structure
 */
public class BinaryTree <E>
{
    private BinaryNode<E> root;
    private BinaryNode<E> current;

    public BinaryTree() {}
    public BinaryTree(E rootObject)
    {
        root = new BinaryNode<E>(rootObject);
        current = root;
    }

    public boolean isEmpty()
    {
        return root == null;
    }
    public void makeEmpty()
    {
        root = null;
        current = null;
    }

    /**
    * This "re-roots" the tree when called. Don't use unless there is no root, I.e. tree is empty. Or if you want to create a new tree and are fine with all of the data being replaced
    */
    public void addRoot(E rootObject)
    {
        root = new BinaryNode<E>(rootObject);
        current = root;
    }

    public void front()
    {
        current = root;
    }
    /**
    * This will adance the current position to the left node from the current position. This will never result in the current becoming null
    */
    public void advanceLeft()
    {
        BinaryNode<E> leftNode = current.getLeft();
        if(leftNode != null)
        {
            current = leftNode;
        }
    }
    /**
    * This will adance the current position to the right node from the current position. This will never result in the current becoming null
    */
    public void advanceRight()
    {
        BinaryNode<E> rightNode = current.getRight();
        if(rightNode != null)
        {
            current = rightNode;
        }
    }
    /**
    * This will remove node that is the left of the current position. However, this will also remove any children nodes under that node.
    */
    public void removeLeft()
    {
        current.setLeft(null);
    }
    
    /**
    * This will remove node that is the right of the current position. However, this will also remove any children nodes under that node.
    */
    public void removeRight()
    {
        current.setRight(null);
    }
    
    public E getCurrent()
    {
        return current.getObject();
    }
    
    /**
    * Inserts an object to the left of the current position. This will replace the object in the left side if there is one.
    */
    public void insertLeft(E leftObject)
    {
        current.setLeft(new BinaryNode<E>(leftObject));
    }
    /**
    * Inserts an object to the right of the current position. This will replace the object in the right side if there is one.
    */
    public void insertRight(E rightObject)
    {
        current.setRight(new BinaryNode<E>(rightObject));
    }

    //The print orders:
    public void printPreOrder()
    {
        printPreOrder(root);
    }
    private void printPreOrder(BinaryNode<E> node)
    {
        System.out.println(node.getObject());
        if(node.getLeft() != null)
        {
            printPreOrder(node.getLeft());
        }
        if(node.getRight() != null)
        {
            printPreOrder(node.getRight());
        }
    }
    
    public void printInOrder()
    {
        printInOrder(root);
    }
    private void printInOrder(BinaryNode<E> node)
    {
        if(node.getLeft() != null)
        {
            printInOrder(node.getLeft());
        }
        System.out.println(node.getObject());
        if(node.getRight() != null)
        {
            printInOrder(node.getRight());
        }
    }
    
    public void printPostOrder()
    {
        printPostOrder(root);
    }
    private void printPostOrder(BinaryNode<E> node)
    {
        if(node.getLeft() != null)
        {
            printPostOrder(node.getLeft());
        }
        if(node.getRight() != null)
        {
            printPostOrder(node.getRight());
        }
        System.out.println(node.getObject());
    }
}