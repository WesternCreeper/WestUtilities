/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aiUtilities;

/**
 * The way that the AI creates and learns new functions. This allows for AIs to have many different functions and use them whenever just by editing this file!
 * Actually the proper usage is to extend this class...
 * @author Westley
 */
public abstract class AIFunctions 
{
    protected AICore functionOwner;
    protected AIFunctions(AICore core)
    {
        functionOwner = core;
    }
    public abstract Object runCurrentFunction(Object args);
}
