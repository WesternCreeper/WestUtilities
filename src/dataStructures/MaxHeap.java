/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataStructures;

/**
 *
 * @author Westley
 */
public class MaxHeap
{
    private int currentSize = 0;
    private boolean orderOK = true;
    private Long[] array;

    public MaxHeap(Long infinity, int size)
    {
        array = new Long[size];
        array[0] = infinity;
    }

    public boolean isEmpty()
    {
        return currentSize == 0;
    }

    public Long findMax()
    {
        if(isEmpty())
        {
            return null;
        }
        if(!orderOK)
        {
            fixHeap();
        }
        return array[1];
    }

    public void checkSize()
    {
        if(currentSize == array.length -1)
        {
            Long[] oldArray = array;
            array = new Long[currentSize *2];
            for(int i = 0 ; i < oldArray.length ; i++)
            {
                array[i] = oldArray[i];
            }
        }
    }

    public void toss(Long obj)
    {
        checkSize();
        array[++currentSize] = obj;
        if(obj.compareTo(array[currentSize/2]) > 0)
        {
            orderOK = false;
        }
    }

    public void insert(Long obj)
    {
        if(!orderOK)
        {
            toss(obj);
            return;
        }

        checkSize();

        //Percolate up:
        int hole = ++currentSize;
        while(obj.compareTo(array[hole/2]) > 0)
        {
            array[hole] = array[hole/2];
            hole /= 2;
        }
        array[hole] = obj;
    }

    public Long deleteMax()
    {
        if(currentSize == 0)
        {
            return null;
        }
        Long minItem = findMax();
        array[1] = array[currentSize--];
        percolateDown(1);

        return minItem;
    }

    public void percolateDown(int hole)
    {
        int child;
        Long temp = array[hole];
        
        while(hole * 2 <= currentSize)
        {
            child = hole * 2;
            if(child != currentSize && array[child+1].compareTo(array[child]) > 0)
            {
                child++;
            }
            if(array[child].compareTo(temp) > 0)
            {
                array[hole] = array[child];
            }
            else
            {
                break;
            }

            hole = child;
        }
        array[hole] = temp;
    }

    public void fixHeap()
    {
        for(int i = currentSize / 2 ; i > 0 ; i--)
        {
            percolateDown(i);
        }
        orderOK = true;
    }
}

