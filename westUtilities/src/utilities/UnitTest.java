/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

/**
 *
 * @author Westley
 */
public class UnitTest 
{
    private String title;
    private String expectedResult;
    private String result;
    
    public UnitTest(String title, String expectedResult, String result)
    {
        this.title = title;
        this.expectedResult = expectedResult;
        this.result = result;
    }
    
    public boolean getResult()
    {
        return expectedResult.equals(result);
    }
    
    public String toString()
    {
        String str = "Title: " + title;
        if(getResult())
        {
            str += " Passed!";
        }
        else
        {
            str += " Failed!";
            str += " Expected: " + expectedResult + " Recieved: " + result;
        }
        return str;
    }
}
