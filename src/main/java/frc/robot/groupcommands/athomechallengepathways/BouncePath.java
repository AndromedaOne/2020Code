/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.groupcommands.athomechallengepathways;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DeployAndRunIntake;
import frc.robot.pathgeneration.pathgenerators.DriveTrainDiagonalPathGenerator;
import frc.robot.pathgeneration.pathgenerators.PathGeneratorBase;
import frc.robot.pathgeneration.waypoints.Waypoint;
import frc.robot.pathgeneration.waypoints.WaypointsBase;
import frc.robot.subsystems.drivetrain.DriveTrain;
import frc.robot.subsystems.intake.IntakeBase;

public class BouncePath extends SequentialCommandGroup {
  private static double maximumPower = 0.5;
  private static double robotLengthInches = 40;
  private static double intakeLengthInches = 4;
  private static Waypoint initialPoint = AtHomeChallengePoints.C2.subtract(new Waypoint(0, robotLengthInches / 2));

  private class BounceWaypoints extends WaypointsBase {
    @Override
    protected void loadWaypoints() {
      addWayPoint(AtHomeChallengePoints.C3);
      addWayPoint(AtHomeChallengePoints.A3.add(new Waypoint(robotLengthInches / 2 - intakeLengthInches, 0)));
      addWayPoint(AtHomeChallengePoints.C3);

      addWayPoint(AtHomeChallengePoints.C4);
      addWayPoint(AtHomeChallengePoints.E4);

      addWayPoint(AtHomeChallengePoints.E6);
      addWayPoint(AtHomeChallengePoints.A6.add(new Waypoint(robotLengthInches / 2 - intakeLengthInches, 0)));
      addWayPoint(AtHomeChallengePoints.E6);

      addWayPoint(AtHomeChallengePoints.E9);
      addWayPoint(AtHomeChallengePoints.A9.add(new Waypoint(robotLengthInches / 2 - intakeLengthInches, 0)));
      addWayPoint(AtHomeChallengePoints.C9);

      addWayPoint(AtHomeChallengePoints.C11);
    }
  }

  /**
   * Creates a new BouncePath.
   */
  public BouncePath(DriveTrain driveTrain, IntakeBase intake) {
    PathGeneratorBase pathGenerator = new DriveTrainDiagonalPathGenerator(getClass().getSimpleName(),
        new BounceWaypoints(), driveTrain, initialPoint, maximumPower, true, false);
    addCommands(new ParallelCommandGroup(pathGenerator.getPath(), new DeployAndRunIntake(intake, () -> false)));
  }
}
