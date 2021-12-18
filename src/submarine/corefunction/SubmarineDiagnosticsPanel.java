package submarine.corefunction;

import java.util.ArrayList;

/**
 * Component responsible for reporting diagnostics information from the submarine.
 */
public class SubmarineDiagnosticsPanel
{
  private int mGammaRate;
  private int mEpsilonRate;
  private int mOxygenGenaratorRating;
  private int mCo2ScrubberRating;

  /**
   * The gamma rate takes the most common bit in each position in the input
   * The epsilon rate takes the least common bit.
   * Doing this together because they are linked.
   *
   * @param input              The input from which to calculate the gamma and epsilon rates
   * @param lengthToConsider   The number of bits up to which we want to determine the rates.
   * @return                   An array containing the gamma and epsilon rates.
   */
  private String[] calculateGammaAndEpsilonRateFromInput(ArrayList<String> input,
                                                         int lengthToConsider)
  {
    //
    // Sum all of the first bits, all of the second bits, etc...
    // Since all of the bits are 1 or 0, if the sum is greater than half the
    // length of the input, then the most common bit is 1, else it is 0
    //
    int[] sums = new int[lengthToConsider];
    String[] gammaAndEpsilonRates = new String[2];

    String gammaRate = "";
    String epsilonRate = "";

    for (String line: input)
    {
      for (int i = 0; i < lengthToConsider; i++)
      {
        sums[i] += Character.getNumericValue(line.charAt(i));
      }
    }

    for (int j : sums)
    {
      if (j >= Math.ceil((double)input.size()/2))
      {
        gammaRate += "1";
        epsilonRate += "0";
      }
      else
      {
        gammaRate += "0";
        epsilonRate += "1";
      }
    }

    gammaAndEpsilonRates[0] = gammaRate;
    gammaAndEpsilonRates[1] = epsilonRate;

    return gammaAndEpsilonRates;
  }

  /**
   * Calculate the carbon dioxide generator rating.
   * @param input   the input from which to calculate the rating.
   */
  private void calculateCo2GeneratorRating(ArrayList<String> input)
  {
    //
    // The epsilon string of some input gives the least common bits in that input
    // Each iteration, only keep input values which match the epsilon string, up to the
    // bit we care about.
    // Calculate a new epsilon string each iteration just with remaining input to a new degree of accuracy
    // The co2 generator rating is the last remaining input.
    //
    String epsilonString = calculateGammaAndEpsilonRateFromInput(input,1)[1];
    ArrayList<String> oldInput = input;
    ArrayList<String> newInput = new ArrayList<String>();
    boolean foundRating = false;
    int numExamined = 1;

    while (!foundRating)
    {
      newInput = new ArrayList<String>();

      for (String line: oldInput)
      {
        if (line.charAt(numExamined-1) == epsilonString.charAt(numExamined-1))
        {
          newInput.add(line);
        }
      }

      if (newInput.size() == 1)
      {
        foundRating = true;
        mCo2ScrubberRating = Integer.valueOf(newInput.get(0), 2);
      }
      else
      {
        numExamined++;
        epsilonString = calculateGammaAndEpsilonRateFromInput(newInput, numExamined)[1];
        oldInput = newInput;
      }
    }
  }

  /**
   * @param input  The input from which to calculate the life support rating
   * @return       The life support rating
   */
  public int calculateLifeSupportRating(ArrayList<String> input)
  {
    calculateOxygenGeneratorRating(input);
    calculateCo2GeneratorRating(input);

    return (mOxygenGenaratorRating * mCo2ScrubberRating);

  }

  /**
   * @param input    calculate and store off the o2 generator rating
   */
  private void calculateOxygenGeneratorRating(ArrayList<String> input)
  {
    //
    // The gamma string of some input gives the most common bits in that input
    // Each iteration, only keep input values which match the gamma string, up to the
    // bit we care about.
    // Calculate a new gamma string each iteration just with remaining input to a new degree of accuracy
    // The o2 generator rating is the last remaining input.
    //
    String gammaRateBinary = Integer.toBinaryString(mGammaRate);
    ArrayList<String> oldInput = input;
    ArrayList<String> newInput = new ArrayList<String>();
    boolean foundRating = false;
    int numExamined = 1;

    while (!foundRating)
    {
      newInput = new ArrayList<String>();
      for (String line: oldInput)
      {
        if (line.startsWith(gammaRateBinary.substring(0,numExamined)))
        {
          newInput.add(line);
        }
      }

      if (newInput.size() == 1)
      {
        foundRating = true;
        mOxygenGenaratorRating = Integer.valueOf(newInput.get(0), 2);
      }
      else
      {
        numExamined++;
        gammaRateBinary = calculateGammaAndEpsilonRateFromInput(newInput, numExamined)[0];
        oldInput = newInput;
      }
    }
  }

  /**
   * @return  The power consumption
   */
  public int getPowerConsumption()
  {
    return (mGammaRate * mEpsilonRate);
  }

  /**
   * Store off the gamma and epsilon rating from some input
   *
   * @param input   The input from which to calculate the ratings
   */
  public void populateGammaAndEpsilonRateFromInput(ArrayList<String> input)
  {
    String[] gammaAndEpsilonRates = calculateGammaAndEpsilonRateFromInput(input, input.get(0).length());
    mGammaRate = Integer.valueOf(gammaAndEpsilonRates[0], 2);
    mEpsilonRate = Integer.valueOf(gammaAndEpsilonRates[1], 2);
  }
}
