package submarine;

import submarine.corefunction.SubmarineDiagnosticsPanel;
import submarine.corefunction.SubmarineMotor;
import submarine.entertainment.BingoSubsystem;

/**
 * Object representing a submarine. It has several components corresponding to different
 * bits of submarine function.
 *
 * Started off adding things where the submarine would only have one of to the base
 * Submarine, but this is kind of pointless and this class isn't really necessary.
 */
public class Submarine
{
  /**
   * The motor for moving the submarine
   */
  private SubmarineMotor mMotor;

  /**
   * The diags panel, for reporting diagnostics
   */
  private SubmarineDiagnosticsPanel mDiagsPanel;

  /**
   * System for playing Bingo!
   */
  private BingoSubsystem mBingoSubsystem;

  /**
   * @return The submarine's bingo subsystem
   */
  public BingoSubsystem getBingoSubsytsem()
  {
    if (mBingoSubsystem == null)
    {
      mBingoSubsystem = new BingoSubsystem();
    }

    return mBingoSubsystem;
  }

  /**
   * @return  The submarine's diags panel
   */
  public SubmarineDiagnosticsPanel getDiagsPanel()
  {
    if (mDiagsPanel == null)
    {
      mDiagsPanel = new SubmarineDiagnosticsPanel();
    }

    return mDiagsPanel;
  }

  /**
   * @return The submarine's motor
   */
  public SubmarineMotor getMotor()
  {
    if (mMotor == null)
    {
      mMotor = new SubmarineMotor();
    }

    return mMotor;
  }
}
