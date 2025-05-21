/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataStructures;

import graphicsUtilities.WGDrawingObject;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Westley
 */
public class WGObjectBoundNode 
{
    private WGObjectBoundNode next;
    private WGObjectBoundNode child;
    private WGDrawingObject object;
    
    public WGObjectBoundNode(WGDrawingObject obj)
    {
        object = obj;
    }
    
    //Methods:
    public boolean hasNext()
    {
        return next != null;
    }
    
    public boolean hasChild()
    {
        return child != null;
    }
    
    public boolean equals(WGObjectBoundNode node)
    {
        return this.getBounds().equals(node.getBounds());
    }
    

    //Getters:
    public WGObjectBoundNode getNext() {
        return next;
    }

    public WGObjectBoundNode getChild() {
        return child;
    }

    public Rectangle2D.Double getBounds() {
        return object.getBounds();
    }

    public Rectangle2D.Double getRelativeBounds() {
        return object.getRelativeBounds();
    }

    public WGDrawingObject getObject() {
        return object;
    }
    
    
    //Setters:
    public void setNext(WGObjectBoundNode next) {
        this.next = next;
    }

    public void setChild(WGObjectBoundNode child) {
        this.child = child;
    }
}
