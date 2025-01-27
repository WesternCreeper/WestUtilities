/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
/**
 *
 * @author Westley
 */
public class FileProcessor 
{
    private File file = null;
    private final static String sectionHeader = "[";
    private final static String sectionHeaderEnd = "]";
    public final static String FAILED_RETURN_VALUE = "can't find";
    /**
     * Creates a new FileProcessor Object that makes a file from your string
     * @param fileName The name of the file including the extension. For example: .txt
     */
    public FileProcessor(String fileName)
    {
        try
        {
            file = new File(fileName);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }
    /**
     * Creates a new FileProcessor Object that uses the File you supply
     * @param file The file
     */
    public FileProcessor(File file)
    {
        this.file = file;
    }
    /**
     * Finds the value under the section and key that are supplied. If there is no section or key in the file then it will return "can't find"
     * @param section
     * @param key
     * @return 
     */
    public String findValue(String section, String key)
    {
        return findValue(section, key, true);
    }
    /**
     * Finds the value under the section and key that are supplied. If there is no section or key in the file then it will return "can't find"
     * @param section
     * @param key
     * @param removeSpaceWithinString if true, then it will act as the other findValue, otherwise it will not remove the whitespace within the value
     * @return 
     */
    public String findValue(String section, String key, boolean removeSpaceWithinString)
    {
        try
        {
            if(!file.exists())
            {
                return "file doesn't exist";
            }
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine())
            {
                String str = sc.nextLine();
                str = str.trim();
                if(str.contains("[") && str.contains("]")) //Is a section!
                {
                    String fileSectionName = str.substring(str.indexOf("[") + 1, str.indexOf("]"));
                    fileSectionName = removeWhite(fileSectionName, removeSpaceWithinString);
                    if(fileSectionName.toLowerCase().equals( removeWhite(section, removeSpaceWithinString).toLowerCase() )) //Then check the inside of the section to the section name
                    {
                        while(sc.hasNextLine())
                        {
                            str = sc.nextLine();
                            str = str.trim();
                            str = removeWhite(str, removeSpaceWithinString);
                            if(str.length() >= 1 && str.substring(0, 1).equals(sectionHeader))
                            {
                                return FAILED_RETURN_VALUE;
                            }
                            //Find the location of the equals sign
                            String keyAndValue[] = str.split("=");
                            if(keyAndValue.length == 2)
                            {
                                String keyInLine = keyAndValue[0].trim();
                                if(keyInLine.toLowerCase().equals(removeWhite(key, removeSpaceWithinString).toLowerCase()))
                                {
                                    sc.close();
                                    str = keyAndValue[1];
                                    return str.trim();
                                }
                            }
                        }
                    }
                }
            }
            sc.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        return FAILED_RETURN_VALUE;
    }
    /**
     * Finds the value, regardless of where it is in the file, and key that are supplied. If there is no key in the file then it will return "can't find"
     * @param key
     * @param removeSpaceWithinString if true, then it will act as the other findValue, otherwise it will not remove the whitespace within the value
     * @return 
     */
    public String findValue(String key, boolean removeSpaceWithinString)
    {
        try
        {
            if(!file.exists())
            {
                return "file doesn't exist";
            }
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine())
            {
                String str = sc.nextLine();
                String test = str.trim();
                test = removeWhite(test, true);
                if(test.length() >= 1 && !test.substring(0, 1).equals(sectionHeader))
                {
                    str = str.trim();
                    str = removeWhite(str, removeSpaceWithinString);
                    //Find the location of the equals sign
                    String keyAndValue[] = str.split("=");
                    if(keyAndValue.length == 2)
                    {
                        String keyInLine = keyAndValue[0].trim();
                        if(keyInLine.toLowerCase().equals(removeWhite(key, removeSpaceWithinString).toLowerCase()))
                        {
                            sc.close();
                            str = keyAndValue[1];
                            return str.trim();
                        }
                    }
                }
            }
            sc.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        return FAILED_RETURN_VALUE;
    }
    /**
     * Finds the key under the section and value that are supplied. If there is no section or value in the file then it will return "can't find" 
     * Sometimes you have just misplaced that key and need the value
     * @param section
     * @param value
     * @param removeSpaceWithinString if true, then it will act as the other findValue, otherwise it will not remove the whitespace within the value
     * @return 
     */
    public String findKey(String section, String value, boolean removeSpaceWithinString)
    {
        try
        {
            if(!file.exists())
            {
                return "file doesn't exist";
            }
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine())
            {
                String str = sc.nextLine();
                str = str.trim();
                if(str.contains("[") && str.contains("]")) //Is a section!
                {
                    String fileSectionName = str.substring(str.indexOf("[") + 1, str.indexOf("]"));
                    fileSectionName = removeWhite(fileSectionName, removeSpaceWithinString);
                    if(fileSectionName.toLowerCase().equals( removeWhite(section, removeSpaceWithinString).toLowerCase() )) //Then check the inside of the section to the section name
                    {
                        while(sc.hasNextLine())
                        {
                            str = sc.nextLine();
                            str = str.trim();
                            str = removeWhite(str, removeSpaceWithinString);
                            if(str.length() >= 1 && str.substring(0, 1).equals(sectionHeader))
                            {
                                return FAILED_RETURN_VALUE;
                            }
                            //Find the location of the equals sign
                            String keyAndValue[] = str.split("=");
                            if(keyAndValue.length == 2)
                            {
                                String valueInLine = keyAndValue[1].trim();
                                if(valueInLine.toLowerCase().contains(removeWhite(value, removeSpaceWithinString).toLowerCase()))
                                {
                                    sc.close();
                                    str = keyAndValue[0];
                                    return str.trim();
                                }
                            }
                        }
                    }
                }
            }
            sc.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        return FAILED_RETURN_VALUE;
    }
    /**
     * Finds the key, regardless of where it is in the file, and value that are supplied. If there is no value in the file then it will return "can't find"
     * Sometimes you have just misplaced that key and need the value
     * @param value
     * @param removeSpaceWithinString if true, then it will act as the other findValue, otherwise it will not remove the whitespace within the value
     * @return 
     */
    public String findKey(String value, boolean removeSpaceWithinString)
    {
        try
        {
            if(!file.exists())
            {
                return "file doesn't exist";
            }
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine())
            {
                String str = sc.nextLine();
                String test = str.trim();
                test = removeWhite(test, true);
                if(test.length() >= 1 && !test.substring(0, 1).equals(sectionHeader))
                {
                    str = str.trim();
                    str = removeWhite(str, removeSpaceWithinString);
                    //Find the location of the equals sign
                    String keyAndValue[] = str.split("=");
                    if(keyAndValue.length == 2)
                    {
                        String keyInLine = keyAndValue[1].trim();
                        if(keyInLine.toLowerCase().contains(removeWhite(value, removeSpaceWithinString).toLowerCase()))
                        {
                            sc.close();
                            str = keyAndValue[0];
                            return str.trim();
                        }
                    }
                }
            }
            sc.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        return FAILED_RETURN_VALUE;
    }
    /**
     * Finds any and all keys that match partially the inserted key
     * @param section The section to find
     * @param keyPart The part of the key that is compared to the keys
     * @return ArrayList[String] This is the array of all possible matches. Can be length 0;
     */
    public ArrayList<String> findAllPartialMatches(String section, String keyPart)
    {
        ArrayList<String> matches = new ArrayList<String>(0);
        try
        {
            if(!file.exists())
            {
                return matches;
            }
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()) //Searches for the section:
            {
                String str = sc.nextLine();
                str = str.trim();
                str = removeWhite(str, true);
                section = removeWhite(section, true);
                //If it is the section then search for partial matches
                if(str.length() >= (sectionHeader.length() + section.length()) && str.substring(0, sectionHeader.length()).equalsIgnoreCase(sectionHeader) && str.substring(sectionHeader.length(), sectionHeader.length() + section.length()).equalsIgnoreCase(section))
                {
                    while(sc.hasNextLine()) //Goes through the body of the section:
                    {
                        str = sc.nextLine();
                        str = str.trim();
                        if(str.length() >= 1 && str.substring(0, 1).equals(sectionHeader)) //Stops at the end of the section
                        {
                            sc.close();
                            return matches;
                        }
                        //Now splits the string over the equals sign so that is can do magic!
                        String keyAndValue[] = str.split("=");
                        if(keyAndValue.length == 2)
                        {
                            String key = keyAndValue[0];
                            key = key.trim();
                            if(key.contains(keyPart) || key.equals(keyPart))
                            {
                                matches.add(key);
                            }
                        }
                    }
                }
            }
            sc.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        return matches;
    }
    /**
     * Finds the header for the section, and if that doesn't exist, then it will return false. If it does exist then it will return true
     * @param section
     * @return BOOLEAN
     */
    public boolean sectionExists(String section)
    {
        try
        {
            if(!file.exists())
            {
                return false;
            }
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine())
            {
                String str = sc.nextLine();
                str = str.trim();
                if(str.contains("[") && str.contains("]")) //Is a section!
                {
                    String fileSectionName = str.substring(str.indexOf("[") + 1, str.indexOf("]"));
                    if(fileSectionName.toLowerCase().equals(section.toLowerCase())) //Then check the inside of the section to the section name
                    {
                        return true;
                    }
                }
            }
            sc.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }
    /**
     * Finds the index of the section header. This will return -1 for a not found.
     * @param section The name of the section
     * @return INTEGER
     */
    public int findSectionIndex(String section)
    {
        try
        {
            if(!file.exists())
            {
                return -1;
            }
            Scanner sc = new Scanner(file);
            int i = 0;
            while(sc.hasNextLine())
            {
                String str = sc.nextLine();
                str = str.trim();
                if(str.contains("[") && str.contains("]")) //Is a section!
                {
                    String fileSectionName = str.substring(str.indexOf("[") + 1, str.indexOf("]"));
                    if(fileSectionName.toLowerCase().equals(section.toLowerCase())) //Then check the inside of the section to the section name
                    {
                        return i;
                    }
                }
                i++;
            }
            sc.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        return -1;
    }
    public FileSet getAllKeysandValues(boolean removeSpaceWithinString)
    {
        FileSet allInfo = new FileSet();
        try
        {
            if(!file.exists())
            {
                return allInfo;
            }
            Scanner sc = new Scanner(file);
            //Place the file in a string arrayList
            ArrayList<String> lines = new ArrayList<String>(0);
            while(sc.hasNextLine())
            {
                lines.add(sc.nextLine());
            }
            for(int i = 0 ; i < lines.size() ; i++) //Find the sections
            {
                String str = lines.get(i);
                str = str.trim();
                str = removeWhite(str, true);
                if(str.contains(sectionHeader))
                {
                    //Make it in the proper format
                    int indexOfSectionHeader = str.indexOf(sectionHeader);
                    int indexOfSectionHeaderEnd = str.indexOf(sectionHeaderEnd);
                    allInfo.addSection(str.substring(indexOfSectionHeader + 1, indexOfSectionHeaderEnd));
                    for(int j = (i+1) ; j < lines.size() ; j++) //Find all keys and values under the section
                    {
                        str = lines.get(j);
                        str = str.trim();
                        str = removeWhite(str, removeSpaceWithinString);
                        if(str.contains(sectionHeader))
                        {
                            break;
                        }
                        //Make both values go to the correct places
                        String[] temp;
                        temp = str.split("=");
                        if(temp.length == 2) //Make sure there is only two values
                        {
                            if(removeSpaceWithinString)
                            {
                                allInfo.addKey(temp[0]);
                                allInfo.addValue(temp[1]);
                            }
                            else
                            {
                                allInfo.addKey(temp[0].trim());
                                allInfo.addValue(temp[1].trim());
                            }
                        }
                    }
                }
            }
            sc.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        return allInfo;
    }
    /**
     * Gets all of the keys and values in a section supplied by the user
     * @param section The section needed to be probed
     * @param removeSpaceWithinString Same as usual. True = remove spaces within string, false = not
     * @return 
     */
    public ArrayList<String[]> getAllKeysandValuesInSection(String section, boolean removeSpaceWithinString)
    {
        ArrayList<String[]> allInfo = new ArrayList<String[]>(100);
        try
        {
            if(!file.exists())
            {
                return allInfo;
            }
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()) //Searches for the section:
            {
                String str = sc.nextLine();
                str = str.trim();
                //If it is the section then find the keys and values
                if(str.contains("[") && str.contains("]")) //Is a section!
                {
                    String fileSectionName = str.substring(str.indexOf("[") + 1, str.indexOf("]"));
                    if(fileSectionName.toLowerCase().equals(section.toLowerCase())) //Then check the inside of the section to the section name
                    {
                        while(sc.hasNextLine()) //Goes through the body of the section:
                        {
                            str = sc.nextLine();
                            str = str.trim();
                            str = removeWhite(str, removeSpaceWithinString);
                            String temp[] = str.split("=");
                            if(temp.length >= 2)
                            {
                                temp[0] = temp[0].strip();
                                temp[1] = temp[1].strip();
                                allInfo.add(temp);
                                if(str.length() >= 1 && str.substring(0, 1).equals(sectionHeader)) //Stops at the end of the section
                                {
                                    sc.close();
                                    return allInfo;
                                }
                            }
                        }
                    }
                }
            }
            sc.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        return allInfo;
    }
    /**
     * Removes the white space from a string and will remove all comments
     * @param str
     * @param removeSpace This is to decide whether or not the system will leave the spaces in the String or 
     * @return 
     */
    public static String removeWhite(String str, boolean removeSpace)
    {
        String result = str;
        //Remove spaces
        if(removeSpace)
        {
            String temp[] = result.split(" ");
            result = "";
            for(int i = 0 ; i < temp.length ; i++)
            {
                result += temp[i];
            }
        }
        //Remove comments
        for(int i = 0 ; i < result.length()-1 ; i++)
        {
            if(result.substring(i, i+2).equals("//"))
            {
                result = result.substring(0, i);
            }
        }
        return result;
    }
    /**
     * Will return an int if and only if there is no chars. Will return Integer.MIN_VALUE otherwise
     * @param str The string value to be converted
     * @return INT
     */
    public static int toInt(String str)
    {
        return toInt(str, Integer.MIN_VALUE);
    }
    /**
     * Will return an int if and only if there is no chars. Will return default value otherwise
     * @param str The string value to be converted
     * @param defaultValue The default value that will be returned if the result could not be turned into an int. Including but not limited to String values, Object values, Boolean values, or Double values
     * @return INT
     */
    public static int toInt(String str, int defaultValue)
    {
        try
        {
            int num = Integer.valueOf(str);
            return num;
        }
        catch(NumberFormatException e)
        {
            return defaultValue;
        }
    }
    /**
     * Using a string this function will make that into a decimal value. String must be in format: 1/2 OR .5
     * @param str
     * @return The number created during division, will return 0 if the second number is 0 or there is an error
     */
    public static float toFloat(String str)
    {
        return toFloat(str, 0);
    }
    /**
     * Using a string this function will make that into a decimal value. String must be in format: 1/2 OR .5
     * @param str
     * @param defaultValue The default value that will be returned if the result could not be turned into an int. Including but not limited to String values, Object values, or Boolean values
     * @return The number created during division, will return defaultValue if there is an error
     */
    public static float toFloat(String str, float defaultValue)
    {
        String temp[] = str.split("/");
        if(temp.length == 2)
        {
            float a = toInt(temp[1]);
            if(a > 0)
            {
                return toInt(temp[0]) / a;
            }
        }
        
        //If a decimal:
        try
        {
            float num = Float.valueOf(str);
            return num;
        }
        catch(NumberFormatException e)
        {
            return defaultValue;
        }
    }
    /**
     * Using a string this function will make that into a decimal value. String must be in format: 1/2 OR .5
     * @param str
     * @return The number created during division, will return 0 if the second number is 0 or there is an error
     */
    public static double toDouble(String str)
    {
        return toDouble(str, 0);
    }
    /**
     * Using a string this function will make that into a decimal value. String must be in format: 1/2 OR .5
     * @param str
     * @param defaultValue The default value that will be returned if the result could not be turned into an int. Including but not limited to String values, Object values, or Boolean values
     * @return The number created during division, will return 0 if the second number is 0 or there is an error
     */
    public static double toDouble(String str, double defaultValue)
    {
        String temp[] = str.split("/");
        if(temp.length == 2)
        {
            double a = toInt(temp[1]);
            if(a > 0)
            {
                return toInt(temp[0]) / a;
            }
        }
        
        //If a decimal:
        try
        {
            double num = Double.valueOf(str);
            return num;
        }
        catch(NumberFormatException e)
        {
            return defaultValue;
        }
    }
    /**
     * Using a string this function will make that into a boolean value. True/False
     * @param str
     * @return The boolean created from the string, will return false if there is an error
     */
    public static boolean toBoolean(String str)
    {
        return toBoolean(str, false);
    }
    /**
     * Using a string this function will make that into a boolean value. True/False
     * @param str
     * @param defaultValue The default value that will be returned if the result could not be turned into an int. Including but not limited to String values, Object values, Double values, or Integer values
     * @return The boolean created from the string, will return false if there is an error
     */
    public static boolean toBoolean(String str, boolean defaultValue)
    {
        //If a Boolean:
        try
        {
            boolean bool = Boolean.valueOf(str);
            return bool;
        }
        catch(NumberFormatException e)
        {
            return defaultValue;
        }
    }
    
    /**
     * Turns all /s into spaces
     * @param str The string that wants spaces
     * @return The new String that has spaces
     */
    public static String addSpaces(String str)
    {
        for(int i = 0 ; i < str.length()-1 ; i++)
        {
            if(str.substring(i, i+2).equalsIgnoreCase("/s"))
            {
                str = str.substring(0, i) + " " + str.substring(i+2);
            }
        }
        return str;
    }
    public static String getHeaderString()
    {
        return sectionHeader;
    }
}
