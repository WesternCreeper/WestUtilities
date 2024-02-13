/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aiUtilities;

import java.io.IOException;
import java.io.File;

/**
 *
 * @author Westley
 */
public class FetchQuestion
{
    private final String[] FETCH_QUESTION_KEY_WORDS = {"what is", "what was", "do you know"};
    private final String[] QUESTION_FILLER_WORDS = {"what", "is", "was", "your", "you", "do", "know"};
    private String question;
    public FetchQuestion(String question)
    {
        this.question = question;
    }
    public Object questionAnswered(AIMemoryHandler memory) throws IOException
    {
        //First search the question for certain key words:
        String str = question.toLowerCase();
        boolean isAFetchQuestion = false;
        for(int i = 0 ; i < FETCH_QUESTION_KEY_WORDS.length ; i++)
        {
            if(str.contains(FETCH_QUESTION_KEY_WORDS[i]))
            {
                isAFetchQuestion = true;
                break;
            }
        }
        if(isAFetchQuestion)
        {
            //Now search for the key word:
            
            //Start by removing all words that are not important!
            String[] temp = str.split(" ");
            for(int i = 0 ; i < QUESTION_FILLER_WORDS.length ; i++)
            {
                for(int j = 0 ; j < temp.length ; j++)
                {
                    if(temp[j].equals(QUESTION_FILLER_WORDS[i]))
                    {
                        temp[j] = "";
                    }
                }
            }
            
            //Add all that is left over to a new string
            String str2 = "";
            for(int i = 0 ; i < temp.length ; i++)
            {
                str2 += temp[i];
                if(!temp[i].isEmpty())
                {
                    str2 += " ";
                }
            }
            str2 = str2.strip();
            
            //Now search all of the memory banks:
            File[] allFiles = memory.retrieveAllFilesInBank();
            for(int i = 0 ; i < allFiles.length ; i++)
            {
                String result = memory.retrieveKeyOrValue(allFiles[i].getName(), str2);
                if(!result.isEmpty())
                {
                    return result;
                }
            }
            //If you have hit here then you are out of luck because it is not here, thus notify the user
            String result = "I am sorry but I cannot find: " + str2;
            return result;
        }
        return false;
    }
}
