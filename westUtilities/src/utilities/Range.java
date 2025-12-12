package utilities;

import java.util.concurrent.ThreadLocalRandom;

public class Range <E extends Number>
{
	private E min;
	private E max;
	
	public Range(E min, E max)
	{
		this.min = min;
		this.max = max;
	}
	
	
	//Methods:
	public void setRange(E min, E max)
	{
		this.min = min;
		this.max = max;
	}
	
	/**
	 * Since this converts the numeric values to ints before creating the random number, there is no guarantee that the random number is within the range (exclusive to the top)
	 * @return int A random integer value within the range
	 */
	public int getRandomIntegerInRange()
	{
		return ThreadLocalRandom.current().nextInt(min.intValue(), max.intValue());
	}
	
	/**
	 * Since this converts the numeric values to doubles before creating the random number, there is no guarantee that the random number is within the range (exclusive to the top)
	 * @return double A random double value within the range
	 */
	public double getRandomDoubleInRange()
	{
		return ThreadLocalRandom.current().nextDouble(min.doubleValue(), max.doubleValue());
	}
	
	
	//Getters:
	public E getMin() {
		return min;
	}

	public E getMax() {
		return max;
	}
}
