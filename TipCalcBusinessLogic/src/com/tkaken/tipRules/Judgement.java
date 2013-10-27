package com.tkaken.tipRules;


public class Judgement
{

	private int fillColor;
	private int textColor;
	private String judgementText;
	
	public Judgement(int fillColor, int textColor, String judgementText)
	{
		this.fillColor = fillColor;
		this.textColor = textColor;
		this.judgementText = judgementText;
	}

	public int getFillColor()
	{
		return this.fillColor;
	}

	public int getTextColor()
	{
		return this.textColor;
	}

	public boolean equals(Object obj)
	{
		if (null == obj)
			return false;
		
		if (this == obj)
			return true;
		
		if (!(obj instanceof Judgement))
			return false;

		Judgement rhs = (Judgement) obj;

		return (this.textColor == rhs.textColor && 
				this.fillColor == rhs.fillColor) &&
				this.judgementText.equals(rhs.judgementText);
	}

	public int hashCode()
	{
		return this.textColor + this.fillColor;
	}

	public String getText()
	{
		return this.judgementText;
	}

}
