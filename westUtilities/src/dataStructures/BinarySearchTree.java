/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataStructures;

/**
 *
 * @author Westley
 */
public class BinarySearchTree<E extends Comparable<E>>
{
    protected BinaryNode<E> root;
    /**
    * Creates a basic binary search tree with no root
    */
    public BinarySearchTree() {}
    /**
    * Creates a basic binary search tree, where the root is specified
    */
    public BinarySearchTree(E rootObject)
    {
        root = new BinaryNode<E>(rootObject);
    }

    public boolean isEmpty()
    {
        return root == null;
    }

    public BinaryNode<E> getRoot()
    {
        return root;
    }
    
    public void makeEmpty()
    {
        root = null;
    }

    /**
    * Inserts the object at the proper location. In order for this operation to work the object of type E must be an instance of the Camparable interface.
    */
    public void insert(E object)
    {
        if(isEmpty()) //First make sure there is a root to add to:
        {
            root = new BinaryNode<E>(object);
            return;
        }
        else //Now start comparing until we find the location of where this goes:
        {
            BinaryNode<E> finder = root;
            while(finder.hasChildren())
            {
                E nodeObject = finder.getObject();
                //Compare to the object in finder to determine if we go left or right:
                if(object.compareTo(nodeObject) > 0)
                {
                    if(finder.getRight() == null)
                    {
                        finder.setRight(new BinaryNode<E>(object));
                    }
                    else
                    {
                        finder = finder.getRight();
                    }
                }
                else if(object.compareTo(nodeObject) < 0)
                {
                    if(finder.getLeft() == null)
                    {
                        finder.setLeft(new BinaryNode<E>(object));
                    }
                    else
                    {
                        finder = finder.getLeft();
                    }
                }
            }

            //Now if there are no children then check from the current node:
            if(object.compareTo(finder.getObject()) > 0)
            {
                if(finder.getRight() == null)
                {
                    finder.setRight(new BinaryNode<E>(object));
                }
            }
            else if(object.compareTo(finder.getObject()) < 0)
            {
                if(finder.getLeft() == null)
                {
                    finder.setLeft(new BinaryNode<E>(object));
                }
            }
        }
    }

    /**
    * Determines if the object is contained within the tree.
    */
    public Boolean contains(E object)
    {
        if(isEmpty()) //First makes sure there is a tree to search through
        {
            return false;
        }
        else //Now start searching
        {
            BinaryNode<E> finder = root;
            while(finder.hasChildren())
            {
                E nodeObject = finder.getObject();
                //Compare to the object in finder to determine if we go left or right:
                if(object.compareTo(nodeObject) > 0)
                {
                    if(finder.getRight() == null)
                    {
                        return false;
                    }
                    else
                    {
                        finder = finder.getRight();
                    }
                }
                else if(object.compareTo(nodeObject) < 0)
                {
                    if(finder.getLeft() == null)
                    {
                        return false;
                    }
                    else
                    {
                        finder = finder.getLeft();
                    }
                }
                else
                {
                    return true;
                }
            }
            if(finder.getObject().equals(object))
            {
                return true;
            }
        }

        //If this function somehow managed to get here, then definitely return false:
        return false;
    }

    /**
    * Finds the minimum value contained in this tree
    */
    public E findMin()
    {
        return findMinNode(root).getObject();
    }
    private BinaryNode<E> findMinNode(BinaryNode<E> node)
    {
        if(node == null) //First makes sure there is a tree to search through
        {
            return null;
        }
        else //Now start searching
        {
            BinaryNode<E> finder = node;
            while(finder.getLeft() != null)
            {
                finder = finder.getLeft();
            }
            return finder;
        }
    }

    /**
    * Finds the maximum value contained in this tree
    */
    public E findMax()
    {
        if(isEmpty()) //First makes sure there is a tree to search through
        {
            return null;
        }
        else //Now start searching
        {
            BinaryNode<E> finder = root;
            while(finder.hasChildren())
            {
                finder = finder.getRight();
            }
            return finder.getObject();
        }
    }



    /**
    * Removes the given object from the tree
    */
    public void remove(E object)
    {
        if(isEmpty()) //First makes sure there is a tree to search through
        {
            return;
        }
        else //Now start searching
        {
            //First make sure the node they are looking for is not the root:
            if(root.getObject() == object)
            {
                //That's problematic, we'll need to fix this:
                if(root.getLeft() == null || root.getRight() == null) //has one child
                {
                    //Then just set the removed node to the child that it has:
                    root = root.getChild(); 
                }
                else //Has two children. Therefore we will need to find the minimum of this sub-tree:
                {
                    BinaryNode<E> min = findMinNode(root.getRight());
                    remove(min.getObject()); //Then remove the minimum node
                    //Now update the children:
                    root.setObject(min.getObject());
                }
            }
            remove(object, root, root.getLeft());
            remove(object, root, root.getRight());
        }
    }
    private void remove(E object, BinaryNode<E> parent, BinaryNode<E> remover)
    {
        if(remover == null) //So there is no more to the tree? Then don't keep looking down this path
        {
            return;
        }
        if(!remover.getObject().equals(object)) //Not the object? Then keep searching
        {
            remove(object, remover, remover.getLeft());
            remove(object, remover, remover.getRight());
        }
        else //We found the object? Remove it based on its children
        {
            if(!remover.hasChildren()) //No children? Good, no messy inheritance
            {
                parent.removeChild(remover);
            }
            else //Now we have to determine if this node has one child or two:
            {
                if(remover.getLeft() == null || remover.getRight() == null) //has one child
                {
                    //Then just set the removed node to the child that it has:
                    parent.replaceChild(remover, remover.getChild());
                }
                else //Has two children. Therefore we will need to find the minimum of this sub-tree:
                {
                    BinaryNode<E> min = findMinNode(remover.getRight());
                    remove(min.getObject()); //Then remove the minimum node
                    remover.setObject(min.getObject());
                }
            }
        }
    }

    //Print orders:
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
    public int getHeight()
    {
        return root.getHeight();
    }
}
