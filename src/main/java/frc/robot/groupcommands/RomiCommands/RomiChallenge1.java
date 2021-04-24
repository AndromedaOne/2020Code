// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.groupcommands.RomiCommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.pathgeneration.pathgenerators.DriveTrainDiagonalPathGenerator;
import frc.robot.pathgeneration.pathgenerators.PathGeneratorBase;
import frc.robot.pathgeneration.waypoints.Waypoint;
import frc.robot.pathgeneration.waypoints.WaypointsBase;
import frc.robot.subsystems.drivetrain.DriveTrain;

/** Add your docs here. */
public class RomiChallenge1 extends SequentialCommandGroup {
  private class ChallengePath extends WaypointsBase {

    @Override
    protected void loadWaypoints() {

      //
      // Autonomouse code
      // this code is to be used with 0.65 and true as values into the constructor
      // it also stops at the mid point of the circle
      //
      addWayPoint(new Waypoint(0, 0)); // home base
      addWayPoint(new Waypoint(0, 18.0)); // straight forward
      addWayPoint(new Waypoint(-17.75, 18.0)); // turn left straight into yellow
      addWayPoint(new Waypoint(-11.32, 8)); // from yellow into mid point of circle
      addWayPoint(new Waypoint(-4.5, -5.50)); // mid point circle to blue section
      addWayPoint(new Waypoint(-21.85, -3)); // from blue go left to the corner
      addWayPoint(new Waypoint(-22.6, 14)); // from corner to the target zone

    }
  }

  public RomiChallenge1(DriveTrain drivetrain) {
    PathGeneratorBase generaterbase = new DriveTrainDiagonalPathGenerator(getClass().getSimpleName(),
        new ChallengePath(), drivetrain, new Waypoint(0, 0), 0.65, true, true);
    addCommands(generaterbase.getPath());
  }
}
