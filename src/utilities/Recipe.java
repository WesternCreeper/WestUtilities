/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

/**
 *
 * A simple class that holds some items and says what item comes out of that
 * Item class in, Item class out.
 */
public class Recipe 
{
    private Item[] reactants = null;
    private Item[] products = null;
    private int id = 0;
    public Recipe(Item[] rea, Item[] pro)
    {
        reactants = rea;
        products = pro;
    }
    public Recipe(Item[] rea, Item[] pro, int id)
    {
        reactants = rea;
        products = pro;
        this.id = id;
    }
    /**
     * Gets the needed items for making a product
     * @return 
     */
    public Item[] getReactants()
    {
        return reactants;
    }
    /**
     * Gets the product that forms when the reactants are used
     * @return 
     */
    public Item[] getProducts()
    {
        return products;
    }
    public int getID()
    {
        return id;
    }
}
