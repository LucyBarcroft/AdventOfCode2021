package submarine.corefunction;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Can interpret a 7 bit display into numbers.
 */
public class DisplayInterpretor
{
  /**
   * Every input string. There should be 10, one will correspond to each digit
   */
  private String[] mInput;

  /**
   * Every output string. There should be 4 to create a 4 digit number
   */
  private String[] mOutput;

  /**
   * A mapping of numbers to their corresponding alphabetised segments.
   * The array index corresponds to each digit, and the value is its
   * segment string
   */
  private String[] mNumsAsStrings = new String[10];

  /**
   * CONSTRUCTOR
   * Reads in the command, and parses into input and output arrays
   *
   * @param command    input Command
   */
  public DisplayInterpretor(String command)
  {
    readCommand(command);
  }

  /**
   * Print an error if we think the input doesn't contain the specified number
   *
   * @param num     the number to check
   */
  private void checkInputContains(int num)
  {
    if (mNumsAsStrings[num] == null)
    {
      System.err.println("Input contains no " + num + "!");
    }
  }

  /**
   * Get the number of times an input string contains characters which
   * are in the corresponding string for the number to compare it to.
   * The string value of the number to compare it to must already have been
   * determined.
   *
   * @param inputNumber
   * @param numberToCompare
   * @return
   */
  private int getNumMatchesWith(String inputNumber, int numberToCompare)
  {
    // could i do anything w streams and num matches i wonder
    int numMatches = 0;

    for (char c: inputNumber.toCharArray())
    {
      if (mNumsAsStrings[numberToCompare].contains(String.valueOf(c)))
      {
        numMatches++;
      }
    }

    return numMatches;
  }

  /**
   * @return  the number of digits in the output which can be made on a 7
   *          segment display using a unique number of segments.
   */
  public int getNumUniqueNumbersInOutput()
  {
    int numUniqueNumbers = 0;

    // Numbers with:
    // 2 characters are 1s
    // 3 characters are 7s
    // 4 characters are 4s
    // 7 characters are 8s
    for (String outputValue: mOutput)
    {
      if (outputValue.length() == 2 ||
          outputValue.length() == 3 ||
          outputValue.length() == 4 ||
          outputValue.length() == 7)
      {
        numUniqueNumbers++;
      }
    }

    return numUniqueNumbers;
  }

  /**
   * Get the output of this interpretor as a 4 digit number.
   *
   * @return 4 digit output of the interpretor
   */
  public int getOutput()
  {
    //
    // Parse the 10 input strings into their corresponding digits
    //
    parseInputIntoDigits();

    //
    // Check which digit each output string corresponds to.
    // Depending on its position multiply by a power of 10 to get the
    // final 4 digit result e.g. 2751
    //
    int result = 0;
    for (int i=0; i<mOutput.length; i++)
    {
      String outputString = mOutput[i];
      String sortedString = getStringInAlphabeticalOrder(outputString);
      int number = Arrays.asList(mNumsAsStrings).indexOf(sortedString);

      result += (number * Math.pow(10, (3-i)));
    }

    return result;
  }

  /**
   * Put a string in alphabetical order
   *
   * @param inputString    The string to alphabetise
   * @return               The string in alphabetical order
   */
  private String getStringInAlphabeticalOrder(String inputString)
  {
    char[] inputChars = inputString.toCharArray();
    Arrays.sort(inputChars);
    return (new String(inputChars));
  }

