/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aiUtilities;

/**
 *
 * @author Westley
 * This class tries to mimic emotions based on the ideas of those over time...
 */
public class Emotion 
{
    private int anger = 0;
    private int happy = 0;
    private int sad = 0;
    /**
     * The no parameter constructor of the emotion.
     * This constructs an emotion of anger: 0, happiness: 0, sadness: 0
     */
    public Emotion()
    {
        anger = 0;
        happy = 0;
        sad = 0;
    }
    /**
     * The two parameter constructor of the Emotion.
     * This constructs an emotion with the name of name and the intensity of intensity
     * @param curAnger
     * @param curHappiness
     * @param curSadness
     */
    public Emotion(int curAnger, int curHappiness, int curSadness)
    {
        anger = curAnger;
        happy = curHappiness;
        sad = curSadness;
    }
    /**
     * Returns the integer expression of the Anger
     * @return Integer
     */
    public int getAnger()
    {
        return anger;
    }
    /**
     * Returns the integer expression of the Happy
     * @return Integer
     */
    public int getHappiness()
    {
        return happy;
    }
    /**
     * Returns the integer expression of the Anger
     * @return Integer
     */
    public int getSadness()
    {
        return sad;
    }
}
