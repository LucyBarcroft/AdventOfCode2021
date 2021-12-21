package submarine.corefunction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class PolymerAnalyser
{
  private String mPolymer;
  private HashMap<String, String> mRules = new HashMap<String, String>();
  private HashMap<String, Long> mPairToNumOccurrences = new HashMap<String, Long>();
  private HashMap<String, Long> mLetterToNumOccurrences = new HashMap<String, Long>();

  /**
   * CONSTRUCTOR
   * Populate the initial polymer state, and list of rules from the input
   *
   * @param polymerInput    The input. The first line is the starting state of the polymer.
   *                                   Then there is a blank line
   *                                   Then each rule is on a new line, and the pair and value are
   *                                   separated by " -> "
   */
  public PolymerAnalyser(ArrayList<String> polymerInput)
  {
    for (int j=2; j<polymerInput.size(); j++)
    {
      String[] rule = polymerInput.get(j).split(" -> ");
      mRules.put(rule[0], rule[1]);
    }

    mPolymer = polymerInput.get(0);

    for (int i=0; i<mPolymer.length(); i++)
    {
      char polymerChar = mPolymer.toCharArray()[i];
      incrementCharacterCount(polymerChar, 1);
      if (i != mPolymer.length()-1)
      {
        String polymerSubstring = mPolymer.substring(i, i+2);
        incrementPairOccurrences(mPairToNumOccurrences, polymerSubstring, 1);
      }
    }
  }

  /**
   * Increment the number of occurrences of a pair within the specified map.
   * @param mapToIncrement     The map where the number of occurrences of each pair is being stored
   * @param pair               The pair which to increment
   * @param numToIncrement     The number by which to increment the number of occurences by
   */
  private void incrementPairOccurrences(HashMap<String, Long> mapToIncrement,
                                        String pair,
                                        long numToIncrement)
  {
    long count = mapToIncrement.containsKey(pair) ? mapToIncrement.get(pair)+numToIncrement : 
                                                    numToIncrement;
    mapToIncrement.put(pair, count);
  }

  /**
   * Increment the number of times a specified character occurs. Used in calculating
   * the results of this polymer analyser
   * @param polymerChar    The character to increment.
   * @param value          The value by which to increment the number of occurrences.
   */
  private void incrementCharacterCount(char polymerChar, long value)
  {
    String polymerLetter= String.valueOf(polymerChar);
    long count = mLetterToNumOccurrences.containsKey(polymerLetter) ?
                                      mLetterToNumOccurrences.get(polymerLetter)+value : value;
    mLetterToNumOccurrences.put(polymerLetter, count);
  }

  /**
   * Perform one round of pair inserts to the polymer being analysed.
   */
  private void pairInsert()
  {
    //
    // Every time a pair within a rule occurs it will behave in the same way.
    // Each occurrence of this pair is replaced by 2 new pairs.
    // e.g. AB -> C      goes to ACB, which removes pair AB and introduces pairs AC and AB.
    // Add these 2 new pairs to a temporary store of the new number of occurrences of each pair,
    // then when we have finished analysing every pair, update our stored list of number of
    // occurrences of each pair
    //
    HashMap<String, Long> mTempRuleToNumOccurrences = new HashMap<String, Long>();

    for (String pair: mPairToNumOccurrences.keySet())
    {
      String letterToInsert = mRules.get(pair);
      long numberOfOccurrences = mPairToNumOccurrences.get(pair);
      incrementCharacterCount(letterToInsert.charAt(0), numberOfOccurrences);

      String substring1 = pair.substring(0,1) + letterToInsert;
      String substring2 = letterToInsert + pair.substring(1);

      if (mRules.containsKey(substring1))
      {
        incrementPairOccurrences(mTempRuleToNumOccurrences, substring1, numberOfOccurrences);
      }
      if (mRules.containsKey(substring2))
      {
        incrementPairOccurrences(mTempRuleToNumOccurrences, substring2, numberOfOccurrences);
      }
    }

    mPairToNumOccurrences = mTempRuleToNumOccurrences;
  }

  /**
   * Perform a specified number of pair inserts
   *
   * @param numInserts The number of pair inserts to perform
   */
  public void doInserts(int numInserts)
  {
    for (int i=0; i<numInserts; i++)
    {
      pairInsert();
    }
  }

  /**
   * @return the current result of this polymer analyser.
   */
  public long getResult()
  {
    return (Collections.max(mLetterToNumOccurrences.values()) - Collections.min(mLetterToNumOccurrences.values()));
  }
}
