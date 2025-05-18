/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import dataStructures.HashTable;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * This is a simple utility that uses basic compression and decompression algorithms to take a file and make it smaller
 * This uses the HashTable data structure!
 * @author Westley
 */
public class SimpleCompressionHelper 
{
    private File fileToUse;
    
    /**
     * Creates a basic compression helper for the file specified
     */
    public SimpleCompressionHelper(File fileToCompressOrDecompress)
    {
        fileToUse = fileToCompressOrDecompress;
    }
    
    /**
     * This compresses a file and gives it the new extension that was given
     * @param newExtension The extension that is used to differentiate the new file
     * @param delim The delimiter known to be used within the file
     * @param endSymbol The symbol to end the compression hashtable. This has to be a safe symbol (One not used by any other part of the code) in order for the compression/decompression to work
     * @exception IOException Throws exceptions based on what the individual parts of the algorithm give as errors. Shouldn't be a problem
     */
    public void compress(String newExtension, String delim, String endSymbol) throws IOException
    {
        HashTable allCompressedValues = new HashTable(1000, HashTable.HASHING_OPTION_LINEAR);
        HashTable seenExpressions = new HashTable(1000, HashTable.HASHING_OPTION_LINEAR);
        
        String oldName = fileToUse.getName();
        oldName = oldName.substring(0, oldName.indexOf("."));
        File newFile = new File(oldName + "." + newExtension);
        
        newFile.createNewFile();
        
        //Now start reading the original file and find patterns:
        Scanner sc = new Scanner(fileToUse);
        sc.useDelimiter(delim);
        while(sc.hasNext())
        {
            String str = sc.next();
            //If the expression we have seen is not within the array, then we add it:
            Object foundValue = seenExpressions.find(str);
            if(foundValue == null)
            {
                seenExpressions.insert(str, 1);
            }
            else //We just need to increase the value by one
            {
                seenExpressions.set(str, (Integer)foundValue + 1);
            }
        }
        sc.close();
        
        //After we have read the whole file and seen it all, now we can see if compression will be helpful for each of the values stored:
        int unicodeForSymbol = 161;
        for(int i = 0 ; i < seenExpressions.size() ; i++)
        {
            if(seenExpressions.getKey(i) == null)
            {
                continue;
            }
            String key = seenExpressions.getKey(i);
            int timesSeen = (Integer)seenExpressions.get(i);
            
            //Calculate the compression amounts
            int uncompressedAmount = timesSeen * key.length();
            String newSymbol = Character.toString(unicodeForSymbol);
            String extraInfo = ",";
            if(i+1 == seenExpressions.size())
            {
                extraInfo = "";
            }
            int compressedAmount = (key + "=" + newSymbol + extraInfo).length() + timesSeen;
            
            //Test to make sure the compression will be worth it:
            if(compressedAmount < uncompressedAmount)
            {
                //If so, then remember to add it to our array so we can go through compressing things:
                unicodeForSymbol++;
                allCompressedValues.insert(seenExpressions.getKey(i), newSymbol);
            }
        }
        
        
        //Time to write up the compressed file:
        BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));
        Scanner reader = new Scanner(fileToUse);
        //Write the hashtable to the array:
        boolean firstOutput = true;
        for(int i = 0 ; i < allCompressedValues.size() ; i++)
        {
            if(allCompressedValues.getKey(i) != null)
            {
                if(firstOutput)
                {
                    firstOutput = false;
                }
                else
                {
                    writer.write(",");
                }
                writer.write(allCompressedValues.getKey(i) + "=" + allCompressedValues.get(i));
            }
        }
        writer.write(endSymbol);
        
        //Now this time, read through and use our new hashtable to compress the file:
        reader.useDelimiter(delim);
        while(reader.hasNext())
        {
            String next = reader.next();
            String possibleCompressValue = (String)allCompressedValues.find(next);
            if(possibleCompressValue != null)
            {
                next = possibleCompressValue;
            }
            writer.write(next);
            if(reader.hasNext())
            {
                writer.write(delim); //Don't add extra data
            }
        }
        writer.close();
        reader.close();
    }
    
    /**
     * This compresses a file and gives it the new extension that was given
     * @param newExtension The extension that is used to differentiate the new file
     * @param delim The delimiter known to be used within the file
     * @param endSymbol The symbol to end the compression hashtable. This has to be a safe symbol (One not used by any other part of the code) in order for the compression/decompression to work
     * @exception IOException Throws exceptions based on what the individual parts of the algorithm give as errors. Shouldn't be a problem
     */
    public void decompress(String newExtension, String delim, String endSymbol) throws IOException
    {
        HashTable allCompressedValues = new HashTable(1000, HashTable.HASHING_OPTION_LINEAR);
        
        //Start by creating the decompressed file:
        String oldName = fileToUse.getName();
        oldName = oldName.substring(0, oldName.indexOf("."));
        File newFile = new File(oldName + "." + newExtension);
        
        newFile.createNewFile();
        
        //Read the hashtable first!
        Scanner sc = new Scanner(fileToUse);
        sc.useDelimiter(",");
        while(sc.hasNext())
        {
            String[] keyAndValue = sc.next().split("=");
            if(keyAndValue.length == 2)
            {
                String value = keyAndValue[0];
                String key = keyAndValue[1];
                
                if(key.contains(endSymbol))//This is the last of the hashtable
                {
                    allCompressedValues.insert(key.substring(0, key.indexOf(endSymbol)), value);
                    break;
                }
                else
                {
                    allCompressedValues.insert(key, value);
                }
            }
        }
        sc.close();
        //Now re-read the file after skipping the hash table: (This time decompressing it)
        BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));
        Scanner reader = new Scanner(fileToUse);
        reader.useDelimiter(endSymbol);
        reader.next();
        reader.useDelimiter(delim);
        boolean isFirst = true;
        while(reader.hasNext())
        {
            //Replace with the table of decompression as seeming to fit:
            String str = reader.next();
            if(isFirst)
            {
                isFirst = false;
                str = str.substring(str.indexOf(endSymbol)+1);
            }
            String possibleDecompressedVersion = (String)allCompressedValues.find(str);
            if(possibleDecompressedVersion != null)
            {
                str = possibleDecompressedVersion;
            }
            writer.write(str);
            if(reader.hasNext())
            {
                writer.write(delim); //Don't add extra data
            }
        }
        writer.close();
        reader.close();
    }
}
