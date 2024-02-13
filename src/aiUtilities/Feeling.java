/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aiUtilities;

/**
 *
 * @author Westley
 */
public class Feeling extends Emotion //This is for feelings towards someone else
{
    int id = 0;
    public Feeling()
    {
        super();
        id = 0;
    }
    public Feeling(int id)
    {
        super();
        this.id = id;
    }
    public Feeling(int id, int mad, int happy, int sad)
    {
        super(mad, happy, sad);
        this.id = id;
    }
    public int getID()
    {
        return id;
    }
}
