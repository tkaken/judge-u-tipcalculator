package com.tkaken.tipRules;

import java.util.List;

public class JudgementValues
{
	private int fillColor;
	private int textColor;
	private List<String> judgementStrings;

	public JudgementValues(int fillColor, int textColor, List<String>judgementStrings)
	{
		this.fillColor = fillColor;
		this.textColor = textColor;
		this.judgementStrings = judgementStrings;
	}

	public int getFillColor()
	{
		return fillColor;
	}

	public int getTextColor()
	{
		return textColor;
	}

	public List<String> getJudgementStrings()
	{
		return judgementStrings;
	}
	
	

}