  /**
   * Takes the input strings, and determines which string corresponds to which number.
   * Stores each input string alphabetised in an array.
   */
  private void parseInputIntoDigits()
  {
    ArrayList<String> fiveCharInput = new ArrayList<String>();
    ArrayList<String> sixCharInput = new ArrayList<String>();

    for (String inputValue: mInput)
    {
      //
      // Check the length of the input value
      // Digits with unique number of segments we can determine straight away
      // Digits with 5 or 6 segments, we store off to analyse later.
      //
      switch (inputValue.length())
      {
        case 2:
        {
          setNumAsString(1, inputValue);
          break;
        }
        case 3:
        {
          setNumAsString(7, inputValue);
          break;
        }
        case 4:
        {
          setNumAsString(4, inputValue);
          break;
        }
        case 5:
        {
          fiveCharInput.add(inputValue);
          break;
        }
        case 6:
        {
          sixCharInput.add(inputValue);
          break;
        }
        case 7:
        {
          setNumAsString(8, inputValue);
          break;
        }
        default:
        {
          System.err.println("Unrecognised length: " + inputValue.length());
        }
      }
    }

    //
    // Check that we have the expected number of input strings of each length.
    // For debugging purposes...
    //
    validateInputLengths(fiveCharInput, sixCharInput);

    //
    // Work out which number each five segment string corresponds to.
    // There are 3 five segment numbers: 2, 3 and 5
    // 2 is the only five segment number which has 2 segments which intersect with a 4
    // Of the remaining digits, 3 has 2 segments which interact with a 1, and 5 only has 1.
    //
    for (String fiveCharNumber: fiveCharInput)
    {
      int numMatches = getNumMatchesWith(fiveCharNumber, 4);

      if (numMatches == 2)
      {
        setNumAsString(2, fiveCharNumber);
      }
      else if (numMatches == 3)
      {
        int numMatchesWithOne = getNumMatchesWith(fiveCharNumber, 1);

        if (numMatchesWithOne == 2)
        {
          setNumAsString(3, fiveCharNumber);
        }
        else if (numMatchesWithOne == 1)
        {
          setNumAsString(5, fiveCharNumber);
        }
        else
        {
          System.err.println("Unrecognised 5 char: " + fiveCharNumber + " num matches w 1: " + numMatchesWithOne);
        }
      }
      else
      {
        System.err.println("Unrecognised 5 char: " + fiveCharNumber + " num matches: " + numMatches);
      }
    }

    //
    // Work out which number each six segment string corresponds to.
    // There are 3 six segment numbers: 0, 6 and 9
    // 6 is the only six segment number which has 1 segment which intersects with a 1
    // Of the remaining digits, 0 has 4 segments which intersect with a 5, and 9 has 5.
    //
    for (String sixCharNumber: sixCharInput)
    {
      int numMatchesWithOne = getNumMatchesWith(sixCharNumber, 1);

      if (numMatchesWithOne == 1)
      {
        setNumAsString(6, sixCharNumber);
      }
      else if (numMatchesWithOne == 2)
      {
        int numMatchesWithFive = getNumMatchesWith(sixCharNumber, 5);

        if (numMatchesWithFive == 4)
        {
          setNumAsString(0, sixCharNumber);
        }
        else
        {
          setNumAsString(9, sixCharNumber);
        }
      }
      else
      {
        System.err.println("Unrecognised 6 char: " + sixCharNumber + " num matches w 1: " + numMatchesWithOne);
      }
    }

    //
    // For debugging purposes
    // Print an error if the input doesn't contain any of the 5 or 6 segment numbers
    //
    checkInputContains(2);
    checkInputContains(3);
    checkInputContains(5);
    checkInputContains(6);
    checkInputContains(9);
  }

  /**
   * Reads a command and parses it into input and output segments.
   *
   * @param command   The command to parse.
   */
  private void readCommand(String command)
  {
    String[] inputAndOutput = command.split(" \\| ");
    mInput = inputAndOutput[0].split(" ");
    mOutput = inputAndOutput[1].split(" ");
  }

  /**
   * Once we have determined which number a string corresponds to - set this
   * in an array. Also print an error if that number already exists
   * for debugging purposes - each number should only correspond to one string.
   *
   * @param num     the number to set
   * @param value   the value of a number as a string
   */
  private void setNumAsString(int num, String value)
  {
    if (mNumsAsStrings[num] != null)
    {
      System.err.println("There are two " + num + "s !!");
    }

    mNumsAsStrings[num] = getStringInAlphabeticalOrder(value);
  }

  /**
   * Check that we have the expected number of strings of each length
   * in the input. For debugging purposes
   *
   * @param fiveCharInput
   * @param sixCharInput
   */
  private void validateInputLengths(ArrayList<String> fiveCharInput,
                                    ArrayList<String> sixCharInput)
  {
    checkInputContains(1);
    checkInputContains(4);
    checkInputContains(7);
    checkInputContains(8);

    if (fiveCharInput.size() != 3)
    {
      System.err.println("Wrong number of 5s!: " + fiveCharInput.size());
    }
    if (sixCharInput.size() != 3)
    {
      System.err.println("Wrong number of 6s!: " + sixCharInput.size());
    }
  }
}
