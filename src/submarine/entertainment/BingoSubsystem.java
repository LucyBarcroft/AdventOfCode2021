package submarine.entertainment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The bingo subsystem on the submarine, responsible for running games of Bingo
 * I suspect this could be done more efficiently.
 */
public class BingoSubsystem
{
  /**
   * A list of numbers the bingo subsystem is going to call
   * I am dealing with these entirely with strings until the end.
   * I should probably change that to integers...
   */
  private String[] mNumbersToCall;

  /**
   * A list of bingo boards that have been generated
   */
  private ArrayList<BingoBoard> mBingoBoards = new ArrayList<BingoBoard>();

  /**
   * A list of Bingo Boards which have been completed
   */
  private ArrayList<Integer> mWinningBoards = new ArrayList<Integer>();

  /**
   * The index of the last board to win.
   */
  private int mLosingBoard;

  /**
   * Set up the bingo subsystem for a game of bingo.
   * Populate the numbers to call and the list of bingo boards.
   *
   * @param input The input to the bingoSubsystem
   */
  public void populateBingoSubsystemFromInput(ArrayList<String> input)
  {
    mNumbersToCall = input.get(0).split(",");

    for (int i=2; i < input.size(); i+=6)
    {
      mBingoBoards.add(new BingoBoard(input.subList(i, i+5)));
    }
  }

  /**
   * Play Bingo, and find the first board to win
   */
  public void playBingo()
  {
    boolean bingoCalled = false;
    int turnNumber = 0;

    while (!bingoCalled)
    {
      turnNumber++;
      bingoCalled = takeTurn(turnNumber, true);
    }

    BingoBoard winningBoard = mBingoBoards.get(mWinningBoards.get(0));
    int unmarkedSum = winningBoard.getUnmarkedSum();
    int finalScore = unmarkedSum * Integer.valueOf(mNumbersToCall[turnNumber-1]);

    System.out.println("final score: " + finalScore);
  }

  /**
   * Play Bingo and find the last board to win
   */
  public void loseAtBingo()
  {
    int turnNumber = 0;
    boolean AllBoardsWon = false;

    while (!AllBoardsWon)
    {
      turnNumber++;
      takeTurn(turnNumber, false);

      if (mWinningBoards.size() == mBingoBoards.size())
      {
        AllBoardsWon = true;
      }
    }

    BingoBoard losingBoard = mBingoBoards.get(mLosingBoard);
    int unmarkedSum = losingBoard.getUnmarkedSum();
    int finalScore = unmarkedSum * Integer.valueOf(mNumbersToCall[turnNumber-1]);
    System.out.println("final score: " + finalScore);
  }

  /**
   * Take a turn in Bingo.
   *
   * @param turnNumber  The round number to play
   * @param breakOnWin  Whether to stop playing when one board has won, or keep
   *                    going.
   * @return            Whether any of the boards have won after this turn
   */
  public boolean takeTurn(int turnNumber, boolean breakOnWin)
  {
    String numberCalled = mNumbersToCall[turnNumber-1];
    boolean boardComplete = false;

    for (int boardIndex=0; boardIndex<mBingoBoards.size(); boardIndex++)
    {
      //
      // We only need to take a turn if the board hasn't already won.
      //
      if (!mWinningBoards.contains(boardIndex))
      {
        BingoBoard bingoBoard = mBingoBoards.get(boardIndex);
        bingoBoard.addNumberCalled(numberCalled);

        if (bingoBoard.shouldCallBingo())
        {
          boardComplete = true;
          mWinningBoards.add(boardIndex);

          if (mWinningBoards.size() == mBingoBoards.size())
          {
            mLosingBoard = boardIndex;
          }
          if (breakOnWin)
          {
            break;
          }
        }
      }
    }

    return boardComplete;
  }

  /**
   * A Bingo Board.
   */
  public class BingoBoard
  {
    private final int BOARD_SIZE = 5;
    private BingoRow[] mRows = new BingoRow[BOARD_SIZE];
    private BingoColumn[] mColumns = new BingoColumn[BOARD_SIZE];
    private boolean mShouldCallBingo = false;

