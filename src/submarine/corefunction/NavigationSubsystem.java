package submarine.corefunction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class NavigationSubsystem
{
  ArrayList<String> mInput = new ArrayList<String>();

  public NavigationSubsystem(ArrayList<String> input)
  {
    mInput = input;
  }

   /**
    * Data corresponding to a type of chunk
    */
  private enum ChunkType
  {
    STANDARD("(", ")", 3, 1),
    SQUARE("[", "]", 57, 2),
    CURLY("{", "}", 1197, 3),
    TRIANGLE("<", ">", 25137, 4);

    private String mOpener;
    private String mCloser;
    private int mInvalidPoints;
    private int mUnfinishedPoints;

    ChunkType(String opener, String closer, int invalidPoints, int unfinishedPoints)
    {
      mOpener = opener;
      mCloser = closer;
      mInvalidPoints = invalidPoints;
      mUnfinishedPoints = unfinishedPoints;
    }

    private static HashMap<String, ChunkType> closerMap = new HashMap<>();
    static
    {
      for (ChunkType chunkType: ChunkType.values())
      {
        closerMap.put(chunkType.mCloser, chunkType);
      }
    }

    private static HashMap<String, ChunkType> openerMap = new HashMap<>();
    static
    {
      for (ChunkType chunkType: ChunkType.values())
      {
        openerMap.put(chunkType.mOpener, chunkType);
      }
    }

    public static ChunkType fromCloser(String closer)
    {
      return closerMap.get(closer);
    }

    public static ChunkType fromOpener(String opener)
    {
      return openerMap.get(opener);
    }

    public int getUnfinishedPoints()
    {
      return mUnfinishedPoints;
    }

    public int getInvalidPoints()
    {
      return mInvalidPoints;
    }

    public String getOpener()
    {
      return mOpener;
    }

    public String getCloser()
    {
      return mCloser;
    }
  }

  public void printScore(boolean printInvalid, boolean printIncomplete)
  {
    long score = 0;
    ArrayList<Long> unfinishedScores = new ArrayList<Long>();

    for (String line: mInput)
    {
      boolean valid = true;

      //
      // Find the earliest chunk closer
      // Check if the character before it is the corresponding opener.
      // if so this is valid and remove from the line.
      //
      while (valid)
      {
        int closerIndex = -1;
        String closerChar = "";

        for (ChunkType chunkType: ChunkType.values())
        {
          String chunkCloser = chunkType.getCloser();
          int index = line.indexOf(chunkCloser);

          if ((closerIndex == -1 || index < closerIndex) && (index > 0))
          {
            closerIndex = index;
            closerChar = chunkCloser;
          }
        }

        if (closerIndex == -1)
        {
          //
          // There are no more chunk closers in this line.
          // Part 2 - score unfinished lines
          //
          valid = false;

          if (printIncomplete)
          {
            if (!line.isEmpty())
            {
              line = new StringBuilder(line).reverse().toString();
              long pointsForLine = 0;

              for (char c: line.toCharArray())
              {
                ChunkType chunkType = ChunkType.fromOpener(Character.toString(c));
                pointsForLine = (pointsForLine * 5) + chunkType.getUnfinishedPoints();
              }

              unfinishedScores.add(pointsForLine);
            }
          }
        }
        else if ((closerIndex-1 >= 0) &&
                  line.substring(closerIndex-1, closerIndex)
                      .equals(ChunkType.fromCloser(closerChar).getOpener()))
        {
          //
          // Valid chunk - remove it from the string and keep iterating
          // I wonder if I could find a better algorithm which doesn't rely on removing
          // things from strings.
          //
          line = (line.substring(0,closerIndex-1) + line.substring(closerIndex+1));
        }
        else
        {
          //
          // invalid chunk - score points for part 1
          //
          valid = false;
          if (printInvalid)
          {
            score+=ChunkType.fromCloser(closerChar).getInvalidPoints();
          }
        }
      }
    }

    if (printInvalid) System.out.println("invalid score: " + score);

    if (printIncomplete)
    {
      Collections.sort(unfinishedScores);
      long secondScore = unfinishedScores.get((unfinishedScores.size()+1)/2 - 1);
      System.out.println("unfinished score: " + secondScore);
    }
  }

}
