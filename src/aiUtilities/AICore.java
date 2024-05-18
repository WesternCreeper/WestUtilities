/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aiUtilities;
import javax.tools.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.io.IOException;
import java.io.File;
import java.net.URI;
import javax.swing.JFrame;

/**
 * This class embodies the AI. It allows for writing in and out of it's memory files, talking to the user, and rewriting its own code.
 * It is the fundamental building block to all life
 * @author Westley
 */
public class AICore 
{
    protected int identificationNumber = 0;
    protected String identificationName = "Bot #" + identificationNumber;
    //protected String languageFile;
    protected AIConsole userConsole = new AIConsole(this);
    protected AIMemoryHandler memory;
    protected ArrayList<Question> allUnansweredQuestions = new ArrayList<Question>(5);
    protected AIStandardFunctions functions = new AIStandardFunctions(this);
    protected AILanguageCore langaugeCore;
    /**
     * Standard setting AICore
     * @param memoryDir The directory for the AI's memories.
     * @param name The name of the AI
     * @param id The identification number of the AI
     * @param langFileDir The directory for the AI's language system
     */
    public AICore(String memoryDir, String name, int id, String langFileDir)
    {
        identificationNumber = id;
        identificationName = name + " ID: " + id;
        functions.say("Core constructing...");
        memory = new AIMemoryHandler(memoryDir);
        functions.say("Memory loaded...");
        langaugeCore = new AILanguageCore(this, memoryDir + "\\" + langFileDir);
        functions.say("Langauge Core loaded...");
        
        functions.say("Core construction completed!");
    }
    
    //Functions:
    public void launchUserConsole()
    {
        functions.say("Console launch requested...");
        userConsole.launchConsole(identificationName, 600, 600, JFrame.EXIT_ON_CLOSE);
        functions.say("Console Launched!");
    }
    
    //Getters/Setters:
    public int getIdentificationNumber()
    {
        return identificationNumber;
    }
    public String getIdentificationName()
    {
        return identificationName;
    }
    public AIConsole getUserConsole()
    {
        return userConsole;
    }
    public AIMemoryHandler getMemory()
    {
        return memory;
    }
    public ArrayList<Question> getAllUnansweredQuestions()
    {
        return allUnansweredQuestions;
    }
    public AIStandardFunctions getFunctions()
    {
        return functions;
    }
}
