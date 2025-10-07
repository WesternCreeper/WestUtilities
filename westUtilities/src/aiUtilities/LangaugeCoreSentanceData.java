/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aiUtilities;

/**
 *
 * @author Westley
 */
public class LangaugeCoreSentanceData 
{
    public static final int NULL_MASK = -1;
    public static final int PROPER_NOUN_MASK = 0;
    public static final String PROPER_NOUN_STRING = "PN";
    public static final int VERB_MASK = 1;
    public static final String VERB_STRING = "VB";
    private int[] sentanceComposition;
    public LangaugeCoreSentanceData(String sentence)
    {
        String sentanceData[] = sentence.split('+' + "");
        sentanceComposition = new int[sentanceData.length];
        for(int i = 0 ; i < sentanceData.length ; i++)
        {
            String psuedonym = sentanceData[i].strip();
            switch(psuedonym)
            {
                case PROPER_NOUN_STRING:
                    sentanceComposition[i] = PROPER_NOUN_MASK;
                    break;
                case VERB_STRING:
                    sentanceComposition[i] = VERB_MASK;
                    break;
                default:
                    sentanceComposition[i] = NULL_MASK;
                    break;
            }
        }
    }

    public int[] getSentanceComposition() 
    {
        return sentanceComposition;
    }
}
