/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aiUtilities;

/**
 *
 * @author Westley
 */
public class DefinitionQuestion extends Question
{
    private String unknownWord = "";
    public DefinitionQuestion(String question, String keyWord)
    {
        super(question);
        unknownWord = keyWord;
    }
    public boolean questionAnswered(String response)
    {
        //For a definition to be complete there needs to be proper wording:
        String unknownWord = question.substring(0, question.length() - 1);
        String tempString[] = response.split("=");
        if(tempString.length == 2 && response.contains(unknownWord)) //So it is the defintion now return true and kill the question:
        {
            return true;
        }
        return false;
    }
}
