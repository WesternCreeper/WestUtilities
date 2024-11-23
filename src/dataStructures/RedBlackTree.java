/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataStructures;

/**
 *
 * @author Westley
 */
public class RedBlackTree<E extends Comparable<E>> extends AVLTree<E>
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
        if(node.getRight() != null && node.getRight().getColor() == BinaryNode.COLOR_BLACK)
        {
            node.setColor(BinaryNode.COLOR_RED);
            k1.setColor(BinaryNode.COLOR_BLACK);
        }
        else if(k1.getLeft() != null)
        {
            k1.getLeft().setColor(BinaryNode.COLOR_RED);
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
        if(node.getLeft() != null && node.getLeft().getColor() == BinaryNode.COLOR_BLACK)
        {
            node.setColor(BinaryNode.COLOR_RED);
            k1.setColor(BinaryNode.COLOR_BLACK);
        }
        else if(k1.getRight() != null)
        {
            k1.getRight().setColor(BinaryNode.COLOR_RED);
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
        k3.setColor(BinaryNode.COLOR_RED);
        k2.setColor(BinaryNode.COLOR_BLACK);
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
        k3.setColor(BinaryNode.COLOR_RED);
        k2.setColor(BinaryNode.COLOR_BLACK);
        return k2;
    }

    @Override
    protected void verifyBalanceTopDown()
    {
        verifyBalanceTopDown(root, null, null, null);
    }
    
    protected void verifyBalanceTopDown(BinaryNode<E> node, BinaryNode<E> parent, BinaryNode<E> grand, BinaryNode<E> great)
    {
        if(node == null)
        {
            return;
        }

        
        BinaryNode<E> left = node.getLeft();
        BinaryNode<E> right = node.getRight();
        if(node != root && left != null && left.getColor() == BinaryNode.COLOR_RED && right != null && right.getColor() == BinaryNode.COLOR_RED)
        {
            //Start by flipping the color:
            node.setColor(BinaryNode.COLOR_RED);
            left.setColor(BinaryNode.COLOR_BLACK);
            right.setColor(BinaryNode.COLOR_BLACK);
        }
        //Now we may have introduced some problems so start cracking to find out what they are:
        if(parent != null && grand != null && parent.getColor() == BinaryNode.COLOR_RED)
        {
            int leftHeight = getHeight(grand.getLeft());
            int rightHeight = getHeight(grand.getRight());
            if(Math.abs(leftHeight - rightHeight) > 1) //If this breaks the predefined rule:
            {
                //Do the needed flips:
                //Find out if it is left or right along the tree:
                if(rightHeight > leftHeight)
                {
                    //Now find out if it is the right right or the right left
                    BinaryNode<E> newNode = grand.getRight();
                    leftHeight = getHeight(newNode.getLeft());
                    rightHeight = getHeight(newNode.getRight());
                    //Find out if we need a single or double flip:
                    if(rightHeight > leftHeight)
                    {
                        replaceChild(great, grand, singleRotateRight(grand));
                    }
                    else
                    {
                        //We need a double flip!
                        replaceChild(great, grand, doubleRotateRight(grand));
                    }
                }
                else
                {
                    //Now find out if it is the left left or the left right
                    BinaryNode<E> newNode = grand.getLeft();
                    leftHeight = getHeight(newNode.getLeft());
                    rightHeight = getHeight(newNode.getRight());
                    //Find out if we need a single or double flip:
                    if(leftHeight > rightHeight)
                    {
                        replaceChild(great, grand, singleRotateLeft(grand));
                    }
                    else
                    {
                        //We need a double flip!
                        replaceChild(great, grand, doubleRotateLeft(grand));
                    }
                }
                
                root.setColor(BinaryNode.COLOR_BLACK);
                //If we got in here then we did something so we have to start all over again:
                verifyBalanceTopDown();
                return;
            }

        }
        else if(parent != null && node.getColor() == BinaryNode.COLOR_RED)
        {
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

                root.setColor(BinaryNode.COLOR_BLACK);
                //If we got in here then we did something so we have to start all over again:
                verifyBalanceTopDown();
                return;
            }
        }
        if(parent != null && node.getColor() == BinaryNode.COLOR_RED && parent.getColor() == BinaryNode.COLOR_RED)
        {
            node.setColor(BinaryNode.COLOR_BLACK);
        }
        verifyBalanceTopDown(node.getLeft(), node, parent, great);
        verifyBalanceTopDown(node.getRight(), node, parent, great);
    }

    private void verifyProperColors()
    {
        verifyProperColors(root, 0, 0);
    }
    private void verifyProperColors(BinaryNode<E> node, int depth, int numBlackNodes)
    {
        if(node == null)
        {
            return;
        }
        if(node.getColor() == BinaryNode.COLOR_BLACK)
        {
            numBlackNodes++;
        }
        if(node.getLeft() == null && node.getRight() == null) //Only Change the colors of leaves
        {
            if(numBlackNodes > depth) //There are too many black nodes!
            {
                node.setColor(BinaryNode.COLOR_RED);
            }
            else if(depth > numBlackNodes) //There are not enough black nodes!
            {
                node.setColor(BinaryNode.COLOR_BLACK);
            }
        }
        verifyProperColors(node.getLeft(), depth+1, numBlackNodes);
        verifyProperColors(node.getRight(), depth+1, numBlackNodes);
    }


    /**
    * Inserts the object at the proper location. In order for this operation to work the object of type E must be an instance of the Camparable interface.
    */
    @Override
    public void insert(E object)
    {
        if(isEmpty()) //First make sure there is a root to add to:
        {
            root = new BinaryNode<E>(object);
            root.setColor(BinaryNode.COLOR_BLACK);
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
                        finder.getRight().setColor(BinaryNode.COLOR_RED);
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
                        finder.getLeft().setColor(BinaryNode.COLOR_RED);
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
                    finder.getRight().setColor(BinaryNode.COLOR_RED);
                }
            }
            else if(object.compareTo(finder.getObject()) < 0)
            {
                if(finder.getLeft() == null)
                {
                    finder.setLeft(new BinaryNode<E>(object));
                    finder.getLeft().setColor(BinaryNode.COLOR_RED);
                }
            }
            verifyBalanceTopDown();
            verifyProperColors();
        }
    }
}
