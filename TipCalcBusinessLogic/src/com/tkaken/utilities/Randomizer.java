package com.tkaken.utilities;

import java.util.Random;

public class Randomizer
{

	private int maxValue;
	private Random random;

	public Randomizer()
	{
		setMaxRandomValue(1);
		setRandom();
	}

	private void setRandom()
	{
		this.random = new Random();
	}
	public int getRandomNumber()
	{
		return random.nextInt(maxValue);
	}


	public void setMaxRandomValue(int max)
	{
		if (max > 0)
		{
			maxValue = max;
		}
		else
		{
			maxValue = 1;
		}
	}
	
	public int getMaxRandomValue()
	{
		return maxValue;
	}
}
