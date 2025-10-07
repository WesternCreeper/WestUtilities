/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import java.util.Arrays;

/**
 *
 * @author Westley
 */
public class ArrayHelper 
{
    /**
     * Returns -1 as a query not found
     * @param array
     * @param query
     * @return 
     */
    public static int binarySearch(int[] array, int query)
    {
        int min = 0;
        int max = array.length;
        int count = 0;
        int maxLoopCount = (int)(Math.log10(array.length) / Math.log10(2)) + 1;
        while(true)
        {
            int i = (min + max) / 2;
            if(array[i] > query)
            {
                max = i;
            }
            if(array[i] < query)
            {
                min = i;
            }
            if(query == array[i])
            {
                return i;
            }
            else if(count >= maxLoopCount)
            {
                return -1;
            }
            count++;
        }
    }
    public static int[] mergeSort(int[] array)
    {
        //Split the array
        if(array.length > 1)
        {
            int split = (int)(array.length / 2.0 + .5);
            int[] array1 = new int[split];
            int[] array2 = new int[array.length - split];
            for(int i = 0 ; i < array1.length ; i++)
            {
                array1[i] = array[i];
            }
            for(int i = 0 ; i < array2.length ; i++)
            {
                array2[i] = array[i+split];
            }
            //now merge again:
            array1 = mergeSort(array1);
            array2 = mergeSort(array2);
            
            //Now sort:
            int[] result = new int[array.length];
            int i = 0;
            int j = 0;
            int c = 0;
            while(i < array1.length || j < array2.length)
            {
                if(i >= array1.length || j >= array2.length)
                {
                    if(i >= array1.length) //Finish the other array:
                    {
                        result[c] = array2[j];
                        c++;
                        j++;
                    }
                    else
                    {
                        result[c] = array1[i];
                        c++;
                        i++;
                    }
                }
                else
                {
                    if(array1[i] < array2[j])
                    {
                        result[c] = array1[i];
                        c++;
                        i++;
                    }
                    else
                    {
                        result[c] = array2[j];
                        c++;
                        j++;
                    }
                }
            }
            return result;
        }
        else
        {
            return array;
        }
    }
}
