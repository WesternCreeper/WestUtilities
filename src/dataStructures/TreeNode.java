/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataStructures;

/**
 *
 * @author Westley
 * The represetnation of a node in a tree. This has an unlimited amount of possible child nodes.
 * This is implemented with references only, therefore traversal of the children is O(N), but adding to the tree is O(1)
 * @param <E> The class name that is represented as data in this structure
 */
class TreeNode<E>
{
    private E object;
    private DoublyLinkedList<TreeNode<E>> children;
    public TreeNode() 
    {
        object = null;
        children = new DoublyLinkedList<TreeNode<E>>();
    }
    public TreeNode(E e)
    {
        object = e;
        children = new DoublyLinkedList<TreeNode<E>>();
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
    public DoublyLinkedList<TreeNode<E>> getChildren()
    {
       return children; 
    }

    /**
    * Sets the left node to a new left node
    */
    public void addChild(TreeNode<E> newChild)
    {
       children.add(newChild); 
    }

   @Override
   public String toString()
   {
      return object.toString();
   }
}
