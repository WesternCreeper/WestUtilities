/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
/**
 *
 * @author Westley
 */
public class Item 
{
    private String name;
    private String description;
    private int number;
    private int strength;
    private int type;
    private int subtype;
    private int usage;
    /**
     * The no parameter constructor
     */
    public Item()
    {
        this("", 0, 0, 0, 0);
    }
    /**
     * The one parameter constructor
     * @param item Another item to duplicate
     */
    public Item(Item item)
    {
        this(item.getName(), item.getNumber(), item.getStrength(), item.getType(), item.getSubType(), item.getUsage(), item.getDescription());
    }
    /**
     * The 5 parameter constructor
     * @param str The name of the item
     * @param num The number items
     * @param stren The strength of the item
     * @param ty The type of the item
     * @param subty The subtype of the item
     */
    public Item(String str, int num, int stren, int ty, int subty)
    {
        this(str, num, stren, ty, subty, 0, "");
    }
    /**
     * The 6 parameter constructor
     * @param str The name of the item
     * @param num The number items
     * @param stren The strength of the item
     * @param ty The type of the item
     * @param subty The subtype of the item
     * @param use The usage of the item
     * @param descr The description of the item
     */
    public Item(String str, int num, int stren, int ty, int subty, int use, String descr)
    {
        name = str;
        number = num;
        strength = stren;
        type = ty;
        subtype = subty;
        usage = use;
        description = descr;
    }
    /**
     * Gets the name
     * @return STRING
     */
    public String getName()
    {
        return name;
    }
    /**
     * Gets the number
     * @return INT
     */
    public int getNumber()
    {
        return number;
    }
    /**
     * Gets the strength
     * @return INT
     */
    public int getStrength()
    {
        return strength;
    }
    /**
     * Gets the type
     * @return INT
     */
    public int getType()
    {
        return type;
    }
    /**
     * Gets the subtype
     * @return INT
     */
    public int getSubType()
    {
        return subtype;
    }
    /**
     * Gets the usage
     * @return INT
     */
    public int getUsage()
    {
        return usage;
    }
    /**
     * Gets the description
     * @return STRING
     */
    public String getDescription()
    {
        return description;
    }
    /**
     * Sets the name
     * @param str
     */
    public void setName(String str)
    {
        name = str;
    }
    /**
     * Sets the number
     * @param num
     */
    public void setNumber(int num)
    {
        number = num;
    }
    /**
     * Sets the strength
     * @param num
     */
    public void setStrength(int num)
    {
        strength = num;
    }
    /**
     * Sets the type
     * @param num
     */
    public void setType(int num)
    {
        type = num;
    }
    /**
     * Sets the subtype
     * @param num
     */
    public void setSubType(int num)
    {
        subtype = num;
    }
    /**
     * Sets the subtype
     * @param num
     */
    public void setUsage(int num)
    {
        usage = num;
    }
    /**
     * Sets the description
     * @param str
     */
    public void setDescription(String str)
    {
        description = str;
    }
    public String toString()
    {
        return name + " " + number + " " + strength + " " + type + " " + subtype + " " + usage + " " + description;
    }
    
    /**
     * Uses the old "Stacking" system of items, none of that new tech stuff with the FileProcessor. (VariableNumber \n ItemName \n ItemNumber \n ItemStrength \n ItemType \n ItemSubType \n ItemUsage \n etc.)
     * @param file The file
     * @param start The Start of the file
     * @return A list of all of the items, properly put together
     * @throws FileNotFoundException If there is no file found
     */
    public static ArrayList<Item> getItemList(String file, int start) throws FileNotFoundException
    {
        ArrayList<Item> items = new ArrayList<Item>(0);
        File fl = new File(file);
        int i = 0;
        int itemSystem = 5; //Number of variables in each item for the overall file
        if(!fl.exists())
        {
            FileNotFoundException error = new FileNotFoundException("Item File Doesn't Exist!!!");
            throw(error);
        }
        Scanner fileIn = new Scanner(fl);
        while(i < start)
        {
            if(fileIn.hasNextLine())
            {
                fileIn.nextLine();
            }
            else
            {
                break;
            }
            i++;
        }
        if(fileIn.hasNextLine())
        {
            String str = fileIn.nextLine();
            itemSystem = FileProcessor.toInt(str);
        }
        while(fileIn.hasNextLine())
        {
            switch(itemSystem)
            {
                default:
                    items.add(new Item(fileIn.nextLine(), FileProcessor.toInt(fileIn.nextLine()), FileProcessor.toInt(fileIn.nextLine()), FileProcessor.toInt(fileIn.nextLine()), FileProcessor.toInt(fileIn.nextLine())));
                    break;
                case 6:
                    items.add(new Item(fileIn.nextLine(), FileProcessor.toInt(fileIn.nextLine()), FileProcessor.toInt(fileIn.nextLine()), FileProcessor.toInt(fileIn.nextLine()), FileProcessor.toInt(fileIn.nextLine()), FileProcessor.toInt(fileIn.nextLine()), ""));
                    break;
                case 7:
                    items.add(new Item(fileIn.nextLine(), FileProcessor.toInt(fileIn.nextLine()), FileProcessor.toInt(fileIn.nextLine()), FileProcessor.toInt(fileIn.nextLine()), FileProcessor.toInt(fileIn.nextLine()), FileProcessor.toInt(fileIn.nextLine()), fileIn.nextLine()));
                    break;
            }
            i++;
        }
        return items;
    }
    
    /**
     * Uses the new "FileProcessor" method system of items, all made possible by the FileProcessor. ([Id] \n Name = ItemName \n Description = ItemDesc \n etc.) Missing values are assumed to defaults, except Strings
     * @param file The item file
     * @return A list of all of the items, properly put together
     * @throws FileNotFoundException If there is no file found
     */
    public static ArrayList<Item> getItemList(String file) throws FileNotFoundException
    {
        ArrayList<Item> items = new ArrayList<Item>(0);
        
        File fl = new File(file);
        if(!fl.exists())
        {
            FileNotFoundException error = new FileNotFoundException("Item File Doesn't Exist!!!");
            throw(error);
        }
        
        FileProcessor itemFinder = new FileProcessor(file);
        int id = 0;
        while(itemFinder.sectionExists(id + ""))
        {   //String str, int num, int stren, int ty, int subty, int use, String descr
            String name = itemFinder.findValue(id + "", "Name", false);
            String description = itemFinder.findValue(id + "", "Description", false);
            int number = FileProcessor.toInt(itemFinder.findValue(id + "", "Number"), 0);
            int strength = FileProcessor.toInt(itemFinder.findValue(id + "", "Strength"), 0);
            int type = FileProcessor.toInt(itemFinder.findValue(id + "", "Type"), 0);
            int usage = FileProcessor.toInt(itemFinder.findValue(id + "", "Usage"), 0);
            items.add(new Item(name, number, strength, type, id, usage, description));
            id++;
        }
        
        return items;
    }
}
