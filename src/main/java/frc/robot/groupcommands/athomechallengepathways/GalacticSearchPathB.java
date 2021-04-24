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

public class GalacticSearchPathB extends CommandBase {
  private final double robotLength = 40;
  private final Waypoint startPoint = AtHomeChallengePoints.B1.average(AtHomeChallengePoints.B2);
  private final double maxSpeed = 1;
  private double sumOfUltrasonicDistances;
  private int count;
  private final int countThreshold = 5;

  private class BluePath extends WaypointsBase {
    @Override
    // Waypoints for the blue path
    protected void loadWaypoints() {
      addWayPoint(AtHomeChallengePoints.D6);
      addWayPoint(AtHomeChallengePoints.B8);
      addWayPoint(AtHomeChallengePoints.E12.add(new Waypoint(30, 0)));
    }
  }

  private class RedPath extends WaypointsBase {
    @Override
    // Waypoints for the red path
    protected void loadWaypoints() {
      addWayPoint(AtHomeChallengePoints.B3);
      addWayPoint(AtHomeChallengePoints.D5);
      addWayPoint(AtHomeChallengePoints.B7);
      addWayPoint(AtHomeChallengePoints.B12);
    }
  }

  public GalacticSearchPathB() {
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
    Trace.getInstance().logCommandStop(this);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return count >= countThreshold;
  }
}
