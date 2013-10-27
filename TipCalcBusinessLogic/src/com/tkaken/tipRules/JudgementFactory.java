package com.tkaken.tipRules;

import java.util.List;

import com.tkaken.utilities.Randomizer;

public class JudgementFactory
{

	private JudgementValues judgementValues;
	private Randomizer randomizer;

	public JudgementFactory(JudgementValues judgementValues, Randomizer randomizer)
	{
		this.judgementValues = judgementValues;
		
		this.randomizer = randomizer;
		this.randomizer.setMaxRandomValue(getNumJudgements());
	}

	public Judgement getJudgement()
	{		
		return new Judgement(judgementValues.getFillColor(), judgementValues.getTextColor(), judgementValues.getJudgementStrings().get(randomizer.getRandomNumber()));
	}

	public int getNumJudgements()
	{
		return judgementValues.getJudgementStrings().size();
	}

}