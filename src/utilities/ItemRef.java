/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import java.util.*;
import java.io.*;
/**
 *
 * @author Westley
 */
/**
 * 
 * A class that creates items without knowing what they do. Only needs the names of the items and the strength.
 * Expects the program to deal with what "strength" is.
 */
public class ItemRef 
{
    ArrayList<String> items = new ArrayList<String>(1);
    ArrayList<Integer> strength = new ArrayList<Integer>(1);
    ArrayList<Integer> number = new ArrayList<Integer>(1); //Can be anything, but with items it is the number of items. Can be interpeted by your program
    ArrayList<Integer> type = new ArrayList<Integer>(1); //Can be anything, but with items it is the number of items. Can be interpeted by your program
    ArrayList<Integer> subType = new ArrayList<Integer>(1); //Can be anything, but with items it is the number of items. Can be interpeted by your program
    int start = 0;
    String file = "";
    public ItemRef()
    {
        
    }
    /**
     * Make sure all arrays are the same length!!!
     * @param its items
     * @param strs strength of said item
     * @param nums number of that said item - Handled by the program
     * @param tps the type of item - Handled by the program
     * @param stps the sub type of item - Handled by the program
     */
    public ItemRef(String[] its, int[] strs, int[] nums, int[] tps, int[] stps)
    {
        for(int i = 0 ; i < its.length ; i++)
        {
            items.add(its[i]);
            strength.add(strs[i]);
            number.add(nums[i]);
            type.add(tps[i]);
            subType.add(stps[i]);
        }
    }
    /**
     * Make sure all arrays are the same length!!!
     * @param fileName the name of the file
     * @param st the starting point in the file
     */
    public ItemRef(String fileName, int st)
    {
        file = fileName;
        start = st;
        imput();
    }
    /**
     * Reads a file and sets the varibles to it.
     */
    public void imput()
    {
        removeAll();
        File fl = new File(file);
        try
        {
            int i = 0;
            if(!fl.exists())
            {
                fl.createNewFile();
                return;
            }
            Scanner fileIn = new Scanner(fl);
            while(fileIn.hasNext())
            {
                if(i < start)
                {
                    fileIn.nextLine();
                }
                else
                {
                    items.add(fileIn.nextLine());
                    strength.add(Integer.valueOf(fileIn.nextLine()));
                    number.add(Integer.valueOf(fileIn.nextLine()));
                    type.add(Integer.valueOf(fileIn.nextLine()));
                    subType.add(Integer.valueOf(fileIn.nextLine()));
                }
                i++;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        //Check to add UniCode Characters to the strings
        for(int i = 0 ; i < items.size() ; i++)
        {
            for(int j = 0 ; j < items.get(i).length() -1 ; j++)
            {
                if(items.get(i).charAt(j) == '\\' && items.get(i).charAt(j+1) == 'U')
                {
                    items.set( i, ( items.get(i).substring(0, j) + Character.toString( Integer.valueOf( items.get(i).substring(j+2 ) ) ) ) ); //Sets the string to the new string with the unicode character instead of the /U#
                }
            }
        }
    }
    /**
     * Adds a single item to the list of items.
     * @param str the name
     * @param sng the strength
     * @param num the number of items - Handled by the program
     * @param tp the type of item - Handled by the program
     * @param stp the Sub Type - Handled by the program
     */
    public void addItem(String str, int sng, int num, int tp, int stp)
    {
        items.add(str);
        strength.add(sng);
        number.add(num);
        type.add(tp);
        subType.add(stp);
    }
    /**
     * Removes an item based on the index
     * @param index the index of the item
     */
    public void removeItem(int index)
    {
        items.remove(index);
        strength.remove(index);
        number.remove(index);
        type.remove(index);
        subType.remove(index);
    }
    /**
     * Removes an item from the list.
     * @param num the number of items spent
     * @param index the index of the item
     */
    public void spendItem(int num, int index)
    {
        if(index >= number.size())
        {
            return;
        }
        number.set(index, (number.get(index) - num));
        if(number.get(index) == 0)
        {
            items.remove(index);
            strength.remove(index);
            number.remove(index);
            type.remove(index);
            subType.remove(index);
        }
    }
    /**
     * Returns the Name of the item at the index
     * @param index the index of the item
     * @return String
     */
    public String getName(int index)
    {
        if(index >= items.size())
        {
            return "-1";
        }
        return items.get(index);
    }
    /**
     * Returns the Strength of the item at the index
     * @param index the index of the item
     * @return int
     */
    public int getStrength(int index)
    {        
        if(index >= strength.size())
        {
            return -1;
        }
        return strength.get(index);
    }
    /**
     * Returns the number of items at the index
     * @param index the index of the item
     * @return int
     */
    public int getNumber(int index)
    {        
        if(index >= number.size())
        {
            return -1;
        }
        return number.get(index);
    }
    /**
     * Returns the type of item at the said index
     * @param index the index of the item
     * @return int
     */
    public int getType(int index)
    {        
        if(index >= type.size())
        {
            return -1;
        }
        return type.get(index);
    }
        /**
     * Returns the sub type of item at the said index
     * @param index the index of the item
     * @return int
     */
    public int getSubType(int index)
    {        
        if(index >= subType.size())
        {
            return -1;
        }
        return subType.get(index);
    }
    /**
     * @deprecated 
     * Returns the size of the arrays
     * @return int
     */
    @Deprecated
    public int getSize()
    {        
        return items.size();
    }
    /**
     * Returns the size of the arrays
     * @return int
     */
    public int size()
    {        
        return items.size();
    }
    /**
     * Adds stock to the items
     * @param stock the added stock to the item
     * @param index the index of the item
     */
    public void addStock(int index, int stock)
    {        
        number.set(index, number.get(index) + stock);
    }
    public String toString()
    {        
        String str = "";
        for(int i = 0 ; i < type.size() ; i++)
        {
            str += "\nName: " + items.get(i) + " Strength: " + strength.get(i) + " Number: " + number.get(i) + " Type: " + type.get(i) + " Sub-Type: " + subType.get(i);
        }
        return str;
    }
    /**
     * Removes all of the items from the ItemRef
     */
    public void removeAll()
    {
        while(!number.isEmpty())
        {
            items.remove(0);
            strength.remove(0);
            number.remove(0);
            type.remove(0);
            subType.remove(0);
        }
    }
}
