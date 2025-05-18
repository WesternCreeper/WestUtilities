/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

/**
 *
 * @author Westley
 */
public class Condition
{
    public final static int LESS_THAN = 0;
    public final static int EQUAL_TO = 1;
    public final static int GREATER_THAN = 2;
    public final static int LESS_THAN_OR_EQUAL_TO = 3;
    public final static int GREATER_THAN_OR_EQUAL_TO = 4;
    private double threashold;
    private int operation;
    private Object successResult;
    private Object failureResult;
    public Condition(double threashold, int operation, Object successResult, Object failureResult)
    {
        this.threashold = threashold;
        this.operation = operation;
        this.successResult = successResult;
        this.failureResult = failureResult;
    }
    
    public boolean checkConditionResult(double comparisionPoint)
    {
        switch(operation)
        {
            case LESS_THAN:
                return threashold < comparisionPoint;
            case EQUAL_TO:
                return threashold == comparisionPoint;
            case GREATER_THAN:
                return threashold > comparisionPoint;
            case LESS_THAN_OR_EQUAL_TO:
                return threashold <= comparisionPoint;
            case GREATER_THAN_OR_EQUAL_TO:
                return threashold >= comparisionPoint;
        }
        return false;
    }
    public Object getConditionResult(double comparisionPoint)
    {
        if(checkConditionResult(comparisionPoint))
        {
            return successResult;
        }
        return failureResult;
    }
}
