/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataStructures;

/**
 *
 * @author Westley
 * This is a data structure in which there are parents and children in a tree-like fashion. This implementation, just like the binary tree, requires a root node to be set before being added to
 * @param <E> The class that is represented in this data structure
 */
public class Tree <E>
{
    private TreeNode<E> root;
    private TreeNode<E> current;

    public Tree() {}
    public Tree(E rootObject)
    {
        root = new TreeNode<E>(rootObject);
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
        root = new TreeNode<E>(rootObject);
        current = root;
    }

    public void front()
    {
        current = root;
    }
    /**
    * This will adance the current position to the closest indexed node from the current position. This will never result in the current becoming null
    * @param index This is the child you want to advance to. If there is no available child, then this function advances to the cloest posible child
    */
    public void advance(int index)
    {
        DoublyLinkedList<TreeNode<E>> children = current.getChildren();
        children.front();
        int i = 0;
        while(i < index)
        {
            children.advance();
            i++;
        }
        TreeNode<E> nextNode = (TreeNode<E>)children.getCurrent();
        if(nextNode != null)
        {
            current = nextNode;
        }
    }
    /**
    * This will adance the current position to the node of that name. This will never result in a null current
    * @param obj This is the child you want to advance to. This is the inside of that said object. If there is no node with that said object/name, then the current position will advance to the leftmost node.
    */
    public void advance(E obj)
    {
        DoublyLinkedList<TreeNode<E>> children = current.getChildren();
        children.front();
        while(children.canAdvance())
        {
            children.advance();
            if(obj.equals(children.getCurrent().getObject()))
            {
                break;
            }
        }
        TreeNode<E> nextNode = (TreeNode<E>)children.getCurrent();
        if(nextNode != null)
        {
            current = nextNode;
        }
    }
    /**
    * This will remove the current node
    */
    public void removeCurrent()
    {
        current = null;
    }

    public E getCurrent()
    {
        return current.getObject();
    }

    public DoublyLinkedList<TreeNode<E>> getCurrentChildren()
    {
        return current.getChildren();
    }

    /**
    * Adds an additional child to the current node. This will add to the end of the current list of children
    */
    public void addChild(E object)
    {
        current.addChild(new TreeNode<E>(object));
    }

    //The print orders:
    public void printPreOrder()
    {
        printPreOrder(root);
    }
    private void printPreOrder(TreeNode<E> node)
    {
        System.out.println(node.getObject());
        DoublyLinkedList<TreeNode<E>> children = node.getChildren();
        children.front();
        while(children.canAdvance())
        {
            children.advance();
            printPreOrder((TreeNode<E>)children.getCurrent());
        }
    }

    public void printPostOrder()
    {
        printPostOrder(root);
    }
    private void printPostOrder(TreeNode<E> node)
    {
        DoublyLinkedList<TreeNode<E>> children = node.getChildren();
        children.front();
        while(children.canAdvance())
        {
            children.advance();
            printPostOrder((TreeNode<E>)(children.getCurrent()));
        }
        System.out.println(node.getObject());
    }
}
