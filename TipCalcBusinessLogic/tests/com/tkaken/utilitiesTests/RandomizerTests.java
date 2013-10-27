package com.tkaken.utilitiesTests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tkaken.utilities.Randomizer;
import static org.hamcrest.CoreMatchers.*;

public class RandomizerTests
{

	@Before
	public void setUp() throws Exception
	{
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void setPositiveMaxRandomValue()
	{
		Randomizer randomizer = new Randomizer();
		int expectedMaxValue = 5;
		
		//Act
		randomizer.setMaxRandomValue(expectedMaxValue);
		
		//check postconditions
		assertThat(randomizer.getMaxRandomValue(), is(equalTo(expectedMaxValue)));
	}

	@Test
	public void setNegativeMaxRandomValue()
	{
		Randomizer randomizer = new Randomizer();
		int expectedMaxValue = 1;
		
		//Act
		randomizer.setMaxRandomValue(-5);
		
		//check postconditions
		assertThat(randomizer.getMaxRandomValue(), is(equalTo(expectedMaxValue)));
	}

	@Test
	public void setZeroMaxRandomValue()
	{
		Randomizer randomizer = new Randomizer();
		int expectedMaxValue = 1;
		
		//Act
		randomizer.setMaxRandomValue(0);
		
		//check postconditions
		assertThat(randomizer.getMaxRandomValue(), is(equalTo(expectedMaxValue)));
	}

}
