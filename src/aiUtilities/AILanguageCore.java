/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aiUtilities;

import java.io.File;

/**
 *
 * @author Westley
 */
public class AILanguageCore 
{
    protected AICore parentCore;
    private File languageCoreFolder;
    private File grammarFile;
    private File dictionaryFile;
    
    public AILanguageCore(AICore parentCore, String languageCoreFolder)
    {
        //Set up the Core:
        this.languageCoreFolder = new File(languageCoreFolder);
        this.parentCore = parentCore;
        
        //Grab the memory from the parent core and find the files
        AIMemoryHandler tempMemory = parentCore.getMemory();
        grammarFile = tempMemory.getFile(this.languageCoreFolder, "Grammar.txt");
        dictionaryFile = tempMemory.getFile(this.languageCoreFolder, "Dictionary.txt");
        
        //Finish:
        parentCore.getFunctions().say("Main Folder: " + this.languageCoreFolder.getName());
        parentCore.getFunctions().say("Files found: " + grammarFile + " " + dictionaryFile);
        parentCore.getFunctions().say("Sentance test 1: " + createSentance());
        parentCore.getFunctions().say("Sentance test 2: " + createSentance());
        parentCore.getFunctions().say("Sentance test 3: " + createSentance());
    }
    
    public String createSentance()
    {
        String result = "";
        
        return result;
    }
}
