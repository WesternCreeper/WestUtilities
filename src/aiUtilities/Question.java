/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aiUtilities;

/**
 * A simple way of tracking whether a question has been answered or not
 * @author Westley
 */
public abstract class Question 
{
    protected String question = "";
    protected Question(String que)
    {
        question = que;
    }
    public abstract boolean questionAnswered(String response);
}
