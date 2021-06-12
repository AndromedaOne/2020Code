package frc.robot.groupcommands.RomiCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.BringWingsUp;
import frc.robot.commands.DriveAndTurn;
import frc.robot.commands.LetWingsDown;
import frc.robot.commands.romiShooter.*;
import frc.robot.commands.TimedCommand;
import frc.robot.commands.TrackLineAndDriveForward;
import frc.robot.commands.romiBallMopper.MopBallMopper;
import frc.robot.commands.romiBallMopper.ResetBallMopper;

public class RomiChallenge4 extends SequentialCommandGroup {

  public static final double TIME_TO_DRIVE_AND_TURN = 4.0;
  public static final double TIME_TO_DRIVE_FORWARD = 7.0; // in seconds
  public static final double TIME_TO_LET_WINGS_DOWN = 4.5; // in seconds
  public static final double TIME_TO_BRING_WINGS_UP = 1.4; // in seconds
  public static final double TIME_BEFORE_FIRST_MOP = 3.3; // in seconds
  public static final double TIME_BETWEEN_FIRST_AND_SECOND_MOP = 6.6; // in seconds
  public static final double TIME_BETWEEN_SECOND_AND_THIRD_MOP = 9.9; // in seconds
  public static final double TIME_BETWEEN_FINAL_MOP_AND_LIFT_WING = 0.1;

  public RomiChallenge4() {

    addCommands(
      getCommandsToMoveToCenterLine(),
      getCommandsToMoveFromStartToEndOfMat(), 
      getCommandsToScoreMoppedBalls()
    );
  }

  private CommandBase getCommandsToMoveToCenterLine() {
    CommandBase command = new SequentialCommandGroup(
      TimedCommand.create(new DriveAndTurn(0.1), 2.0),
      TimedCommand.create(new DriveAndTurn(0.19), 2.0)
    );
    return command;
  }

  private CommandBase getCommandsToMoveFromStartToEndOfMat() {
    CommandBase command = new SequentialCommandGroup(
      new ParallelDeadlineGroup(
        TimedCommand.create(new LetWingsDown(), TIME_TO_LET_WINGS_DOWN),
        new StopRomiShooter()
      ),
      getCommandsToDriveAlongPath()
    );
    return command;
  }

  private CommandBase getCommandsToScoreMoppedBalls() {
    CommandBase command = new ParallelDeadlineGroup(
      new SequentialCommandGroup(
        TimedCommand.create(TIME_BETWEEN_FINAL_MOP_AND_LIFT_WING),
        TimedCommand.create(new BringWingsUp(), TIME_TO_BRING_WINGS_UP)
      ),
      new MopBallMopper(),
      new StopRomiShooter()
    );
    return command;
  }

  private CommandBase getCommandsToDriveAlongPath() {
    return new ParallelDeadlineGroup(
      TimedCommand.create(new TrackLineAndDriveForward(0.5, "ColorSensorFrontFast"), TIME_TO_DRIVE_FORWARD),
      new RunRomiShooter(),
      new ResetBallMopper()
    );
  }

}
