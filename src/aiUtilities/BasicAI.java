/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aiUtilities;

import aiUtilities.Emotion;

/**
 *
 * @author Westley
 */
public class BasicAI 
{
    protected byte gender = 0;
    protected String firstName = "";
    protected String lastName = "";
    protected String middleName = "";
    protected Emotion feeling = new Emotion(); //This is the feeling of the ai whether real or imaginary one cannot know
    public BasicAI(String firstName, String middleName, String lastName)
    {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }
    public BasicAI(String firstName, String middleName, String lastName, byte gender, Emotion feeling)
    {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.gender = gender;
        this.feeling = feeling;
    }
    /**
     * This returns the actual EMOTION of the ai's feeling
     * @return EMOTION
     */
    public Emotion getEmotion()
    {
        return feeling;
    }
    public String getName()
    {
        return firstName;
    }
    public String getMiddleName()
    {
        return middleName;
    }
    public String getLastName()
    {
        return lastName;
    }
    public byte getGender()
    {
        return gender;
    }
}
