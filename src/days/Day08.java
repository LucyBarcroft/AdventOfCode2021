package days;

import java.util.ArrayList;

import submarine.corefunction.DisplayInterpretor;
import utils.GenericDay;

/**
 * --- Day 8: Seven Segment Search ---
 *
 * Numbers are displayed on seven segments, but we don't know which value
 * corresponds to which segment. Work out the output numbers!
 */
public class Day08 extends GenericDay {

  /**
   * CONSTRUCTOR
   */
  public Day08()
  {
    super(8);
  }

  public static void main(String[] args)
  {
    Day08 day = new Day08();
    ArrayList<String> input = day.getInput(false);

    int numUniqueNumbers = 0;
    int sum = 0;

    for (String inputRow: input)
    {
      DisplayInterpretor displayInterpretor = new DisplayInterpretor(inputRow);
      numUniqueNumbers += displayInterpretor.getNumUniqueNumbersInOutput();
      sum += displayInterpretor.getOutput();
    }

    System.out.println("number of unique numbers: " + numUniqueNumbers);
    System.out.println("sum: " + sum);
  }

}
