/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataStructures;

import graphicsUtilities.WGButton;
import graphicsUtilities.WGDrawingObject;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.geometry.Rectangle2D;

/**
 *
 * @author Westley
 */
public class WGObjectBoundList 
{
    private WGObjectBoundNode header;
    
    public WGObjectBoundList(){}
    
    public void addNode(WGObjectBoundNode node)
    {
        if(header == null)
        {
            header =  node;
        }
        else
        {
            //Now we need to find where to place this:
            //Search based on the x-value:
            double x = node.getRelativeBounds().getMinX();
            double width = node.getRelativeBounds().getWidth();
            WGObjectBoundNode searchNode = header;
            WGObjectBoundNode prev = null;
            boolean isChild = false;
            
            //Find the next best location to go (This should automatically sort the x-values, from smallest to largest):
            while(searchNode != null && x > searchNode.getRelativeBounds().getMinX())
            {
                prev = searchNode;
                searchNode = searchNode.getNext();
            }
            
            //Now that we are at the right x-value, either because searchNode.next does not exist, or because we found an x that we are equal to:
            if(searchNode != null && x == searchNode.getRelativeBounds().getMinX())
            {
                //Now find the best location (largest to smallest widths):
                while(searchNode != null && width < searchNode.getRelativeBounds().getWidth())
                {
                    isChild = true;
                    prev = searchNode; //All child nodes do not have a prev
                    searchNode = searchNode.getChild();
                }
                
                //We finally have our location:
                if(prev != null)
                {
                    if(!isChild)
                    {
                        //We are the new biggest, so push everything down one:
                        prev.setNext(node);
                        node.setChild(searchNode);
                        node.setNext(searchNode.getNext());
                    }
                    else
                    {
                        //We just need to fit into our spot:
                        prev.setChild(node);
                        node.setChild(searchNode);
                    }
                }
                else
                {
                    //We just do what the other does, set ourself as the smallest x, but largest width:
                    node.setChild(searchNode);
                    node.setNext(searchNode.getNext());
                    searchNode.setNext(null);
                    header = node;
                }
            }
            else
            {
                if(prev != null)
                {
                    //Disrupt essentially:
                    prev.setNext(node);
                    node.setNext(searchNode);
                }
                else
                {
                    //We are the new smallest so:
                    node.setNext(searchNode);
                    header = node;
                }
            }
        }
    }
    
