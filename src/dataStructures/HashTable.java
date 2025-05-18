/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataStructures;

/**
 *
 * @author Westley
 * This is a standard hash table with a multitude of hashing options. When the hashtable is setup, the hashing option must be chosen to prevent conflicting keys and information.
*/
public class HashTable<E extends Object>
{
    public final static int HASHING_OPTION_LINEAR = 0;
    public final static int HASHING_OPTION_QUADRATIC = 1;
    public final static int HASHING_OPTION_SECOND_HASH_WITH_LINEAR = 2;

    private final static int CHARACTER_SIZE = 127;
    private final static int DEFAULT_MAXIMUM_STRING_LENGTH = 8;

    private final int hashingOption;
    private final int numericSpacing;
    private int numberOfFilledIndexes = 0;
    private int tableSize;
    protected Object[] allObjects;
    protected String[] allKeys;

    public HashTable(int size, int hashOption)
    {
        hashingOption = hashOption;
        numericSpacing = (int)Math.ceil((double)size / (CHARACTER_SIZE * DEFAULT_MAXIMUM_STRING_LENGTH));
        allObjects = new Object[size];
        allKeys = new String[size];
        tableSize = size;
    }
    public HashTable(int size, int hashOption, int maximumStringLength)
    {
        hashingOption = hashOption;
        numericSpacing = (int)Math.ceil((double)size / (CHARACTER_SIZE * maximumStringLength));
        allObjects = new Object[size];
        allKeys = new String[size];
        tableSize = size;
    }

    protected HashTable(int size, int hashOption, double numericSpacing)
    {
        hashingOption = hashOption;
        this.numericSpacing = (int)numericSpacing;
        allObjects = new Object[size];
        allKeys = new String[size];
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

    private int getSecondHashIndex(String key, int oldHashIndex)
    {
        int hashIndex = 0;
        for(int i = 0 ; i < key.length() ; i++)
        {
            hashIndex = hashIndex * oldHashIndex + key.charAt(i);
        }
        hashIndex %= tableSize;
        if(hashIndex < 0)
        {
            hashIndex *= -1; //This will force the negative number to become positive
        }
        return hashIndex;
    }

    private int fixHashIndex(int originalIndex, String key)
    {
        if(allKeys[originalIndex] == null)
        {
            return originalIndex;
        }
        else if(allKeys[originalIndex].equals(key))
        {
            return originalIndex;
        }
        switch(hashingOption)
        {
            case HASHING_OPTION_SECOND_HASH_WITH_LINEAR:
                //If it got here, then we will need to use the second hash:
                originalIndex = getSecondHashIndex(key, originalIndex);
                
                if(allKeys[originalIndex] == null)
                {
                    return originalIndex;
                }
                else if(allKeys[originalIndex].equals(key))
                {
                    return originalIndex;
                }
                
                int indexPlus = 0;
                while(indexPlus < tableSize)
                {
                    originalIndex++;
                    indexPlus++;
                    if(originalIndex >= tableSize)
                    {
                        originalIndex = 0;
                    }
                    if(allKeys[originalIndex] == null)
                    {
                        return originalIndex;
                    }
                    else if(allKeys[originalIndex].equals(key))
                    {
                        return originalIndex;
                    }
                }
                return -1;
            case HASHING_OPTION_QUADRATIC:
                indexPlus = 0;
                while(indexPlus < tableSize)
                {
                    indexPlus++;
                    int newIndex = originalIndex + (int)Math.pow(indexPlus, 2);
                    while(newIndex >= tableSize)
                    {
                        newIndex -= tableSize;
                    }
                    if(allKeys[newIndex] == null)
                    {
                        return newIndex;
                    }
                    else if(allKeys[newIndex].equals(key))
                    {
                        return newIndex;
                    }
                }
                return -1;
            default: //This is the defualt option and also linear probing:
                indexPlus = 0;
                while(indexPlus < tableSize)
                {
                    originalIndex++;
                    indexPlus++;
                    if(originalIndex >= tableSize)
                    {
                        originalIndex = 0;
                    }
                    if(allKeys[originalIndex] == null)
                    {
                        return originalIndex;
                    }
                    else if(allKeys[originalIndex].equals(key))
                    {
                        return originalIndex;
                    }
                }
                return -1;
        }
    }

    public void insert(String key, E value)
    {
        //Don't add null keys! That would ruin the table
        if(key == null)
        {
            return;
        }
        //Before anything make sure that the load factor is not such that the table is full
        if(getLoadFactor() >= 1.0 || (hashingOption == HASHING_OPTION_QUADRATIC && getLoadFactor() >= 0.5))
        {
            //The table is full! Now we start the long process of rehashing and increasing the size of the hashtable:
            HashTable<E> newHashTable = new HashTable<E>(tableSize * 2, hashingOption, numericSpacing);

            //Now run through the hashtable rehashing everything:
            for(int i = 0 ; i < allKeys.length ; i++)
            {
                newHashTable.insert(allKeys[i], (E)allObjects[i]);
            }
            //And set this hashTable to the new hash table:
            tableSize *= 2;
            allKeys = newHashTable.getAllKeys();
            allObjects = newHashTable.getAllObjects();
        }

        
        int hashIndex = getHashIndex(key);
        //Now do the probing to find the correct index:
        hashIndex = fixHashIndex(hashIndex, key);
        if(hashIndex == -1) //The hashTable is full! That's a huge problem
        {
            return;
        }
        else if(allKeys[hashIndex] == null)
        {
            //If we got to here then we have a proper index and can add:
            allKeys[hashIndex] = key;
            allObjects[hashIndex] = value;
            numberOfFilledIndexes++;
        }
        else
        {
            //This is the exact same key, so don't allow for it to overide the value associated with it:
            return;
        }
    }

    public E find(String key)
    {
        int hashIndex = getHashIndex(key);
        //Now do the probing to find the correct index:
        hashIndex = fixHashIndex(hashIndex, key);
        if(hashIndex == -1) //The hashTable is full and we could not find the value!
        {
            return null;
        }
        else if(allKeys[hashIndex] == null)
        {
            //We could not find the value:
            return null;
        }
        else
        {
            //We clearly found an index, and since it is not null it should be the index we want!
            return (E)allObjects[hashIndex];
        }
    }

    public void set(String key, E newValue)
    {
        int hashIndex = getHashIndex(key);
        //Now do the probing to find the correct index:
        hashIndex = fixHashIndex(hashIndex, key);
        if(hashIndex == -1) //The hashTable is full! That's a huge problem
        {
            return;
        }
        else if(allKeys[hashIndex] == null)
        {
            //If we got to here then we have a proper index and can add:
            return;
        }
        else
        {
            //We found the proper key so we change the value
            allObjects[hashIndex] = newValue;
        }
    }
    
    public int size()
    {
        return allKeys.length;
    }
    
    public Object get(int index)
    {
        if(index < 0 || index >= allObjects.length)
        {
            return null;
        }
        return allObjects[index];
    }
    
    public String getKey(int index)
    {
        if(index < 0 || index >= allObjects.length)
        {
            return null;
        }
        return allKeys[index];
    }

    public double getLoadFactor()
    {
        return (double)numberOfFilledIndexes/tableSize;
    }

    protected String[] getAllKeys()
    {
        return allKeys;
    }
    protected Object[] getAllObjects()
    {
        return allObjects;
    }
}
