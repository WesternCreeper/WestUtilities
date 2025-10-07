/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import java.util.ArrayList;

/**
 *
 * @author Westley
 */
public class FileSet 
{
    private ArrayList<String> sections = new ArrayList<String>(0);
    private ArrayList<String> keys = new ArrayList<String>(0);
    private ArrayList<String> values = new ArrayList<String>(0);
    private ArrayList<Integer> keysStart = new ArrayList<Integer>(0);
    
    public FileSet(){}
    /**
     * Adds to the section ArrayList
     * @param str The String
     */
    public void addSection(String str)
    {
        sections.add(str);
        keysStart.add(keys.size());
    }
    
    /**
     * Adds to the key ArrayList
     * @param str The String
     */
    public void addKey(String str)
    {
        keys.add(str);
    }
    
    /**
     * Adds to the value ArrayList
     * @param str The String
     */
    public void addValue(String str)
    {
        values.add(str);
    }
    
    /**
     * Gets the section name from the index supplied
     * @param index The index of the section
     * @return STRING, will return "index out of bounds!" if the index is not within bounds
     */
    public String getSection(int index)
    {
        if(index < 0 || index > sections.size())
        {
            return "INDEX OUT OF BOUNDS!";
        }
        return sections.get(index);
    }
    
    /**
     * Gets the key's name from the index supplied
     * @param index The index of the key
     * @return STRING, will return "index out of bounds!" if the index is not within bounds
     */
    public String getKey(int index)
    {
        if(index < 0 || index > keys.size())
        {
            return "INDEX OUT OF BOUNDS!";
        }
        return keys.get(index);
    }
    
    /**
     * Gets the value's name from the index supplied
     * @param index The index of the value
     * @return STRING, will return "index out of bounds!" if the index is not within bounds
     */
    public String getValue(int index)
    {
        if(index < 0 || index > values.size())
        {
            return "INDEX OUT OF BOUNDS!";
        }
        return values.get(index);
    }
    
    /**
     * Gets the index of where the keys and values start for that section
     * @param index The index of the section
     * @return INTEGER
     */
    public int getKeyStart(int index)
    {
        if(index < 0)
        {
            return 0;
        }
        else if(index >= keysStart.size())
        {
            return keys.size();
        }
        return keysStart.get(index);
    }
    
    /**
     * Gets the size of the sections ArrayList
     * @return INTEGER
     */
    public int getSectionSize()
    {
        return sections.size();
    }
    
    /**
     * Gets the size of the keys ArrayList
     * @return INTEGER
     */
    public int getKeySize()
    {
        return keys.size();
    }
    
    /**
     * Gets the size of the values ArrayList
     * @return INTEGER
     */
    public int getValueSize()
    {
        return values.size();
    }
}
