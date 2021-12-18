package days;

import java.util.ArrayList;

import submarine.corefunction.NavigationSubsystem;
import utils.GenericDay;

/**
 * --- Day 10: Syntax Scoring ---
 *
 * Work out which lines are invalid, or incomplete and score them
 */
public class Day10 extends GenericDay
{
  public Day10()
  {
    super(10);
  }

  public static void main(String[] args)
  {
    Day10 day = new Day10();
    ArrayList<String> input = day.getInput(false);
    NavigationSubsystem navSub = new NavigationSubsystem(input);
    navSub.printScore(true, true);
  }
}
