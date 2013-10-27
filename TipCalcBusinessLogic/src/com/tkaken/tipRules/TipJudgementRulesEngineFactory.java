package com.tkaken.tipRules;

import com.tkaken.utilities.Randomizer;

public class TipJudgementRulesEngineFactory
{
	public TipJudgementRulesEngineFactory()
	{
	}
	
	public TipJudgementRules getRulesEngine(JudgementValues cheapoValues, JudgementValues normalValues, JudgementValues saintValues)
	{
		JudgementFactory cheapoJudgementFactory = makeJudgementFactory(cheapoValues);
		JudgementFactory normalJudgementFactory = makeJudgementFactory(normalValues);
		JudgementFactory saintJudgementFactory = makeJudgementFactory(saintValues);
		
		TipJudgementRules tipRulesEngine = new TipJudgementRules(cheapoJudgementFactory, saintJudgementFactory, normalJudgementFactory);
		return tipRulesEngine;
	}

	private JudgementFactory makeJudgementFactory(JudgementValues jValues)
	{
		Randomizer randomizer = new Randomizer();		
		JudgementFactory judgementFactory = new JudgementFactory(jValues, randomizer);
		return judgementFactory;
	}


}
