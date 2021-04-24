package frc.robot.groupcommands.athomechallengepathways;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.pathgeneration.pathgenerators.DriveTrainDiagonalPathGenerator;
import frc.robot.pathgeneration.pathgenerators.PathGeneratorBase;
import frc.robot.pathgeneration.waypoints.Waypoint;
import frc.robot.pathgeneration.waypoints.WaypointsBase;
import frc.robot.subsystems.drivetrain.DriveTrain;

public class BarrelRacingPath extends SequentialCommandGroup {
  private static double maximumPower = .5;
  private static double robotLengthInches = 40;
  private static Waypoint initialPoint = AtHomeChallengePoints.C2.subtract(new Waypoint(0, robotLengthInches / 2));

  private class BarrelWaypoints extends WaypointsBase {
    @Override
    protected void loadWaypoints() {
      addWayPoint(AtHomeChallengePoints.C5);
      addWayPoint(AtHomeChallengePoints.E7);
      addWayPoint(AtHomeChallengePoints.E3);
      addWayPoint(AtHomeChallengePoints.C5);
      addWayPoint(AtHomeChallengePoints.C8);
      addWayPoint(AtHomeChallengePoints.A10);
      addWayPoint(AtHomeChallengePoints.A6);
      addWayPoint(AtHomeChallengePoints.E11);
      addWayPoint(AtHomeChallengePoints.C11);
      addWayPoint(AtHomeChallengePoints.C2);
    }
  }

  public BarrelRacingPath(DriveTrain driveTrain) {
    PathGeneratorBase pathGenerator = new DriveTrainDiagonalPathGenerator(getClass().getSimpleName(),
        new BarrelWaypoints(), driveTrain, initialPoint, maximumPower, true, false);
    addCommands(pathGenerator.getPath());
  }
}