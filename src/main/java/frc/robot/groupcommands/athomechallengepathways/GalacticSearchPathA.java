package frc.robot.groupcommands.athomechallengepathways;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import frc.robot.Robot;
import frc.robot.commands.DeployAndRunIntake;
import frc.robot.pathgeneration.pathgenerators.DriveTrainDiagonalPathGenerator;
import frc.robot.pathgeneration.waypoints.Waypoint;
import frc.robot.pathgeneration.waypoints.WaypointsBase;
import frc.robot.telemetries.Trace;

public class GalacticSearchPathA extends CommandBase {
  private final double robotLength = 40;
  private final Waypoint startPoint = AtHomeChallengePoints.C1.average(AtHomeChallengePoints.C2);
  private final double maxSpeed = 1;
  private double sumOfUltrasonicDistances;
  private int count;
  private final int countThreshold = 5;

  private class BluePath extends WaypointsBase {
    // Waypoints for the blue path
    @Override
    protected void loadWaypoints() {
      addWayPoint(AtHomeChallengePoints.E6);
      addWayPoint(AtHomeChallengePoints.B7);
      addWayPoint(AtHomeChallengePoints.D12.average(AtHomeChallengePoints.E12));
    }
  }

  private class RedPath extends WaypointsBase {
    // Waypoints for the red path
    @Override
    protected void loadWaypoints() {
      addWayPoint(AtHomeChallengePoints.C3);
      addWayPoint(AtHomeChallengePoints.D5);
      addWayPoint(AtHomeChallengePoints.A6);
      addWayPoint(AtHomeChallengePoints.A12);
    }
  }

  public GalacticSearchPathA() {
    sumOfUltrasonicDistances = 0;
    count = 0;
  }

  public void initialize() {
    sumOfUltrasonicDistances = 0;
    count = 0;
    Trace.getInstance().logCommandStart(this);
  }

  @Override
  public void execute() {
    count++;
    sumOfUltrasonicDistances += Robot.getInstance().getSensorsContainer().getPowercellDetector().getDistanceInches();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(final boolean interrupted) {
    final double distanceToPowercell = sumOfUltrasonicDistances / count;
    if (distanceToPowercell <= 30) {
      DriveTrainDiagonalPathGenerator red = new DriveTrainDiagonalPathGenerator(getClass().getSimpleName(),
          new RedPath(), Robot.getInstance().getSubsystemsContainer().getDrivetrain(), startPoint, maxSpeed, false,
          false);
      CommandBase redPath = red.getPath();
      CommandBase cmd = new ParallelDeadlineGroup(redPath,
          new DeployAndRunIntake(Robot.getInstance().getSubsystemsContainer().getIntake(), () -> false));
      CommandScheduler.getInstance().schedule(cmd);
    } else {
      DriveTrainDiagonalPathGenerator blue = new DriveTrainDiagonalPathGenerator(getClass().getSimpleName(),
          new BluePath(), Robot.getInstance().getSubsystemsContainer().getDrivetrain(), startPoint, maxSpeed, false,
          false);
      CommandBase bluePath = blue.getPath();
      CommandBase cmdblue = new ParallelDeadlineGroup(bluePath,
          new DeployAndRunIntake(Robot.getInstance().getSubsystemsContainer().getIntake(), () -> false));
      CommandScheduler.getInstance().schedule(cmdblue);
    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return count >= countThreshold;
  }
}
