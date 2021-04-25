/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.groupcommands.athomechallengepathways;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DeployAndRunIntake;
import frc.robot.pathgeneration.pathgenerators.TwoDDriveTrainPathGenerator;
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
    System.out.println("Generating Path for Bounce");

    CommandBase bounce1 = (new TwoDDriveTrainPathGenerator("BouncePart1.wpilib.json", driveTrain, "BounceP1"))
        .getPath();
    CommandBase bounce2 = (new TwoDDriveTrainPathGenerator("BouncePart2.wpilib.json", driveTrain, false, "BounceP2"))
        .getPath();
    CommandBase bounce3 = (new TwoDDriveTrainPathGenerator("BouncePart3.wpilib.json", driveTrain, false, "BounceP3"))
        .getPath();
    CommandBase bounce4 = (new TwoDDriveTrainPathGenerator("BouncePart4.wpilib.json", driveTrain, false, "BounceP4"))
        .getPath();

    SequentialCommandGroup fullBounce = new SequentialCommandGroup(bounce1, bounce2, bounce3, bounce4);

    addCommands(new ParallelCommandGroup(fullBounce, new DeployAndRunIntake(intake, () -> false)));
  }
}
