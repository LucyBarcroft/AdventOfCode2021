package days;

import java.util.ArrayList;

import submarine.Submarine;
import submarine.corefunction.SubmarineDiagnosticsPanel;
import utils.GenericDay;

/**
 * --- Day 3: Binary Diagnostic ---
 * 
 * Examine a diagnostic report for the submarine (a series of binary input) 
 * and obtain some diagnostics through considering the most and least common
 * value of each bit.
 */
public class Day03 extends GenericDay 
{
	protected Day03() 
	{
		super(3);
	}

	public static void main(String[] args)
	{
        Day03 day = new Day03();
        ArrayList<String> input = day.getInput(false);
        Submarine submarine = new Submarine();
        SubmarineDiagnosticsPanel submarineDiagsPanel = submarine.getDiagsPanel();
        submarineDiagsPanel.populateGammaAndEpsilonRateFromInput(input);
        
        System.out.println(submarineDiagsPanel.getPowerConsumption());
        
        int lifeSupportRating = submarineDiagsPanel.calculateLifeSupportRating(input);
        
        System.out.println("life support rating: " + lifeSupportRating);
  	}
}
