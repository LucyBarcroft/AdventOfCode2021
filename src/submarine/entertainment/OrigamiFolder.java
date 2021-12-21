package submarine.entertainment;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Can fold paper.
 */
public class OrigamiFolder
{
  private boolean[][] mPaper;
  private ArrayList<Fold> mFolds = new ArrayList<Fold>();

  /**
   * CONSTRUCTOR
   * Initialise the paper, and the fold instructions.
   *
   * @param input   The input from which to initialise this object.
   */
  public OrigamiFolder(ArrayList<String> input)
  {
    String inputRow;
    int i=0;

    ArrayList<int[]> coordsList = new ArrayList<int[]>();
    int maxX = 0;
    int maxY = 0;

    //
    // There was slight initial confusion because java array indexing is the other
    // way around to that in the question. i.e. {1,0} in question is array[0][1]
    // Because it is basically an array of arrays.
    // Put the initial co-ordinates the other way around and then just consider an "x"
    // fold as a left fold and a "y" fold as an up fold.
    //
    while (!(inputRow = input.get(i)).isEmpty())
    {
      int[] coords = Arrays.stream(inputRow.split(",")).mapToInt(Integer::parseInt).toArray();
      if (coords[1] > maxX) maxX=coords[1];
      if (coords[0] > maxY) maxY=coords[0];
      coordsList.add(coords);
      i++;
    }
    i++;

    mPaper = new boolean[maxX+1][maxY+1];
    for (int[] coords: coordsList)
    {
      mPaper[coords[1]][coords[0]] = true;
    }

    for (int j=i; j<input.size(); j++)
    {
      inputRow = input.get(j);
      mFolds.add(new Fold(inputRow.substring(11, 12), Integer.valueOf(inputRow.substring(13))));
    }
  }

  /**
   * Perform a fold in the paper
   *
   * @param fold    The fold to make
   */
  public void foldPaper(Fold fold)
  {
    boolean[][] newPaper;
    switch (fold.getDirection())
    {
      case "y":
      {
        //
        // Consider a y fold to be an upwards fold
        // Woo the folds were always in half, I didn't need to bother considering none half folds!
        // If you fold upwards, you reduce the rows
        // If either the first row or last row in the same column is marked
        // then the first row of the new paper will be marked in that column.
        // Dito working inwards. (first+1) && (last-1) etc
        //
        if (fold.getAxis()!=(mPaper.length-1)/2)
        {
          System.out.println("axis: " + fold.getAxis() + " length: " + (mPaper.length-1)/2);
          System.err.println("Not a fold in half :( You actually have to consider that!");
        }
        else
        {
          newPaper = new boolean[(mPaper.length-1)/2][mPaper[0].length];
          for (int i=0; i<newPaper.length; i++)
          {
            for (int j=0; j<newPaper[0].length; j++)
            {
              newPaper[i][j] = (mPaper[i][j] || mPaper[mPaper.length-1-i][j]);
            }
          }
          mPaper = newPaper;
        }
        break;
      }
      case "x":
      {
        //
        // Consider an x fold to be a leftwards fold.
        // If you fold left, you reduce the columns.
        // if either the first column or last column in the same row
        // is marked then the first column of the new paper will be marked in that row
        // Ditto working inwards.(first+1) && (last-1) etc
        //
        if (fold.getAxis()!=(mPaper[0].length-1)/2)
        {
          System.out.println("axis: " + fold.getAxis() + " length: " + (mPaper[0].length-1)/2);
          System.err.println("Not a fold in half :( You actually have to consider that!");
        }
        else
        {
          newPaper = new boolean[mPaper.length][(mPaper[0].length-1)/2];
          for (int i=0; i<newPaper.length; i++)
          {
            for (int j=0; j<newPaper[0].length; j++)
            {
              newPaper[i][j] = (mPaper[i][j] || mPaper[i][mPaper[0].length-1-j]);
            }
          }
          mPaper = newPaper;
        }
        break;
      }
      default:
      {
        System.err.println("unexpected fold direction!: " + fold.getDirection());
        break;
      }
    }
  }

  /**
   * Fold the paper just one time
   */
  public void foldOnce()
  {
    foldPaper(mFolds.get(0));
  }

  /**
   * Perform all the folds in the folds list.
   */
  public void foldAll()
  {
    for (Fold fold: mFolds)
    {
      foldPaper(fold);
    }
  }

  /**
   * @return The number of places which appear marked on the paper
   */
  public int getNumTrue()
  {
    int sum = 0;
    for (boolean[] paperRow: mPaper)
    {
      for (boolean value: paperRow)
      {
        sum += value? 1: 0;
      }
    }

    return sum;
  }

  /**
   * An class representing an instruction to fold the paper
   */
  public class Fold
  {
    private String mDirection;
    private int mAxis;

    public Fold(String direction, int axis)
    {
      mDirection = direction;
      mAxis = axis;
    }

    public String getDirection()
    {
      return mDirection;
    }

    public int getAxis()
    {
      return mAxis;
    }
  }

  /**
   * Print the paper.
   */
  public void printPaper()
  {
    for (boolean[] paperRow: mPaper)
    {
      for (boolean paperValue: paperRow)
      {
        if (paperValue) System.out.print("#");
        else System.out.print("-");
      }
      System.out.println();
    }
  }
}