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

/**
 * This class embodies the AI. It allows for writing in and out of it's memory files, talking to the user, and rewriting its own code.
 * It is the fundamental building block to all life
 * @author Westley
 */
public abstract class AICore 
{
    protected int identificationNumber = 0;
    protected String identificationName = "Bot #" + identificationNumber;
    protected String languageFile;
    protected AIConsole userConsole = new AIConsole(this);
    protected AIMemoryHandler memory;
    protected ArrayList<Question> allUnansweredQuestions = new ArrayList<Question>(5);
    protected AIStandardFunctions functions = new AIStandardFunctions(this);
    /**
     * Standard setting AICore
     * @param memoryDir The directory for the AI's memories.
     */
    public AICore(String memoryDir)
    {
        this(memoryDir, "Language\\English.lang");
    }
    /**
     * Standard setting AICore
     * @param memoryDir The directory for the AI's memories.
     * @param langFileDir The directory for the AI's language system
     */
    public AICore(String memoryDir, String langFileDir)
    {
        memory = new AIMemoryHandler(memoryDir);
        languageFile = langFileDir;
        try
        {
            memory.createBackupFile(langFileDir);
            /*
            //This re-complies a file
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
            DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
            File file = new File("src/tester/Tester.java"); //This is the file/ files to be complied
            Iterable<? extends JavaFileObject> compilationUnits1 = fileManager.getJavaFileObjectsFromFiles(Arrays.asList(file));
            System.out.println(compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits1).call()); //The actual compliation. This can then be changed paramenter wise and other stuff. Add .call() to call a function/ entire file
            for(Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics())
            {
                System.out.format("Error on line %d in %s%n", diagnostic.getLineNumber(), diagnostic.getSource().toUri());
            }
            fileManager.close();
            */
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    /**
     * Standard setting AICore
     * @param memoryDir The directory for the AI's memories.
     * @param name The name of the AI
     * @param id The identification number of the AI
     */
    public AICore(String memoryDir, String name, int id)
    {
        this(memoryDir);
        identificationNumber = id;
        identificationName = name;
    }
    
    //Functions:
    public void launchUserConsole()
    {
        userConsole.launch();
    }
    public abstract Object runFunction(Object args);
    
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
    public String getLanguageFile()
    {
        return languageFile;
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
