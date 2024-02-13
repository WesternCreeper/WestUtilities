/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aiUtilities;

import java.io.IOException;

/**
 * These are the recommended functions to be in an AI. However a person can completely bi-pass this if they wish to.
 * @author Westley
 */
public class AIStandardFunctions extends AIFunctions
{
    public AIStandardFunctions(AICore core)
    {
        super(core);
    }
    @Deprecated
    public Object runCurrentFunction(Object args)
    {
        return args;
    }
    public boolean command(String str)
    {
        if(str.length() >= 4)
        {
            String firstCharacter = str.substring(0,1);
            if(firstCharacter.equals("/"))
            {
                int commandEnd = str.indexOf(" ");
                if(commandEnd == -1)
                {
                    commandEnd = str.length();
                }
                String command = str.substring(1, commandEnd);
                switch(command)
                {
                    case "help":
                            say("Command List:");
                            say("/say");
                            say("  -> Says whatev");
                            say("  -> Format: \"/say [cool stuff here]\"");
                            say("/get");
                            say("  -> Gets a piece of information.");
                            say("  /!\\ Currently unavalible");
                            say("/run");
                            say("  -> Runs a function.");
                            say("  /!\\ Currently unavalible");
                            say("/org");
                            say("  -> Organizes the language file");
                            say("/store");
                            say("  -> Stores inforamtion into a file");
                            say("  -> Format: \"/store info, file\"");
                            say("/help");
                            say("  -> Shows all Commands and what they do");
                            break;
                    case "say":
                        say(str.substring(4).strip());
                        break;
                    case "clear":
                        functionOwner.getUserConsole().resetOutput();
                        break;
                    case "get":
                        functionOwner.getUserConsole().addToOutput("\nRETREIVING INFORMATION...");
                        break;
                    case "run":
                        functionOwner.getUserConsole().addToOutput("\nRUNNING THAT FUNCTION...");
                        functionOwner.runFunction(str.substring(4).strip());
                        break;
                    case "org":
                        functionOwner.getUserConsole().addToOutput("\nORGANIZING THE CURRENT LANGUAGE FILE...");
                        try
                        {
                            functionOwner.getMemory().organizeLangaugeFile(functionOwner.getLanguageFile());
                        }
                        catch(IOException e)
                        {
                            functionOwner.getUserConsole().addToOutput("\nERROR ERROR! CANNOT ORGANIZE THE FILE!");
                        }
                        break;
                    case "store":
                        String commandParts = str.substring(commandEnd);
                        String temp[] = commandParts.split(",");
                        if(temp.length >= 2)
                        {
                            try
                            {
                                functionOwner.getMemory().store(temp[0].strip(), temp[1].strip());
                            }
                            catch(IOException e)
                            {
                                functionOwner.getUserConsole().addToOutput("\nERROR ERROR! CANNOT STORE IN THAT FILE OR STORE THAT INFORMATION!");
                            }
                        }
                        break;
                    default:
                        functionOwner.getUserConsole().addToOutput("\n\n/!\\UNKNOWN COMMAND! Please consult the /help for information on commands/!\\\n\n");
                        return false;
                }
                return true;
            }
        }
        return false;
    }
    /**
     * This adds to the output stream in the console
     * @param speechString The string to speak
     */
    public void say(String speechString)
    {
        functionOwner.getUserConsole().addToOutput("\n" + speechString);
    }
    /**
     * This adds to the output stream in the console and adds the question to the questions ArrayList
     * @param speechString The string to speak
     * @param keyWord The word that is tripping up the AI
     */
    public void askDefinition(String speechString, String keyWord)
    {
        say(speechString);
        functionOwner.getAllUnansweredQuestions().add(new DefinitionQuestion(speechString, keyWord));
    }
    /**
     * This reads in a line from the AIConsole's input stream.
     * This breaks apart a string into each word and looks into it's functionOwner.getMemory() for the definitions.
     * @param line The line in the input stream
     */
    public void read(String line)
    {
        String eachWord[] = line.split(" ");
        //Before anything see if any questions got answered:
        boolean questionWasAnswered = false; 
        for(int i = 0 ; i < functionOwner.getAllUnansweredQuestions().size() ; i++)
        {
            Question unanswered = functionOwner.getAllUnansweredQuestions().get(i);
            if(unanswered.questionAnswered(line))
            {
                //Now do something with the response:
                if(unanswered instanceof DefinitionQuestion) //If it is a definition question then store the definition:
                {
                    try
                    {
                        if(!questionWasAnswered) //Prevents duplcates when adding phrases
                        {
                            functionOwner.getMemory().store("\n" + line, functionOwner.getLanguageFile());
                        }
                        questionWasAnswered = true;
                    }
                    catch(IOException e)
                    {
                        say("ERROR ERROR! Unable to store definition. Recheck databases please!");
                    }
                }
            }
        }
        //No need to read it again if it was an answer to a question!
        if(!questionWasAnswered)
        {
            //First is it a fetch question?
            boolean fetched = false;
            try
            {
                FetchQuestion fetcher = new FetchQuestion(line);
                Object result = fetcher.questionAnswered(functionOwner.getMemory());
                if(result instanceof String)
                {
                    fetched = true;
                    say((String)result);
                }
            }
            catch(IOException e){} //If this is not a fetch question then just read it like normal
            
            if(fetched)
            {
                return;
            }
            
            //Then replace all phrases with their definitions:
            //Search and find all partal matches, then replace with the one that matches the most words:
            eachWord = functionOwner.getMemory().phraseReplace(eachWord, functionOwner.getLanguageFile());
            
            //Now retrieve all of the definitions of the words:
            int i = 0;
            while(i < eachWord.length)
            {
                //Check to make sure the signle word isn't a command:
                if(i == 0)
                {
                    boolean isCommand = command(eachWord[i]);
                    if(isCommand)
                    {
                        break;
                    }
                }
                
                String pseudonym = functionOwner.getMemory().retrieve(functionOwner.getLanguageFile(), "words", eachWord[i]);

                //Recomple the whole thing:
                String fullSentance = "";
                for(int a = 0 ; a < eachWord.length ; a++)
                {
                    if(a != i)
                    {
                        fullSentance += eachWord[a];
                    }
                    else
                    {
                        fullSentance += pseudonym;
                    }
                }

                //Now if it is a command:
                if(i == 0)
                {
                    boolean isCommand = command(fullSentance);
                    if(isCommand)
                    {
                        break;
                    }
                }

                //If the psedonym could not be found then ask about it:
                askDefinition(eachWord[i] + "?", eachWord[i]);

                i++;
            }
        }
    }
}
