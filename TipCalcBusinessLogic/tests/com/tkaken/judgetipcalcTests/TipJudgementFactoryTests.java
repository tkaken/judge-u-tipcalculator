package com.tkaken.judgetipcalcTests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tkaken.tipRules.Judgement;
import com.tkaken.tipRules.JudgementFactory;
import com.tkaken.tipRules.JudgementValues;
import com.tkaken.utilities.Randomizer;

public class TipJudgementFactoryTests
{
	@Mock private Randomizer randomizerReturnsZero;
	@Mock private Randomizer randomizerReturnsOne;
	
	private static final int fillColor = 0x0033B5E5;
	private static final int textColor = 0xffffffff;
	private static final String textTwo = "text two";
	private static final String textOne = "text one";
	
    ArrayList<String> judgementStrings;
	JudgementValues judgementValues;

	private static final int NUM_JUDGEMENTS = 2;
	
	
	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);
		
		when(randomizerReturnsOne.getRandomNumber()).thenReturn(NUM_JUDGEMENTS-1);
		
		when(randomizerReturnsZero.getRandomNumber()).thenReturn(0);
		
		makeJudgementValues();
	}

	private void makeJudgementValues()
	{
		judgementStrings = new ArrayList<String>();
		judgementStrings.add(textOne);
		judgementStrings.add(textTwo);

		this.judgementValues = new JudgementValues(fillColor, textColor, judgementStrings);
	}

	@After
	public void tearDown() throws Exception
	{

	}

	
	@Test
	public void judgementEqualsTest()
	{
		Judgement judgement1 = new Judgement(1, 2, textOne);
		Judgement judgement2 = new Judgement(1, 2, textOne);
		
		assertEquals(judgement1,judgement2);		
	}

	@Test
	public void judgementNotEqualsNullTest()
	{
		Judgement judgement1 = new Judgement(1, 2, textOne);
		
		assertThat(judgement1,not(equalTo(null)));		
	}
	
	
	@Test
	public void judgementNotEqualsDifferentTypeTest()
	{
		Judgement judgement1 = new Judgement(1, 2, textOne);
		Object aRandomizer = randomizerReturnsZero;
		
		assertThat(judgement1,not(equalTo(aRandomizer)));		
	}
		
	@Test
	public void judgementNotEqualsStateTest()
	{
		Judgement judgement1 = new Judgement(1, 2, textOne);
		Judgement judgement2 = new Judgement(1, 2, textTwo);
		
		assertThat(judgement1, not(equalTo(judgement2)));	
	}
	
	
	@Test
	public void getJudgementOneFromFactory()
	{
		
		//act
		JudgementFactory judgementFactory = new JudgementFactory(judgementValues, randomizerReturnsZero);
				
		//check postconditions
		Judgement actualJudgement = judgementFactory.getJudgement();
		assertEquals(fillColor, actualJudgement.getFillColor());				
		assertEquals(textColor, actualJudgement.getTextColor());
		assertEquals(textOne, actualJudgement.getText());
	}
	
	@Test
	public void getJudgementTwoFromFactory()
	{
		
		//act
		JudgementFactory judgementFactory = new JudgementFactory(judgementValues, randomizerReturnsOne);
				
		//check postconditions
		Judgement actualJudgement = judgementFactory.getJudgement();
		assertEquals(fillColor, actualJudgement.getFillColor());				
		assertEquals(textColor, actualJudgement.getTextColor());
		assertEquals(textTwo, actualJudgement.getText());
	}
	
}
