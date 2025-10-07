/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aiUtilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import utilities.FileProcessor;
import utilities.FileSet;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The memory storage and retriever for an AI
 * @author Westley
 */
public class AIMemoryHandler 
{
    private File aiMemoryDirectory;
    public AIMemoryHandler(String aiMemoryDirectory)
    {
        this.aiMemoryDirectory = new File(aiMemoryDirectory);
    }
    /**
     * A very simple way to store a piece of information. This will create any missing directories.
     * @param data The data to be stored
     * @param dataFile The file path to the file where the information needed to be stored
     * @throws IOException If a problem occurs parsing the file
     */
    public void store(String data, String dataFile) throws IOException
    {
        File dataPath = new File(aiMemoryDirectory.getPath() + "\\" + dataFile);
        if(!dataPath.exists())
        {
            dataPath.createNewFile();
        }
        FileWriter writer = new FileWriter(dataPath, true);
        writer.append(data);
        writer.close();
    }
    /**
     * Retrieves data from the file system of memory
     * Requires a File and Key for getting the stored data.
     * @param dataFile The file to be looking into
     * @param key The key that goes with the value
     * @return 
     */
    public String retrieve(String dataFile, String key)
    {
        FileProcessor finder = new FileProcessor(aiMemoryDirectory.getPath() + "\\" + dataFile);
        String data = finder.findValue(key, false);
        if(!FileProcessor.FAILED_RETURN_VALUE.equals(data))
        {
            return data;
        }
        return "";
    }
    /**
     * Retrieves data from the file system of memory, which can be a key or a value
     * Requires a File and Key for getting the stored data.
     * @param dataFile The file to be looking into
     * @param key The key that goes with the value
     * @return 
     */
    public String retrieveKeyOrValue(String dataFile, String key)
    {
        FileProcessor finder = new FileProcessor(aiMemoryDirectory.getPath() + "\\" + dataFile);
        String data = finder.findValue(key, false); //First search as if it is the key
        if(!FileProcessor.FAILED_RETURN_VALUE.equals(data))
        {
            return data;
        }
        else //Then search as if it is the value
        {
            data = finder.findKey(key, false);
            if(!FileProcessor.FAILED_RETURN_VALUE.equals(data))
            {
                return data;
            }
        }
        return ""; //There is nothing in the memory that matches that inquiry, better luck next time
    }
    /**
     * Retrieves data from the file system of memory
     * Requires a File, Section, and Key for getting the stored data.
     * @param dataFile The file to be looking into
     * @param section The section where in it is
     * @param key The key that goes with the value
     * @return 
     */
    public String retrieve(String dataFile, String section, String key)
    {
        FileProcessor finder = new FileProcessor(aiMemoryDirectory.getPath() + "\\" + dataFile);
        String data = finder.findValue(section, key, false);
        if(!FileProcessor.FAILED_RETURN_VALUE.equals(data))
        {
            return data;
        }
        return "";
    }
    public File[] retrieveAllFilesInBank() throws IOException
    {
        //Retrieve
        File[] files = aiMemoryDirectory.listFiles();
        
        //Sort into readables:
        ArrayList<File> returnedFiles = new ArrayList<File>(1);
        for(int i = 0 ; i < files.length ; i++)
        {
            if(files[i].exists())
            {
                boolean canAccess = true;
                try
                {
                    Scanner sc = new Scanner(files[i]);
                    sc.close();
                }
                catch(Exception e)
                {
                    canAccess = false;
                }
                if(canAccess)
                {
                    returnedFiles.add(files[i]);
                }
            }
        }
        
        //Now turn it into an array because that is easier to read through:
        File[] result = new File[returnedFiles.size()];
        for(int i = 0 ; i < returnedFiles.size() ; i++)
        {
            result[i] = returnedFiles.get(i);
        }
        return result;
    }
    public File getFile(String fileName)
    {
        return getFile(aiMemoryDirectory, fileName);
    }
    public File getFile(File currentFolder, String fileName)
    {
        File[] allFilesInFolder = currentFolder.listFiles();
        if(allFilesInFolder == null)
        {
            return null;
        }
        //Now search for the folder that is wanted:
        File searchResult = null;
        for(int i = 0 ; i < allFilesInFolder.length ; i++)
        {
            if(searchResult != null)
            {
                break;
            }
            if(allFilesInFolder[i].getName().equals(fileName))
            {
                searchResult = allFilesInFolder[i];
                break;
            }
            else if(allFilesInFolder[i].isDirectory()) //If it is a folder then go into it
            {
                searchResult = getFile(allFilesInFolder[i], fileName);
            }
        }
        return searchResult;
    }
    /**
     * This does a very good job at reorganizing the language file of an AI. It may be CPU and RAM intensive if done too often but it does do its job.
     * @param langFile The language file to be sorted
     * @throws IOException If a problem occurs parsing the file
     */
    public void organizeLangaugeFile(String langFile) throws IOException
    {
        langFile = aiMemoryDirectory.getPath() + "\\" + langFile;
        //Read in the whole file
        FileProcessor reader = new FileProcessor(langFile);
        FileSet wholeFile = reader.getAllKeysandValues(false);
        //Now take in everything in each section:
        ArrayList<String> wordKeys = new ArrayList<String>(10);
        ArrayList<String> wordValues = new ArrayList<String>(10);
        int iStart = wholeFile.getKeyStart(0);
        int iEnd = wholeFile.getKeyStart(1);
        for(int i = iStart ; i < iEnd ; i++)
        {
            wordKeys.add(wholeFile.getKey(i));
            wordValues.add(wholeFile.getValue(i));
        }
        ArrayList<String> phraseKeys = new ArrayList<String>(10);
        ArrayList<String> phraseValues = new ArrayList<String>(10);
        iStart = wholeFile.getKeyStart(1);
        iEnd = wholeFile.getKeyStart(2);
        for(int i = iStart ; i < iEnd ; i++)
        {
            phraseKeys.add(wholeFile.getKey(i));
            phraseValues.add(wholeFile.getValue(i));
        }
        ArrayList<String> keys = new ArrayList<String>(10);
        ArrayList<String> values = new ArrayList<String>(10);
        iStart = wholeFile.getKeyStart(2);
        iEnd = wholeFile.getKeyStart(3);
        for(int i = iStart ; i < iEnd ; i++)
        {
            keys.add(wholeFile.getKey(i));
            values.add(wholeFile.getValue(i));
        }
        //Now add these collections back into the respective locations:
        for(int i = 0 ; i < keys.size() ; i++)
        {
            String key = keys.get(i);
            if(key.split(" ").length > 1)
            {
                phraseKeys.add(key);
                phraseValues.add(values.get(i));
            }
            else
            {
                wordKeys.add(key);
                wordValues.add(values.get(i));
            }
        }
        //Now alphabetize each of them:
        for(int i = 0 ; i < wordKeys.size()-1 ; i++)
        {
            int biggest = i;
            for(int j = i+1 ; j < wordKeys.size() ; j++)
            {
                if(wordKeys.get(j).compareTo(wordKeys.get(biggest)) > 0)
                {
                    biggest = j;
                }
            }
            String temp = wordKeys.get(biggest);
            wordKeys.set(biggest, wordKeys.get(i));
            wordKeys.set(i, temp);
            temp = wordValues.get(biggest);
            wordValues.set(biggest, wordValues.get(i));
            wordValues.set(i, temp);
        }
        for(int i = 0 ; i < phraseKeys.size()-1 ; i++)
        {
            int biggest = i;
            for(int j = i+1 ; j < phraseKeys.size() ; j++)
            {
                if(phraseKeys.get(j).compareTo(phraseKeys.get(biggest)) > 0)
                {
                    biggest = j;
                }
            }
            String temp = phraseKeys.get(biggest);
            phraseKeys.set(biggest, phraseKeys.get(i));
            phraseKeys.set(i, temp);
            temp = phraseValues.get(biggest);
            phraseValues.set(biggest, phraseValues.get(i));
            phraseValues.set(i, temp);
        }
        //Now return all of this to the file:
        FileWriter writer = new FileWriter(langFile);
        writer.write("[words]");
        for(int i = 0 ; i < wordKeys.size() ; i++)
        {
            writer.write("\n" + wordKeys.get(i) + " = " + wordValues.get(i));
        }
        writer.write("\n[phrases]");
        for(int i = 0 ; i < phraseKeys.size() ; i++)
        {
            writer.write("\n" + phraseKeys.get(i) + " = " + phraseValues.get(i));
        }
        writer.write("\n[misc]");
        writer.close();
    }
    public String[] phraseReplace(String[] eachWord, String langFile)
    {
        langFile = aiMemoryDirectory.getPath() + "\\" + langFile;
        String result[] = eachWord;
        FileProcessor finder = new FileProcessor(langFile);
        int i = 0;
        while(i < result.length)
        {
            //This finds all of the partial matches within the file
            ArrayList<String> allPartialKeys = finder.findAllPartialMatches("phrases", result[i]);
            //Now find the phrase that most closely matches the whole line
            if(allPartialKeys.size() > 0)
            {
                String wholeLine = String.join(" ", result);
                int closestMatch = 0;
                for(int j = 1 ; j < allPartialKeys.size() ; j++)
                {
                    if(wholeLine.contains(allPartialKeys.get(j))) //If it contains 
                    {
                        int partLength = allPartialKeys.get(j).split(" ").length;
                        int closestPartLength = allPartialKeys.get(closestMatch).split(" ").length;
                        if(closestPartLength < partLength) //Now check that it is the closest match
                        {
                            closestMatch = j;
                        }
                    }
                }
                //Now replace in the whole line with the proper definition.
                //System.out.println(allPartialKeys.get(closestMatch));
                String value = finder.findValue("phrases", allPartialKeys.get(closestMatch), false);
                wholeLine = wholeLine.replace(allPartialKeys.get(closestMatch), value);
                result = wholeLine.split(" ");
            }
            i++;
        }
        return result;
    }
    public boolean createBackupFile(String file) throws IOException
    {
        return createBackupFile(file, true);
    }
    /**
     * This function will create a backup of the file with the extention of .blahBAC (up to three of them).
     * @param file The file needed to be backed up
     * @param rewriteData If true then the backup and the new file will both contain the data held in the original file. Otherwise only the backup will
     * @return
     * @throws IOException 
     */
    public boolean createBackupFile(String file, boolean rewriteData) throws IOException
    {
        file = aiMemoryDirectory.getPath() + "\\" + file;
        File orginalFile = new File(file);
        File backupFile = new File(file + "bac");
        File backupFile2 = new File(file + "bac2");
        File backupFile3 = new File(file + "bac3");
        boolean bac3Delete = backupFile3.delete();
        boolean bac2To3 = backupFile2.renameTo(new File(file + "bac3"));
        boolean bac1To2 = backupFile.renameTo(new File(file + "bac2"));
        boolean orgTo1 = orginalFile.renameTo(new File(file + "bac"));
        File newFile = new File(file);
        boolean newOrg = newFile.createNewFile();
        if(rewriteData)
        {
            Scanner sc = new Scanner(backupFile);
            FileWriter writer = new FileWriter(newFile);
            int i = 0;
            while(sc.hasNextLine())
            {
                if(i != 0)
                {
                    writer.write("\n");
                }
                writer.write(sc.nextLine());
                i++;
            }
            writer.close();
            sc.close();
        }
        return bac3Delete && bac2To3 && bac1To2 && orgTo1 && newOrg;
    }
}
