package com.tkaken.judgetipcalcTests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tkaken.tipRules.Judgement;
import com.tkaken.tipRules.JudgementValues;
import com.tkaken.tipRules.TipJudgementRules;
import com.tkaken.tipRules.TipJudgementRulesEngineFactory;

public class TipJudgementRulesEngineFactoryTests
{
	JudgementValues cheapJudgementValues;
	private static final int CHEAPO_FILL_COLOR = 0;
	private static final int CHEAPO_TEXT_COLOR = 1;

	JudgementValues normalJudgementValues;
	private static final int NORMAL_FILL_COLOR = 2;
	private static final int NORMAL_TEXT_COLOR = 3;

	JudgementValues saintJudgementValues;
	private static final int SAINT_FILL_COLOR = 4;
	private static final int SAINT_TEXT_COLOR = 5;

	
	private TipJudgementRulesEngineFactory rulesEngineFactory;

	@Before
	public void setUp() throws Exception
	{
		rulesEngineFactory = new TipJudgementRulesEngineFactory();
        
		cheapJudgementValues = makeJudgementValues(CHEAPO_FILL_COLOR, CHEAPO_TEXT_COLOR, "cheap string1", "cheap string2");
		normalJudgementValues = makeJudgementValues(NORMAL_FILL_COLOR, NORMAL_TEXT_COLOR, "normal string1", "normal string2");
		saintJudgementValues = makeJudgementValues(SAINT_FILL_COLOR, SAINT_TEXT_COLOR, "saint string1", "saint string2");

	}

	@After
	public void tearDown() throws Exception
	{
	}

	private JudgementValues makeJudgementValues(int fillColor, int textColor, String stringOne, String stringTwo)
	{
		ArrayList<String> judgementStrings = new ArrayList<String>();
		judgementStrings.add(stringOne);
		judgementStrings.add(stringTwo);
	
		return new JudgementValues(fillColor, textColor, judgementStrings);
	}

	@Test
	public void getRulesEngineFromFactory()
	{
		TipJudgementRules rulesEngine = rulesEngineFactory.getRulesEngine(cheapJudgementValues, normalJudgementValues, saintJudgementValues);
		
		assertThat(rulesEngine, is(notNullValue()));
				
	}
	
	@Test
	public void useRulesEngineForCheapo()
	{
		TipJudgementRules rulesEngine = rulesEngineFactory.getRulesEngine(cheapJudgementValues, normalJudgementValues, saintJudgementValues);
		
		Judgement judgement = rulesEngine.getJudgement(0);
		
		assertThat(judgement.getFillColor(), is(equalTo(CHEAPO_FILL_COLOR)));
		assertThat(judgement.getTextColor(), is(equalTo(CHEAPO_TEXT_COLOR)));
				
	}
	
	
	@Test
	public void useRulesEngineForSaint()
	{
		TipJudgementRules rulesEngine = rulesEngineFactory.getRulesEngine(cheapJudgementValues, normalJudgementValues, saintJudgementValues);
		
		Judgement judgement = rulesEngine.getJudgement(25);
		
		assertThat(judgement.getFillColor(), is(equalTo(SAINT_FILL_COLOR)));
				
	}
	
	@Test
	public void useRulesEngineForNormal()
	{
		TipJudgementRules rulesEngine = rulesEngineFactory.getRulesEngine(cheapJudgementValues, normalJudgementValues, saintJudgementValues);
		
		Judgement judgement = rulesEngine.getJudgement(15);
		
		assertThat(judgement.getFillColor(), is(equalTo(NORMAL_FILL_COLOR)));
				
	}
	
	
}
