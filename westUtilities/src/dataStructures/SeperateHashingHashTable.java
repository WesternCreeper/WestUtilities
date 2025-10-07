/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataStructures;

/**
 *
 * @author Westley
 * This is a very specific type of hash tables that works better with higher load factors, (I.e less unused space)
*/
public class SeperateHashingHashTable<E>
{
    private final static int CHARACTER_SIZE = 127;
    private final static int DEFAULT_MAXIMUM_STRING_LENGTH = 8;

    private final int numericSpacing;
    private int numberOfFilledIndexes = 0;
    private int tableSize;
    private LinkedList<HashObject>[] allObjects;

    public SeperateHashingHashTable(int size)
    {
        numericSpacing = (int)Math.ceil((double)size / (CHARACTER_SIZE * DEFAULT_MAXIMUM_STRING_LENGTH));
        allObjects = new LinkedList[size];
        tableSize = size;
    }
    public SeperateHashingHashTable(int size, int maximumStringLength)
    {
        numericSpacing = (int)Math.ceil((double)size / (CHARACTER_SIZE * maximumStringLength));
        allObjects = new LinkedList[size];
        tableSize = size;
    }

    private int getHashIndex(String key)
    {
        int hashIndex = 0;
        for(int i = 0 ; i < key.length() ; i++)
        {
            hashIndex = hashIndex * numericSpacing + key.charAt(i);
        }
        hashIndex %= tableSize;
        if(hashIndex < 0)
        {
            hashIndex *= -1; //This will force the negative number to become positive
        }
        return hashIndex;
    }

    public void insert(String key, E value)
    {
        //Don't add null keys! That would ruin the table
        if(key == null)
        {
            return;
        }

        int hashIndex = getHashIndex(key);
        //Now find out if that hash index has already been filled with a linkedList, or a new one needs to be created:
        if(allObjects[hashIndex] == null)
        {
            allObjects[hashIndex] = new LinkedList<HashObject>();
        }

        //Then just add it into the list!
        allObjects[hashIndex].add(new HashObject(key, value));
        numberOfFilledIndexes++;
    }

    public E find(String key)
    {
        int hashIndex = getHashIndex(key);
        if(allObjects[hashIndex] == null)
        {
            return null;
        }
        //So there is a list there so search it for the correct index:
        allObjects[hashIndex].front();
        allObjects[hashIndex].advance();
        HashObject currentObject = allObjects[hashIndex].getCurrent();
        while(allObjects[hashIndex].hasNext() && currentObject != null)
        {
            if(currentObject.getKey().equals(key))
            {
                return currentObject.getObject();
            }
            allObjects[hashIndex].advance();
            currentObject = allObjects[hashIndex].getCurrent();
        }
        if(currentObject != null && currentObject.getKey().equals(key))
        {
            return currentObject.getObject();
        }
        return null;
    }

    public void set(String key, E newValue)
    {
        int hashIndex = getHashIndex(key);
        if(allObjects[hashIndex] == null)
        {
            return;
        }
        //So there is a list there so search it for the correct index:
        allObjects[hashIndex].front();
        allObjects[hashIndex].advance();
        HashObject currentObject = allObjects[hashIndex].getCurrent();
        while(allObjects[hashIndex].hasNext() && currentObject != null)
        {
            if(currentObject.getKey().equals(key))
            {
                //Now set the object to the new value, and break;
                currentObject.setObject(newValue);
                break;
            }
            allObjects[hashIndex].advance();
            currentObject = allObjects[hashIndex].getCurrent();
        }
        if(currentObject != null && currentObject.getKey().equals(key))
        {
            //Now set the object to the new value, and break;
            currentObject.setObject(newValue);
        }
    }

    public double getLoadFactor()
    {
        return (double)numberOfFilledIndexes/tableSize;
    }

    private class HashObject
    {
        private E object;
        private String key;
        HashObject(String key, E object)
        {
            this.key = key;
            this.object = object;
        }
        public String getKey()
        {
            return key;
        }
        public E getObject()
        {
            return object;
        }
        public void setObject(E newObject)
        {
            object = newObject;
        }
    }
}