    /**
     * CONSTRUCTOR
     * Create a bingo board from the input.
     *
     * @param list   A list of rows, where each row contains a series of 5 Bingo numbers
     */
    public BingoBoard(List<String> list)
    {
      for (int i=0; i < list.size(); i++)
      {
        try
        {
          String[] rowArr = list.get(i).trim().replaceAll("  ", " ").split(" ");
          BingoRow row = new BingoRow(i, rowArr);
          BingoColumn column = new BingoColumn();
          mRows[i] = row;
          mColumns[i] = column;
        }
        catch (IndexOutOfBoundsException e)
        {
          e.printStackTrace();
        }
      }
    }

    /**
     * @return   Whether this board has a completed row or column and the owner
     *           should call BINGO!
     */
    public boolean shouldCallBingo()
    {
      return mShouldCallBingo;
    }

    /**
     * Set whether this board has a completed row or column and the owner
     * should call BINGO!
     *
     * @param shouldCallBingo Whether the board is complete
     */
    public void setShouldCallBingo(boolean shouldCallBingo)
    {
      mShouldCallBingo = shouldCallBingo;
    }

    /**
     * An inner class representing a value present on the BINGO board.
     */
    private class BingoValue
    {
      /**
       * Whether this number has been called
       */
      private boolean mNumberCalled = false;

      /**
       * The number of this value.
       */
      private String mValue;

      public BingoValue(String value)
      {
        mValue = value;
      }

      public void setNumberCalled(boolean numberCalled)
      {
        mNumberCalled = numberCalled;
      }

      public boolean getNumberCalled()
      {
        return mNumberCalled;
      }

      public String getValue()
      {
        return mValue;
      }
    }

    class BingoRow
    {
      private BingoValue[] mRowValues;
      private int mNumComplete;

      BingoRow(int rowNumber, String[] rowValues)
      {
        mRowValues = Arrays.stream(rowValues)
                           .map(x -> new BingoValue(x))
                           .toArray(BingoValue[]::new);
      }

      public BingoValue[] getValues()
      {
        return mRowValues;
      }

      public int getNumComplete()
      {
        return mNumComplete;
      }

      public void setNumComplete(int numComplete)
      {
        mNumComplete = numComplete;
      }
    }

    private class BingoColumn
    {
      public void setNumComplete(int numComplete)
      {
        mNumComplete = numComplete;
      }

      public int getNumComplete()
      {
        return mNumComplete;
      }

      private int mNumComplete;
    }

    public BingoColumn getColumn(int columnNum)
    {
      return mColumns[columnNum];
    }

    /**
     * @return the sum of all numbers on this board which haven't been called
     */
    public int getUnmarkedSum()
    {
      int sum = 0;
      for (BingoRow row: mRows)
      {
        for (BingoValue value: row.getValues())
        {
          if (!value.getNumberCalled())
          {
            sum+=Integer.valueOf(value.getValue());
          }
        }
      }

      return sum;
    }

    /**
     * A number has been called. Add that number as marked on this board, if it exists.
     *
     * @param numberCalled   The number which has been called.
     */
    public void addNumberCalled(String numberCalled)
    {
      for (int rowNum=0; rowNum<5; rowNum++)
      {
        BingoRow row = mRows[rowNum];
        for (int columnNum=0; columnNum<5; columnNum++)
        {
          BingoValue rowValue = row.getValues()[columnNum];

          if (rowValue.getValue().equals(numberCalled) && 
              !rowValue.getNumberCalled())
          {
            rowValue.setNumberCalled(true);
            row.setNumComplete(row.getNumComplete()+1);

            BingoColumn column = getColumn(columnNum);
            column.setNumComplete(column.getNumComplete()+1);

            if (row.getNumComplete() == 5 || column.getNumComplete() == 5)
            {
              setShouldCallBingo(true);
            }
            break;
          }
        }
      }
    }
  }
}
