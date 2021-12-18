package utils;

import java.io.File;
import java.util.ArrayList;

/**
 * Function common to all days.
 */
public abstract class GenericDay
{
  protected String mInputFilename;
  protected String mExampleInputFilename;
  protected int mDayNumber;

  /**
   * @param usingExampleInput Whether we want to use the example input instead
   *                          of the real data
   *
   * @return
   */
  public String populateFilename(boolean usingExampleInput)
  {
    String dayString = String.valueOf(mDayNumber);

    if (mDayNumber < 10)
    {
      dayString = "0" + dayString;
    }

    String filename = "input" + File.separator + "Day" + dayString;

    if (usingExampleInput)
    {
      filename += "example";
    }

    filename += "input.txt";

    return filename;
  }

  public ArrayList<String> getInput(boolean usingExampleInput)
  {
    String filename = mInputFilename;

    if (usingExampleInput)
    {
      filename = mExampleInputFilename;
    }

    return FileUtilities.readStringsFromFile(filename);
  }

  public int[] getInputAsIntArray(boolean usingExampleInput)
  {
    ArrayList<String> inputStringList = getInput(usingExampleInput);
    int[] inputIntArray = null;
    try
    {
      inputIntArray = inputStringList.stream()
                                     .mapToInt(x -> Integer.valueOf(x))
                                     .toArray();
    }
    catch (NumberFormatException e)
    {
      System.err.println("Not all of the input was integer!");
      e.printStackTrace();
    }

    return inputIntArray;
  }

  public int[] readInputAsIntArrayFromSingleLine(boolean usingExampleInput,
                                                 String delimeter)
  {
    String filename = mInputFilename;

    if (usingExampleInput)
    {
      filename = mExampleInputFilename;
    }

    return FileUtilities.readIntArrayFromSingleLineFile(filename, delimeter);
  }

  protected GenericDay(int dayNumber)
  {
    mDayNumber = dayNumber;
    mInputFilename = populateFilename(false);
    mExampleInputFilename = populateFilename(true);
  }
}
