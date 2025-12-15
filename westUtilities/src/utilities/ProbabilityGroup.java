package utilities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class ProbabilityGroup 
{
	private ArrayList<Object> objects;
	private ArrayList<BigDecimal> probabilities;
	private boolean ignoreNonOneProbabilityCase = false;
	
	public ProbabilityGroup(ArrayList<Object> objects, ArrayList<BigDecimal> probabilities)
	{
		if(objects != null && probabilities != null && objects.size() == probabilities.size()) 
		{
			this.objects = new ArrayList<>(objects);
			this.probabilities = new ArrayList<>(probabilities);
		}
		else
		{
			this.objects = null;
			this.probabilities = null;
		}
	}
	
	public ProbabilityGroup(ArrayList<Object> objects, ArrayList<BigDecimal> probabilities, boolean ignoreMaxProbabilityCase)
	{
		this(objects, probabilities);
		this.ignoreNonOneProbabilityCase = ignoreMaxProbabilityCase;
	}
	
	
	//Methods:
	/**
	 * This function returns an object from the group according to weighted probabilities. 
	 * This function will return null when the total probability of the group is not equal to 1, unless the ignoreNonOneProbabilityCase is set to true.
	 * In that case, the function will re-weight itself, using the current probability max as the new 100%
	 * @return An Object randomly selected from the list given the list of probabilities.
	 */
	public Object getRandomPart()
	{
		BigDecimal totalProbability = isProbabilityIntegrityMaintained();
		if(totalProbability.compareTo(new BigDecimal(1)) == 0)
		{
			//do the work
			BigDecimal radomValue = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(0, 1));
			
			//Now loop through the probabilities until the correct probability is found:
			BigDecimal totalPercent = new BigDecimal(0);
			BigDecimal nextPercent = probabilities.get(0);
			for(int i = 0 ; i < probabilities.size()-1; i++)
			{
				if(totalPercent.compareTo(radomValue) <= 0 && nextPercent.compareTo(radomValue) >= 0)
				{
					return objects.get(i);
				}
				totalPercent.add(probabilities.get(i));
				nextPercent.add(probabilities.get(i+1));
			}
			//If we got here, then the last item in the list must be the one to return:
			return objects.get(objects.size()-1);
		}
		else if(ignoreNonOneProbabilityCase)
		{
			//Ignore the problem and use the total probability as our max random value. (Effectively this just makes a new 100% and will make everything weighted accordingly)

			//do the work
			BigDecimal radomValue = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(0, totalProbability.doubleValue()));
			
			//Now loop through the probabilities until the correct probability is found:
			BigDecimal totalPercent = new BigDecimal(0);
			BigDecimal nextPercent = probabilities.get(0);
			for(int i = 0 ; i < probabilities.size()-1; i++)
			{
				if(totalPercent.compareTo(radomValue) <= 0 && nextPercent.compareTo(radomValue) >= 0)
				{
					return objects.get(i);
				}
				totalPercent.add(probabilities.get(i));
				nextPercent.add(probabilities.get(i+1));
			}
			//If we got here, then the last item in the list must be the one to return:
			return objects.get(objects.size()-1);
		}
		//Else return a null value
		return null;
	}
	
	/**
	 * Has a O(n) time complexity.
	 * This function checks to make sure all probabilities add up to 1.
	 * @return A boolean value that is true only when the probabilities add up to 1.
	 */
	public BigDecimal isProbabilityIntegrityMaintained()
	{
		//Sum up the probabilities, and make sure they equal 1:
		BigDecimal totalPercent = new BigDecimal(0);
		for(int i = 0 ; i < probabilities.size(); i++)
		{
			totalPercent.add(probabilities.get(i));
		}
		
		if(totalPercent.compareTo(BigDecimal.valueOf(1)) == 0)
		{
			return totalPercent;
		}
		return totalPercent;
	}

	
	//Getters:
	public ArrayList<Object> getObjects() {
		return objects;
	}

	public ArrayList<BigDecimal> getProbabilities() {
		return probabilities;
	}

	public boolean isIgnoreNonOneProbabilityCase() {
		return ignoreNonOneProbabilityCase;
	}
	
	
	//Setters:
	public void setObjects(ArrayList<Object> objects) {
		this.objects = new ArrayList<>(objects);
	}

	public void setProbabilities(ArrayList<BigDecimal> probabilities) {
		this.probabilities = new ArrayList<>(probabilities);
	}

	public void setIgnoreNonOneProbabilityCase(boolean ignoreNonOneProbabilityCase) {
		this.ignoreNonOneProbabilityCase = ignoreNonOneProbabilityCase;
	}
}
