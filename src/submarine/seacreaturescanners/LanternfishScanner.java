package submarine.seacreaturescanners;

import java.util.stream.LongStream;

public class LanternfishScanner
{
  /**
   * The number of lanternfish with each number of days left until decay.
   * The index of the array represents the number of days left until decay,
   * and the value is the number of lanternfish with that many days.
   */
  long[] mNumLanternfishWithEachDays = new long[9];

  /**
   * CONSTRUCTOR
   * populates the list of number of lanternfish with each number of days
   * left until decay
   *
   * @param startingLanternfish    A list of starting lanternfish
   *                               each integer represents one lanternfish.
   *                               It is the number of days left of that lanternfish
   *                               until decay.
   */
  public LanternfishScanner(int[] startingLanternfish)
  {
    for (int lanternfish: startingLanternfish)
    {
      mNumLanternfishWithEachDays[lanternfish]++;
    }
  }

  /**
   * Get the number of lanternfish there are after the specified number of
   * days
   *
   * @param numDays    The number of days after which to count lanternfish
   * @return           The final number of lanternfish
   */
  public long getNumLanternfish(int numDays)
  {
    for (int day=0; day<numDays; day++)
    {
      //-----------------------------------------------------------------
      // Each day every fish decays by 1. Therefore each value is moved
      // one earlier in the array.
      // Each lanternfish with 0 days left will generate 2 new fish -
      // one with 6 days left and one with 8 days left (the maximum
      // possible number of days left)
      // Therefore we add the number that had 0 days left to the number
      // which now have 6 days left. Since all fish which had 8 days
      // left will have decayed to 7, set the final value in the array
      // equal to the number of new lanternfish.
      //-----------------------------------------------------------------
      long numNewLanternfish = mNumLanternfishWithEachDays[0];

      for (int j=1; j<9; j++)
      {
        mNumLanternfishWithEachDays[j-1] = mNumLanternfishWithEachDays[j];
      }

      mNumLanternfishWithEachDays[6]+=numNewLanternfish;
      mNumLanternfishWithEachDays[8]=numNewLanternfish;
    }

    long sum = LongStream.of(mNumLanternfishWithEachDays).sum();

    return sum;
  }



}