    public void removeNode(WGObjectBoundNode node)
    {
        if(header == null)
        {
            return;
        }
        else
        {
            //Now we need to find where we were placed
            //Search based on the x-value:
            double x = node.getRelativeBounds().getMinX();
            double width = node.getRelativeBounds().getWidth();
            WGObjectBoundNode searchNode = header;
            WGObjectBoundNode prev = null;
            boolean isChild = false;
            
            //Search by the x-values:
            while(searchNode != null && x > searchNode.getRelativeBounds().getMinX())
            {
                prev = searchNode;
                searchNode = searchNode.getNext();
            }
            
            //Now that we are at the right x-value, now find out where we went:
            if(searchNode != null && x == searchNode.getRelativeBounds().getMinX())
            {
                //Now find our exact location:
                while(searchNode != null && width <= searchNode.getRelativeBounds().getWidth())
                {
                    if(searchNode.equals(node)) //Just in case it shares it's width with another object
                    {
                        break;
                    }
                    isChild = true;
                    prev = searchNode; //All child nodes do not have a prev
                    searchNode = searchNode.getChild();
                }
                
                //We finally have our location:
                if(searchNode.equals(node))
                {
                    if(prev != null)
                    {
                        if(!isChild)
                        {
                            //So set ourself to our child:
                            if(searchNode.getChild() != null)
                            {
                                prev.setNext(searchNode.getChild());
                                searchNode.getChild().setNext(searchNode.getNext());
                            }
                            else
                            {
                                //We have no child, so just fill the hole:
                                prev.setNext(searchNode.getNext());
                            }
                        }
                        else
                        {
                            //We just need to fit into our spot:
                            prev.setChild(searchNode.getChild());
                        }
                    }
                    else
                    {
                        //We are the header, so fix that:
                        header.getChild().setNext(header.getNext());
                        header = header.getChild();
                    }
                }
                else
                {
                    //We are not part of this list
                    return;
                }
            }
            else
            {
                //We are not part of the group...
                return;
            }
        }
    }
    public WGDrawingObject contains(Point2D point, Canvas currentParent)
    {
        WGDrawingObject foundInstance = containsUseRelative(point, currentParent);
        if(foundInstance == null) //Double check that the real thing we are looking for has not scrolled away:
        {
            WGDrawingObject instanceABS = containsUseAbsolute(point, currentParent);
            if(instanceABS != null && instanceABS.getClickListener().getParentOwningPane() != null)
            {
                //This is in a pane and therefore must have been scrolled, use this instead of null:
                foundInstance = instanceABS;
            }
        }
        return foundInstance;
    }
    public WGDrawingObject containsUseRelative(Point2D point, Canvas currentParent)
    {
        if(header == null)
        {
            return null;
        }
        //Start searching by the x-value:
        double x = point.getX() / currentParent.getWidth();
        double y = point.getY() / currentParent.getHeight();
        WGObjectBoundNode searchNode = header;
        
        //Figure out where we go based on the x-value:
        while(x >= searchNode.getRelativeBounds().getMinX())
        {
            WGObjectBoundNode searchChild = searchNode;
            do
            {
                if(searchChild.getObject().isShown() && searchChild.getObject().getParent() == currentParent)
                {
                    //We know that we are larger than or equal to the current x, so search the widths of the serachNode until we find one that contains us:
                    Rectangle2D nodeBounds = searchChild.getRelativeBounds();
                    double nodeWidthX = nodeBounds.getWidth() + nodeBounds.getMinX();
                    if(x <= nodeWidthX) //We fit in the box, that means we are good, hopefully!
                    {
                        //Now check the y-coor:
                        if(y >= nodeBounds.getMinY() && y <= nodeBounds.getMinY() + nodeBounds.getHeight())
                        {
                            return searchChild.getObject();
                        }
                        searchChild = searchChild.getChild();
                    }
                    else
                    {
                        searchChild = null;
                    }
                }
                else
                {
                    searchChild = searchChild.getChild();
                }
            }
            while(searchChild != null);
            
            if(searchNode.hasNext())
            {
                searchNode = searchNode.getNext();
            }
            else
            {
                return null;
            }
        }
        
        //If we exited the loop then we know we did not find it:
        return null;
    }
    public WGDrawingObject containsUseAbsolute(Point2D point, Canvas currentParent)
    {
        if(header == null)
        {
            return null;
        }
        //Start searching by the x-value:
        double x = point.getX();
        double y = point.getY();
        WGObjectBoundNode searchNode = header;
        
        //Figure out where we go based on the x-value:
        while(x >= searchNode.getBounds().getMinX())
        {
            WGObjectBoundNode searchChild = searchNode;
            do
            {
                if(searchChild.getObject().isShown() && searchChild.getObject().getParent() == currentParent)
                {
                    //We know that we are larger than or equal to the current x, so search the widths of the serachNode until we find one that contains us:
                    Rectangle2D nodeBounds = searchChild.getBounds();
                    double nodeWidthX = nodeBounds.getWidth() + nodeBounds.getMinX();
                    if(x <= nodeWidthX) //We fit in the box, that means we are good, hopefully!
                    {
                        //Now check the y-coor:
                        if(y >= nodeBounds.getMinY() && y <= nodeBounds.getMinY() + nodeBounds.getHeight())
                        {
                            return searchChild.getObject();
                        }
                        searchChild = searchChild.getChild();
                    }
                    else
                    {
                        searchChild = null;
                    }
                }
                else
                {
                    searchChild = searchChild.getChild();
                }
            }
            while(searchChild != null);
            
            if(searchNode.hasNext())
            {
                searchNode = searchNode.getNext();
            }
            else
            {
                return null;
            }
        }
        
        //If we exited the loop then we know we did not find it:
        return null;
    }
}
