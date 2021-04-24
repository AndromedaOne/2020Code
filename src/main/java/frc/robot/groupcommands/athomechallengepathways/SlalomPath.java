/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.groupcommands.athomechallengepathways;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.pathgeneration.pathgenerators.DriveTrainDiagonalPathGenerator;
import frc.robot.pathgeneration.pathgenerators.PathGeneratorBase;
import frc.robot.pathgeneration.waypoints.Waypoint;
import frc.robot.pathgeneration.waypoints.WaypointsBase;
import frc.robot.subsystems.drivetrain.DriveTrain;

public class SlalomPath extends SequentialCommandGroup {
  private static double robotLengthInches = 40;
  private static double maximumPower = 1;
  private static Waypoint initialPoint = AtHomeChallengePoints.E2.subtract(new Waypoint(0, robotLengthInches / 2));

  private class SlalomWaypoints extends WaypointsBase {
    @Override
    protected void loadWaypoints() {
      addWayPoint(initialPoint);
      addWayPoint(AtHomeChallengePoints.E2);
      addWayPoint(AtHomeChallengePoints.A6);
      addWayPoint(AtHomeChallengePoints.E10);
      addWayPoint(AtHomeChallengePoints.E11);
      addWayPoint(AtHomeChallengePoints.C11);
      addWayPoint(AtHomeChallengePoints.C9);
      addWayPoint(AtHomeChallengePoints.E9);
      addWayPoint(AtHomeChallengePoints.E3);
      addWayPoint(AtHomeChallengePoints.C3);
      addWayPoint(AtHomeChallengePoints.C1);
    }
  }

  public SlalomPath(DriveTrain driveTrain) {
    PathGeneratorBase pathGenerator = new DriveTrainDiagonalPathGenerator(getClass().getSimpleName(),
        new SlalomWaypoints(), driveTrain, initialPoint, maximumPower, false, false);
    addCommands(pathGenerator.getPath());
  }
}