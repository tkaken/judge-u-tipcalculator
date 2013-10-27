package com.tkaken.tipRules;



public class TipJudgementRules
{
	private JudgementFactory cheapoFactory;
	private JudgementFactory saintFactory;
	private JudgementFactory normalFactory;

	private static int cheapoThreshold = 10;
	private static int saintThreshold = 20;
	
	
	public TipJudgementRules(JudgementFactory cheapoFactory, JudgementFactory saintFactory, JudgementFactory normalFactory )
	{
		this.cheapoFactory = cheapoFactory;
		this.saintFactory = saintFactory;
		this.normalFactory = normalFactory;
	}

	public Judgement getJudgement(int tipRate)
	{
		if (tipRate <= TipJudgementRules.cheapoThreshold)
		{
		   return this.cheapoFactory.getJudgement();
		}
		else if (tipRate >= TipJudgementRules.saintThreshold)
		{
			return this.saintFactory.getJudgement();
		}
		else
		{
			return this.normalFactory.getJudgement();
		}
	}

}
